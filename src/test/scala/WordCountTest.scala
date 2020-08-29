package com.wordcount

import org.scalatest.funsuite.AnyFunSuite
import org.apache.spark.sql.{DataFrame, SparkSession}


class WordCountTest extends AnyFunSuite {
  val sparkSession: SparkSession = {
    SparkSession
      .builder()
      .master("local")
      .appName("WordCountTest")
      .getOrCreate()
  }
  import sparkSession.implicits._

  test("Test getTweetWords remove stop words and clean tweets") {
    val tweetDataframe = Seq(("#test123"), ("I"), ("test")).toDF("text")
    val stopWordsDataframe = Seq(("you"), ("i"), ("yourself")).toDF("word")

    val tweetWords = WordCount.getTweetsWords(tweetDataframe, stopWordsDataframe)

    assert((tweetWords.count() == 2))
    assert((tweetWords.first.getString(0) === "test123"))
    assert(tweetWords.isInstanceOf[DataFrame])
  }

  test("Test getStopWordsDataframe return a dataframe object") {
    val stopWordsDataframe = WordCount.getStopWordsDataframe()

    assert(stopWordsDataframe.isInstanceOf[DataFrame])
  }

  test("Test getTweetsDataframe return a dataframe object") {
    val tweetsDataframe = WordCount.getTweetsDataframe()

    assert(tweetsDataframe.isInstanceOf[DataFrame])
  }
}
