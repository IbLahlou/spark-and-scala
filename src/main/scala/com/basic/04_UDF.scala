// 4. Custom UDF for Complex Transformations
import org.apache.spark.sql.functions.udf

val cleanseDataUDF = udf((input: String) => {
  input.toLowerCase()
    .replaceAll("[^a-zA-Z0-9]", "")
    .trim
})

df.withColumn("cleansed_data", cleanseDataUDF(col("raw_data")))