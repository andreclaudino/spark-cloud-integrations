package com.b2wdigital.iafront.data

import com.b2wdigital.iafront.data.configuration.ConfigurationUtils.envOrSparkConf
import org.apache.spark.sql.SparkSession

package object athena {

  implicit class AthenaConfig(sparkSession: SparkSession) {
    private val namespace:Option[String] = Some("com.b2wdigital.iafront.data.aws")

    private val hadoopConfig = sparkSession.sparkContext.hadoopConfiguration

    def setupAWS:Unit = {

      hadoopConfig.set("fs.s3a.impl", "org.apache.hadoop.fs.s3a.S3AFileSystem ")
      hadoopConfig.set("fs.s3a.impl", "org.apache.hadoop.fs.s3a.S3AFileSystem")

      val awsAccessKeyId = envOrSparkConf("aws_access_key_id", namespace)(sparkSession)
      if(awsAccessKeyId.isDefined) {
        hadoopConfig.set("fs.s3a.access.key", awsAccessKeyId.get)
        hadoopConfig.set("fs.s3a.awsAccessKeyId", awsAccessKeyId.get)
      }

      val awsSecretAccessKey = envOrSparkConf("aws__secret_access_key", namespace)(sparkSession)
      if(awsSecretAccessKey.isDefined) {
        hadoopConfig.set("fs.s3a.secret.key", awsAccessKeyId.get)
        hadoopConfig.set("fs.s3a.awsSecretAccessKey", awsAccessKeyId.get)
      }
    }
  }

}
