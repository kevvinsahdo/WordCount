.PHONY: build run test

build:
	@docker build --tag wordcount:1.0 .

run:
	@docker run --rm wordcount:1.0 sbt "run ${THRESHOLD} ${LINES}"

test:
	@docker run --rm wordcount:1.0 sbt test
