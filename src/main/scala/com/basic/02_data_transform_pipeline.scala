// 2. Data Transformation Pipeline
case class InputData(id: String, value: Double, timestamp: Long)
case class OutputData(id: String, aggregatedValue: Double, date: String)

def transformData(data: Dataset[InputData]): Dataset[OutputData] = {
  import spark.implicits._
  
  data
    .groupByKey(_.id)
    .mapGroups { case (id, values) =>
      val sum = values.map(_.value).sum
      val date = values.map(_.timestamp).max
      OutputData(id, sum, date.toString)
    }
}