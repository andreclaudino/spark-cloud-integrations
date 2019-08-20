package com.b2wdigital.iafront.data.configuration

import org.apache.spark.sql.SparkSession

private [data] object ConfigurationUtils {

  def envOrSparkConf(name:String, namespace:Option[String]=None)(implicit sparkSession: SparkSession):Option[String] = {
    val envName = name.toUpperCase.replace('.', '_')

    sys.env.get(envName) match {
      case None => getSparkConf(namespace, name)
      case result => result
    }
  }

  def getSparkConf(namespace:Option[String], key:String)(implicit sparkSession: SparkSession):Option[String] = {

    val keyConfig =
      namespace match {
        case Some(n) => s"$n.$key"
        case None => key
      }

    sparkSession.sparkContext.getConf.getOption(keyConfig)
  }
}
