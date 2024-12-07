// 3. Streaming Data Processing
val streamingDF = spark.readStream
  .format("kafka")
  .option("kafka.bootstrap.servers", "localhost:9092")
  .option("subscribe", "input-topic")
  .load()

val query = streamingDF
  .selectExpr("CAST(value AS STRING)")
  .writeStream
  .outputMode("append")
  .format("console")
  .start()