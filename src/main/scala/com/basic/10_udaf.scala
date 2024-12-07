
// 10. Custom Aggregation with UDAF
import org.apache.spark.sql.expressions.Aggregator
import org.apache.spark.sql.Encoder
import org.apache.spark.sql.Encoders

case class Average(var sum: Double, var count: Long) {
  def add(value: Double): Average = {
    sum += value
    count += 1
    this
  }
  
  def merge(other: Average): Average = {
    sum += other.sum
    count += other.count
    this
  }
  
  def finish: Double = if (count == 0) 0.0 else sum / count
}

class CustomAverage extends Aggregator[Double, Average, Double] {
  def zero: Average = Average(0.0, 0L)
  def reduce(b: Average, a: Double): Average = b.add(a)
  def merge(b1: Average, b2: Average): Average = b1.merge(b2)
  def finish(reduction: Average): Double = reduction.finish
  
  def bufferEncoder: Encoder[Average] = Encoders.product[Average]
  def outputEncoder: Encoder[Double] = Encoders.scalaDouble
}

// Usage
val customAvg = new CustomAverage().toColumn
df.select(customAvg(col("value")).as("custom_average"))