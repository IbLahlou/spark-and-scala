// 7. Data Quality Checks
case class DataQualityMetrics(
  totalCount: Long,
  nullCount: Long,
  distinctCount: Long,
  minValue: Double,
  maxValue: Double
)

def calculateMetrics(df: DataFrame, columnName: String): DataQualityMetrics = {
  val metrics = df.agg(
    count("*").as("total"),
    count(when(col(columnName).isNull, true)).as("nulls"),
    countDistinct(columnName).as("distinct"),
    min(columnName).as("min"),
    max(columnName).as("max")
  ).collect()(0)
  
  DataQualityMetrics(
    metrics.getLong(0),
    metrics.getLong(1),
    metrics.getLong(2),
    metrics.getDouble(3),
    metrics.getDouble(4)
  )
}