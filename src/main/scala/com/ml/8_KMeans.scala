// File: 8_KMeans.scala
// Purpose: KMeans



class KMeans(k: Int, maxIterations: Int = 100) {
  private var centroids: Array[DenseVector[Double]] = _
  
  def fit(data: Array[DenseVector[Double]]): Array[Int] = {
    // Initialize centroids randomly
    centroids = Array.fill(k) {
      data(util.Random.nextInt(data.length))
    }
    
    var changed = true
    var iteration = 0
    var assignments = Array.empty[Int]
    
    while (changed && iteration < maxIterations) {
      val newAssignments = data.map(point => 
        findClosestCentroid(point)
      )
      
      changed = !assignments.sameElements(newAssignments)
      assignments = newAssignments
      
      // Update centroids
      updateCentroids(data, assignments)
      iteration += 1
    }
    
    assignments
  }
  
  private def findClosestCentroid(point: DenseVector[Double]): Int = {
    centroids.indices.minBy(i => 
      breeze.linalg.norm(point - centroids(i))
    )
  }
  
  private def updateCentroids(
    data: Array[DenseVector[Double]],
    assignments: Array[Int]
  ): Unit = {
    for (i <- centroids.indices) {
      val cluster = data.zip(assignments)
        .filter(_._2 == i)
        .map(_._1)
      
      if (cluster.nonEmpty) {
        centroids(i) = cluster.reduce(_ + _) / cluster.length.toDouble
      }
    }
  }
}
