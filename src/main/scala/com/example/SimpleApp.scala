package com.example

import org.apache.spark.sql.SparkSession

object SimpleApp {
  def main(args: Array[String]): Unit = {
    // Create Spark session with Java 11 compatible settings
    val spark = SparkSession.builder()
      .appName("Simple Spark App")
      .master("local[*]")  // Use local mode for testing
      .config("spark.driver.extraJavaOptions", "-Dio.netty.tryReflectionSetAccessible=true")
      .config("spark.executor.extraJavaOptions", "-Dio.netty.tryReflectionSetAccessible=true")
      .getOrCreate()

    try {
      // Create sample data
      val data = 1 to 100
      val rdd = spark.sparkContext.parallelize(data)

      // Perform a simple parallel computation
      val result = rdd
        .map(_ * 2)      // Double each number
        .filter(_ > 100)  // Keep numbers > 100
        .collect()        // Gather results

      println("Results greater than 100:")
      result.foreach(println)

    } finally {
      spark.stop()
    }
  }
}
