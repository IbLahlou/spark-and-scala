// 6. Error Handling and Validation
def processWithValidation[T](data: Dataset[T])(process: Dataset[T] => Dataset[T]): Either[String, Dataset[T]] = {
  try {
    val processed = process(data)
    if (processed.isEmpty) {
      Left("Processing resulted in empty dataset")
    } else {
      Right(processed)
    }
  } catch {
    case e: Exception => Left(s"Processing failed: ${e.getMessage}")
  }
}