// File: 7_WordEmbedding.scala
// Purpose: WordEmbedding


class Word2Vec(
  vocabularySize: Int,
  embeddingDim: Int,
  contextSize: Int
) {
  private val embeddings = DenseMatrix.rand[Double](vocabularySize, embeddingDim)
  private val contextVectors = DenseMatrix.rand[Double](vocabularySize, embeddingDim)
  
  def trainWord(
    targetWordIdx: Int,
    contextWordIndices: Array[Int],
    learningRate: Double
  ): Unit = {
    val targetEmbedding = embeddings(targetWordIdx, ::).inner
    
    for (contextIdx <- contextWordIndices) {
      val contextVector = contextVectors(contextIdx, ::).inner
      val prediction = targetEmbedding dot contextVector
      val error = sigmoid(prediction) - 1.0
      
      // Update vectors using gradient descent
      embeddings(targetWordIdx, ::) := embeddings(targetWordIdx, ::) - 
        (contextVector * error * learningRate).t
      contextVectors(contextIdx, ::) := contextVectors(contextIdx, ::) - 
        (targetEmbedding * error * learningRate).t
    }
  }
}
