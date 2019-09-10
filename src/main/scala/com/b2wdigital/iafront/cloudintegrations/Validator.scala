package com.b2wdigital.iafront.cloudintegrations

import org.apache.spark.sql.{SaveMode, SparkSession}
import com.b2wdigital.iafront.cloudintegrations.data.gcp._
import com.b2wdigital.iafront.cloudintegrations.data.aws._

object Validator extends App {

  val spark =
    if(args.length > 2){
      SparkSession.builder.master(args(2)).getOrCreate()
    } else {
      SparkSession.builder.getOrCreate()
    }

  spark.setupGCP
  spark.setupAWS

  val df =
    spark
      .read
      .json(args(0))
        .select("iddepartamento", "idlinha", "calculo_datahora", "cep")
  df.printSchema
  df.show

  df
    .write
    .partitionBy("iddepartamento", "idlinha")
    .format("json")
    .mode(SaveMode.Append)
    .save(args(1))

  spark.close
}
