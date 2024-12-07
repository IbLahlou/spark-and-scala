// 5. Delta Lake Operations
import io.delta.tables._

// Upsert (merge) operation
val deltaTable = DeltaTable.forPath(spark, "path/to/delta-table")

deltaTable.merge(
  updatesDF.as("updates"),
  "target.id = updates.id"
)
.whenMatched()
.updateAll()
.whenNotMatched()
.insertAll()
.execute()