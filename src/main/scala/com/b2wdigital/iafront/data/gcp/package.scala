package com.b2wdigital.iafront.data

import com.b2wdigital.iafront.data.configuration.ConfigurationUtils._
import org.apache.spark.sql.{DataFrame, DataFrameReader, SparkSession}

package object gcp {

  implicit class GCPConfig(sparkSession: SparkSession) {
    private val hadoopConfig = sparkSession.sparkContext.hadoopConfiguration

    def setupGCP:Unit = {
      hadoopConfig.set("google.cloud.auth.service.account.enable", "true")
      setHadoopConf("fs.gs.project.id", "GCP_PROJECT_ID")(sparkSession)

      setHadoopConf("google.cloud.auth.service.account.json.keyfile", "GOOGLE_APPLICATION_CREDENTIALS_FILE")(sparkSession)

      hadoopConfig.set("fs.gs.inputstream.support.gzip.encoding.enable", "true")
      hadoopConfig.set("fs.gs.impl", "com.google.cloud.hadoop.fs.gcs.GoogleHadoopFileSystem")
      hadoopConfig.set("fs.AbstractFileSystem.gs.impl", "com.google.cloud.hadoop.fs.gcs.GoogleHadoopFS")
    }
  }
}