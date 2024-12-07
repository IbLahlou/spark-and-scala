// File: 9_GeneticAlgorithm.scala
// Purpose: GeneticAlgorithm



case class Individual(genes: Array[Double], fitness: Double)

class GeneticAlgorithm(
  populationSize: Int,
  geneLength: Int,
  mutationRate: Double
) {
  private var population: Array[Individual] = _
  
  def initialize(fitnessFunction: Array[Double] => Double): Unit = {
    population = Array.fill(populationSize) {
      val genes = Array.fill(geneLength)(util.Random.nextDouble())
      Individual(genes, fitnessFunction(genes))
    }
  }
  
  def evolve(generations: Int, fitnessFunction: Array[Double] => Double): Individual = {
    for (_ <- 1 to generations) {
      // Selection
      val parents = selectParents()
      
      // Crossover
      val offspring = crossover(parents)
      
      // Mutation
      mutate(offspring)
      
      // Evaluate new population
      population = offspring.map(genes => 
        Individual(genes, fitnessFunction(genes))
      )
    }
    
    population.maxBy(_.fitness)
  }
  
  private def selectParents(): Array[Array[Double]] = {
    // Tournament selection
    Array.fill(populationSize) {
      val tournament = Array.fill(3)(
        population(util.Random.nextInt(populationSize))
      )
      tournament.maxBy(_.fitness).genes
    }
  }
  
  private def crossover(
    parents: Array[Array[Double]]
  ): Array[Array[Double]] = {
    parents.grouped(2).flatMap { pair =>
      if (pair.length == 2) {
        val crossoverPoint = util.Random.nextInt(geneLength)
        Array(
          pair(0).take(crossoverPoint) ++ pair(1).drop(crossoverPoint),
          pair(1).take(crossoverPoint) ++ pair(0).drop(crossoverPoint)
        )
      } else {
        Array(pair(0))
      }
    }.toArray
  }
  
  private def mutate(population: Array[Array[Double]]): Unit = {
    for {
      individual <- population
      i <- individual.indices
      if util.Random.nextDouble() < mutationRate
    } {
      individual(i) = util.Random.nextDouble()
    }
  }
}
