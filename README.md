# Spark Scala Tutorial Project

## Introduction
This is a tutorial project demonstrating the use of Apache Spark with Scala for parallel computing in a Kubernetes environment.

## Prerequisites
- Kubernetes 1.23+
- Helm 3.8.0+
- Java 11
- Git
- Linux distribution (Debian-based)
- Scala (sbt)

## Installation Guide

### 1. Install Java 11
```bash
sudo apt update
sudo apt install openjdk-11-jdk
```
Verify installation:
```bash
java -version
```

### 2. Install Scala Build Tool (sbt)
```bash
# Add SBT repository
echo "deb https://repo.scala-sbt.org/scalasbt/debian all main" | sudo tee /etc/apt/sources.list.d/sbt.list
echo "deb https://repo.scala-sbt.org/scalasbt/debian /" | sudo tee /etc/apt/sources.list.d/sbt_old.list

# Add SBT repository key
curl -sL "https://keyserver.ubuntu.com/pks/lookup?op=get&search=0x2EE0EA64E40A89B84B2DF73499E82A75642AC823" | sudo apt-key add

# Update and install
sudo apt update
sudo apt install sbt
```

### 3. Install Spark on Kubernetes using Helm
```bash
# Add Bitnami repository
helm repo add bitnami https://charts.bitnami.com/bitnami

# Install Spark
helm install my-spark bitnami/spark --version 9.2.14
```

## Project Structure
```
spark-scala-project/
├── build.sbt
├── project/
│   └── build.properties
├── src/
│   └── main/
│       └── scala/
│           └── com/
│               └── example/
│                   └── SimpleApp.scala
├── run.sh
└── deploy-to-k8s.sh
```

## Configuration

### build.sbt
```scala
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
```

## Running the Application

1. Make sure your Kubernetes cluster is running:
```bash
kubectl get pods
```

2. Deploy your application:
```bash
# Make scripts executable
chmod +x run.sh deploy-to-k8s.sh

# Deploy JAR to cluster
./deploy-to-k8s.sh

# Run the application
./run.sh
```

## Verifying the Installation

### Check Spark Cluster
```bash
kubectl get pods
```
You should see pods named `my-spark-master-0` and `my-spark-worker-0`.

### Access Spark UI
```bash
kubectl port-forward svc/my-spark-master-svc 8080:80
```
Then visit `http://localhost:8080` in your browser.

## Troubleshooting

### Common Issues

1. Pod Status Pending
```bash
kubectl describe pod my-spark-master-0
```
Check for resource constraints or volume mount issues.

2. Java Version Mismatch
```bash
java -version
```
Ensure you're using Java 11.

3. SBT Compilation Issues
```bash
sbt clean
sbt compile
```
Try cleaning and recompiling the project.

## Additional Resources
- [Apache Spark Documentation](https://spark.apache.org/docs/latest/)
- [Scala Documentation](https://docs.scala-lang.org/)
- [Kubernetes Documentation](https://kubernetes.io/docs/)
- [Helm Documentation](https://helm.sh/docs/)

## Contributing
Feel free to submit issues and enhancement requests!

