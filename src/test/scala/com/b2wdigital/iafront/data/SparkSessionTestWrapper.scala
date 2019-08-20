package com.b2wdigital.iafront.data

import org.apache.spark.sql.SparkSession
import org.apache.log4j.Logger
import org.apache.log4j.Level

trait SparkSessionTestWrapper {

  Logger.getLogger("org").setLevel(Level.OFF)
  Logger.getLogger("akka").setLevel(Level.OFF)

  implicit lazy val spark:SparkSession =
    SparkSession
      .builder()
      .config("log4j.rootCategory", "ERROR,console")
      .master("local[*]")
      .getOrCreate()
}
