// File: 1_NeuralNetwork.scala
// Purpose: NeuralNetwork



import breeze.linalg._
import breeze.numerics._

class NeuralNetwork(layers: Array[Int]) {
  private val weights = layers.zip(layers.tail).map { case (in, out) =>
    DenseMatrix.rand[Double](out, in)
  }
  
  private val biases = layers.tail.map(size => DenseVector.rand[Double](size))
  
  def forward(input: DenseVector[Double]): DenseVector[Double] = {
    weights.zip(biases).foldLeft(input) { case (act, (w, b)) =>
      sigmoid(w * act + b)
    }
  }
  
  def sigmoid(x: DenseVector[Double]): DenseVector[Double] = {
    x.map(xi => 1.0 / (1.0 + math.exp(-xi)))
  }
}
