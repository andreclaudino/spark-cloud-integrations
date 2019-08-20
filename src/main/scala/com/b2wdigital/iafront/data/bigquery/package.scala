package com.b2wdigital.iafront.data

import com.b2wdigital.iafront.data.configuration.ConfigurationUtils._
import com.b2wdigital.iafront.exceptions.InvalidTableNameException
import com.google.cloud.hadoop.io.bigquery.BigQueryConfiguration
import com.samelamin.spark.bigquery._
import org.apache.hadoop.conf.Configuration
import org.apache.spark.sql.{DataFrame, DataFrameReader, SparkSession}

package object bigquery {

  private val namespace:Option[String] = Some("com.b2wdigital.iafront.data.gcp")

  implicit class BigQueryReaderExtensions(reader:DataFrameReader) {

    def bigQueryTable(table:String):DataFrame = {

      reader
        .option("tableReferenceSource", table)
        .format("com.samelamin.spark.bigquery")
        .load()
    }
  }

  implicit class BigQueryWriterExtensions(dataFrame:DataFrame)(implicit sparkSession:SparkSession) {

    private val hadoopConfig = sparkSession.sparkContext.hadoopConfiguration

    def bigQueryTable(table:String) = {
      dataFrame
        .saveAsBigQueryTable(tableNameWithProject(hadoopConfig: Configuration, table: String))
    }
  }

  implicit class BigQueryConfig(sparkSession: SparkSession) {
    private val sqlContext = sparkSession.sqlContext
    private val hadoopConfig = sparkSession.sparkContext.hadoopConfiguration

    def setupGCP:Unit = {
      hadoopConfig.set("fs.gs.impl", "com.google.cloud.hadoop.fs.gcs.GoogleHadoopFileSystem")
      hadoopConfig.set("fs.AbstractFileSystem.gs.impl", "com.google.cloud.hadoop.fs.gcs.GoogleHadoopFS")

      val jsonKeyFile = envOrSparkConf("json_key_file", namespace)(sparkSession)
      if(jsonKeyFile.isDefined) {
        sqlContext.setGcpJsonKeyFile(jsonKeyFile.get)
      }

      val gcpProject = envOrSparkConf("gcp_project_id", namespace)(sparkSession)
      if(gcpProject.isDefined) {
        sqlContext.setBigQueryProjectId(gcpProject.get)
        sqlContext.hadoopConf.set("fs.gs.project.id", gcpProject.get)
      }

      val gcsBucket = envOrSparkConf("gcs_bucket", namespace)(sparkSession)
      if(gcsBucket.isDefined){
        sqlContext.setBigQueryGcsBucket(gcsBucket.get)
      }

      sqlContext.setAllowSchemaUpdates()
    }
  }

  def tableNameWithProject(hadoopConfig: Configuration, table: String):String = {
    table.split(':') match {
      case Array(_, _) => table
      case Array(tableWithoutProject) =>
        s"${hadoopConfig.get(BigQueryConfiguration.PROJECT_ID_KEY)}:${tableWithoutProject}"
      case _ => throw new InvalidTableNameException(table)
    }
  }

}
