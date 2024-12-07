// 9. Schema Evolution and Validation
import org.apache.spark.sql.types._

def validateSchema(df: DataFrame, expectedSchema: StructType): Either[String, DataFrame] = {
  val currentSchema = df.schema
  val missingFields = expectedSchema.fields.filterNot(field => 
    currentSchema.fields.exists(_.name == field.name)
  )
  
  if (missingFields.isEmpty) {
    Right(df)
  } else {
    Left(s"Missing required fields: ${missingFields.map(_.name).mkString(", ")}")
  }
}