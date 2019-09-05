package com.b2wdigital.iafront.data.configuration

import com.b2wdigital.iafront.exceptions.ConfigurationEnvNotFoundException
import org.apache.spark.sql.SparkSession

private [data] object ConfigurationUtils {

  def setHadoopConf(namespace:String, envName:String)(implicit sparkSession: SparkSession):String = {
    sys.env.get(envName) match {
      case Some(value) =>
        sparkSession
          .sparkContext
          .hadoopConfiguration
          .set(namespace, value)

        value
      case None => throw new ConfigurationEnvNotFoundException(envName)
    }
  }

  def setSparkConf(namespace:String, envName:String, raises:Boolean=false)(implicit sparkSession: SparkSession):Option[String] = {
    sys.env.get(envName) match {
      case Some(value) =>
        sparkSession
          .conf
          .set(namespace, value)
        Some(value)
      case None =>
        if(raises) throw  new ConfigurationEnvNotFoundException(envName)
        None
    }
  }
}
