// 8. Complex Window Operations
import org.apache.spark.sql.expressions.Window

val windowSpec = Window
  .partitionBy("category")
  .orderBy("timestamp")
  .rowsBetween(Window.unboundedPreceding, Window.currentRow)

val result = df
  .withColumn("running_total", sum("value").over(windowSpec))
  .withColumn("running_average", avg("value").over(windowSpec))
  .withColumn("rank", dense_rank().over(windowSpec))