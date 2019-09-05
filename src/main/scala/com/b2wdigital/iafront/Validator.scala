package com.b2wdigital.iafront

import org.apache.spark.sql.{SaveMode, SparkSession}
import com.b2wdigital.iafront.data.aws._
import com.b2wdigital.iafront.data.gcp._

object Validator extends App {

  val spark =
    if(args.length > 1){
      SparkSession.builder.master(args(1)).getOrCreate()
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
    .save("/tmp/result")

  spark.close
}
