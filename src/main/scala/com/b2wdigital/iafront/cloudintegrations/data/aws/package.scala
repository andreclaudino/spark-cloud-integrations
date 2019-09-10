package com.b2wdigital.iafront.cloudintegrations.data

import com.b2wdigital.iafront.cloudintegrations.data.configuration.ConfigurationUtils.setSparkConf
import org.apache.spark.sql.SparkSession

package object aws {

  implicit class AthenaConfig(sparkSession: SparkSession) {

    def setupAWS:Unit = {
      setSparkConf("spark.hadoop.fs.s3a.access.key", "AWS_ACCESS_KEY_ID")(sparkSession)
      setSparkConf("spark.hadoop.fs.s3a.secret.key", "AWS_SECRET_ACCESS_KEY")(sparkSession)

      setSparkConf("spark.hadoop.fs.s3a.awsAccessKeyId", "AWS_ACCESS_KEY_ID")(sparkSession)
      setSparkConf("spark.hadoop.fs.s3a.sawsSecretAccessKey", "AWS_SECRET_ACCESS_KEY")(sparkSession)

      sparkSession.conf.set("spark.hadoop.fs.s3a.impl", "org.apache.hadoop.fs.s3a.S3AFileSystem")
    }
  }

}
