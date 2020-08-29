FROM mozilla/sbt

WORKDIR /app

COPY . .

RUN sbt compile
