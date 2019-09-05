package com.b2wdigital.iafront.data

import com.b2wdigital.iafront.data.configuration.ConfigurationUtils._
import org.apache.spark.sql.{DataFrame, DataFrameReader, SparkSession}

package object gcp {

  implicit class BigQueryReaderExtensions(reader:DataFrameReader) {

    def bigquery(table:String, billingProjectOption:Option[String]=None, dataProjectOption:Option[String]=None):DataFrame = {

      val project =
        dataProjectOption match {
          case None =>
            val Array(value, _) = table.split(":")
            value

          case Some(value) => value
        }

      val billingProject =
        billingProjectOption match {
          case None => project
          case Some(value) => value
        }

      reader
        .format("bigquery")
        .option("table", table)
        .option("project", project)
        .option("parentProject", billingProject)
        .load()
    }
  }

  implicit class BigQueryWriterExtensions(dataFrame:DataFrame)(implicit sparkSession:SparkSession) {

    def toBigQuery(table:String) = {
      dataFrame.write.format("bigquery").saveAsTable(table)
    }
  }

  implicit class BigQueryConfig(sparkSession: SparkSession) {
    private val hadoopConfig = sparkSession.sparkContext.hadoopConfiguration

    def setupGCP:Unit = {
      hadoopConfig.set("fs.gs.impl", "com.google.cloud.hadoop.fs.gcs.GoogleHadoopFileSystem")
      hadoopConfig.set("fs.AbstractFileSystem.gs.impl", "com.google.cloud.hadoop.fs.gcs.GoogleHadoopFS")
      hadoopConfig.set("google.cloud.auth.service.account.enable", "true")

      setHadoopConf("fs.gs.project.id", "GCP_PROJECT_ID")(sparkSession)
      setHadoopConf("google.cloud.auth.service.account.json.keyfile",
        "GOOGLE_APPLICATION_CREDENTIALS_FILE")(sparkSession)
    }
  }
}