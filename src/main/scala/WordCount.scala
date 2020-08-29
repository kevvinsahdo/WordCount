package com.wordcount

import org.apache.spark.sql.functions._
import org.apache.spark.sql.{DataFrame, SparkSession}

object WordCount {

    val sparkSession: SparkSession = {
        SparkSession
          .builder()
          .master("local")
          .appName("WordCount")
          .getOrCreate()
    }
    import sparkSession.implicits._

    def main(args: Array[String] = Array("7", "50")): Unit = {
        //threshold: Filter number of characters in word
        var threshold: Integer = 7
        //lines: Show given number of lines in result
        var lines: Integer  = 100

        if (!args.isEmpty) {
            threshold = args(0).toInt
            lines = args(1).toInt
        }

        // Load .csv file with tweets in a spark dataframe
        val tweetsDataframe = getTweetsDataframe()

        // Load stop words dataframe
        val stopWordsDataframe = getStopWordsDataframe()

        // Get words from tweets texts in csv
        val tweetsWords = getTweetsWords(tweetsDataframe, stopWordsDataframe)

        showWords(tweetsWords, threshold, lines)

        sparkSession.stop()
    }

    def showWords(tweetsWords: DataFrame, threshold: Integer, lines: Integer) = {
        tweetsWords
          .filter(length(col("word")) > threshold)
          .groupBy("word")
          .count()
          .orderBy(desc("count"))
          .show(lines)
    }

    def getTweetsWords(tweetsDataframe: DataFrame, stopWordsDataframe: DataFrame) = tweetsDataframe.select("text")
          .filter(col("text") =!= "")
          .rdd
          .flatMap(_.getString(0).split(" "))
          .toDF("word")
          .withColumn("word",
              trim {
                  lower {
                      regexp_replace(col("word"), "([^A-Za-z0-9]+)", "")
                  }
              })
          .as("tweetWords")
          .join(
              right = stopWordsDataframe.as("stopWords"),
              joinExprs = col("tweetWords.word") === col("stopWords.word"),
              joinType = "left_anti")
          .toDF("word")

    def getStopWordsDataframe() : DataFrame = sparkSession.sparkContext
      .textFile("/app/data/stop_words")
      .flatMap(_.split(" "))
      .toDF("word")

    def getTweetsDataframe() : DataFrame = sparkSession.read
      .format("com.databricks.spark.csv")
      .option("delimiter", ",")
      .option("header", "true")
      .load("/app/data/covid19_tweets.csv")
}
