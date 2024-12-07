#!/bin/bash

# Check Java version
java_version=$(java -version 2>&1 | awk -F '"' '/version/ {print $2}' | cut -d'.' -f1)
if [ "$java_version" != "11" ]; then
    echo "Warning: Current Java version is $java_version. This project is configured for Java 11."
    echo "Please ensure you're using Java 11 for optimal compatibility."
    read -p "Do you want to continue anyway? (y/n) " -n 1 -r
    echo
    if [[ ! $REPLY =~ ^[Yy]$ ]]; then
        exit 1
    fi
fi

echo "Compiling and running Spark Scala application..."

# Compile the project
sbt package

# Get the Kubernetes master URL
KUBE_MASTER=$(kubectl cluster-info | grep -E 'Kubernetes control plane' | awk '/http/ {print $NF}')

# Submit to Kubernetes cluster using kubectl
kubectl exec -it my-spark-master-0 -- \
  spark-submit \
  --master spark://my-spark-master-svc:7077 \
  --deploy-mode cluster \
  --name "Simple-Spark-App" \
  --class com.example.SimpleApp \
  --conf "spark.driver.extraJavaOptions=-Dio.netty.tryReflectionSetAccessible=true" \
  --conf "spark.executor.extraJavaOptions=-Dio.netty.tryReflectionSetAccessible=true" \
  local:///opt/spark-apps/spark-scala-project_2.12-0.1.jar