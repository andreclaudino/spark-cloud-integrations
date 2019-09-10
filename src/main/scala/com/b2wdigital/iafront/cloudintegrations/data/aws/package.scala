package com.b2wdigital.iafront.data

import com.b2wdigital.iafront.data.configuration.ConfigurationUtils.{setSparkConf, setHadoopConf}
import org.apache.spark.sql.SparkSession

package object aws {

  implicit class AthenaConfig(sparkSession: SparkSession) {

    def setupAWS:Unit = {
      sparkSession.conf.set("spark.hadoop.fs.s3a.impl", "org.apache.hadoop.fs.s3a.S3AFileSystem")
      sparkSession.conf.set("spark.hadoop.fs.s3.impl", "org.apache.hadoop.fs.s3aS3FileSystem")
//      sparkSession.conf.set("spark.hadoop.fs.s3a.fast.upload", "true")
//      sparkSession.conf.set("spark.hadoop.mapreduce.fileoutputcommitter.algorithm.version", "2")

      setSparkConf("spark.hadoop.fs.s3a.access.key", "AWS_ACCESS_KEY_ID")(sparkSession)
      setSparkConf("spark.hadoop.fs.s3a.secret.key", "AWS_SECRET_ACCESS_KEY")(sparkSession)

      setSparkConf("spark.hadoop.fs.s3a.awsAccessKeyId", "AWS_ACCESS_KEY_ID")(sparkSession)
      setSparkConf("spark.hadoop.fs.s3a.sawsSecretAccessKey", "AWS_SECRET_ACCESS_KEY")(sparkSession)

      setSparkConf("spark.hadoop.fs.s3.access.key", "AWS_ACCESS_KEY_ID")(sparkSession)
      setSparkConf("spark.hadoop.fs.s3.secret.key", "AWS_SECRET_ACCESS_KEY")(sparkSession)

      setSparkConf("spark.hadoop.fs.s3.awsAccessKeyId", "AWS_ACCESS_KEY_ID")(sparkSession)
      setSparkConf("spark.hadoop.fs.s3.sawsSecretAccessKey", "AWS_SECRET_ACCESS_KEY")(sparkSession)
    }
  }

}
