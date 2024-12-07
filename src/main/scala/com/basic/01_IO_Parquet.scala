// 1. Reading and Writing Parquet Files with Spark
import org.apache.spark.sql.SparkSession

val spark = SparkSession.builder()
  .appName("ParquetExample")
  .getOrCreate()

// Reading Parquet
val df = spark.read.parquet("path/to/data.parquet")

// Writing Parquet with partitioning
df.write
  .partitionBy("date", "country")
  .mode("overwrite")
  .parquet("path/to/output")