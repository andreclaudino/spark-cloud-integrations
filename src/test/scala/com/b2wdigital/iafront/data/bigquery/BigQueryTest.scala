package com.b2wdigital.iafront.data.bigquery

import com.b2wdigital.iafront.data.SparkSessionTestWrapper
import org.scalatest.{FlatSpec, Matchers}

class BigQueryTest extends FlatSpec with Matchers with SparkSessionTestWrapper {

  "Big query" should "load stack overflow public dataset" in {
    val datasetName ="bigquery-public-data:stackoverflow.posts_questions"
    spark.setupGCP

    val fieldName =
      spark
        .read
        .bigQueryTable(datasetName)
        .select("creation_date")
        .schema
        .fields(0)
        .name

    fieldName shouldBe "creation_date"
  }

  "Big query" should "load stack frete.visita dataset" in {
    val datasetName ="master-sector-247616:frete.pedidos_para_clusterizadao"
    spark.setupGCP

    val fieldName =
      spark
        .read
        .bigQueryTable(datasetName)
        .select("CEPS")
        .schema
        .fields(0)
        .name

    fieldName shouldBe "CEPS"
  }
}
