name := "spark-scala-project"
version := "0.1"
scalaVersion := "2.12.15"

// Explicitly set Java version
javacOptions ++= Seq("-source", "11", "-target", "11")

// Spark dependencies
libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "3.5.3",
  "org.apache.spark" %% "spark-sql" % "3.5.3"
)

// Add additional settings for Java 11 compatibility
scalacOptions ++= Seq("-release", "11")
