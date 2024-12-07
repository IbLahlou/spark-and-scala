// File: 3_ConvolutionalNN.scala
// Purpose: ConvolutionalNN



case class ConvLayer(
  kernelSize: Int,
  inChannels: Int,
  outChannels: Int,
  stride: Int = 1
) {
  val kernels = Array.fill(outChannels, inChannels) {
    DenseMatrix.rand[Double](kernelSize, kernelSize)
  }
  
  def forward(input: Array[DenseMatrix[Double]]): Array[DenseMatrix[Double]] = {
    val (h, w) = (input(0).rows, input(0).cols)
    val outHeight = (h - kernelSize) / stride + 1
    val outWidth = (w - kernelSize) / stride + 1
    
    Array.tabulate(outChannels) { outChan =>
      DenseMatrix.tabulate[Double](outHeight, outWidth) { (i, j) =>
        val receptiveField = for {
          ic <- 0 until inChannels
          ki <- 0 until kernelSize
          kj <- 0 until kernelSize
        } yield input(ic)(i * stride + ki, j * stride + kj) * kernels(outChan)(ic)(ki, kj)
        
        receptiveField.sum
      }
    }
  }
}
