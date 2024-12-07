// File: 6_QLearning.scala
// Purpose: QLearning


class QLearning(
  numStates: Int,
  numActions: Int,
  learningRate: Double,
  discountFactor: Double
) {
  private val qTable = DenseMatrix.zeros[Double](numStates, numActions)
  
  def selectAction(state: Int, epsilon: Double): Int = {
    if (util.Random.nextDouble() < epsilon) {
      // Exploration
      util.Random.nextInt(numActions)
    } else {
      // Exploitation
      argmax(qTable(state, ::).inner)
    }
  }
  
  def update(
    state: Int,
    action: Int,
    reward: Double,
    nextState: Int
  ): Unit = {
    val oldValue = qTable(state, action)
    val nextMax = max(qTable(nextState, ::).inner)
    
    qTable(state, action) = oldValue + learningRate * (
      reward + discountFactor * nextMax - oldValue
    )
  }
}
