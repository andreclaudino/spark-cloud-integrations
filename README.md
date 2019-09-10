# Spark Cloud Integrations

Uma biblioteca [Spark](https://spark.apache.org/) para integrar com `GCP`e `AWS`.

Atualmente, a biblioteca só suporta integrações com `S3` e `Google Storage`, mas há planos de suporte a a [*Athena*](https://github.com/B2W-BIT/athena-spark-driver) e `BigQuery`. 

## Preparação

Para usar a biblioteca é preciso que as seguintes variáveis de ambiente estejam disponíveis:

* `GCP_PROJECT_ID`: Id do projeto GCP que contém os recursos
* `GOOGLE_APPLICATION_CREDENTIALS_FILE`: JSON de autorização para conta de serviço, instruções podem ser obtidas [aqui](https://cloud.google.com/docs/authentication/getting-started)
* `AWS_ACCESS_KEY_ID`: Chave de acesso a AWS
* `AWS_SECRET_ACCESS_KEY` Chave secreta de acesso a AWS

## Como usar:

Primeiro passo é importar os implicitos que configuram a sessão do spark

```scala
import com.b2wdigital.iafront.cloudintegrations.data.gcp._
import com.b2wdigital.iafront.cloudintegrations.data.aws._
```

Cria-se uma sessão spark

```scala
import org.apache.spark.sql.SparkSession

val spark = SparkSession.builder.master("spark://master:7077").getOrCreate()
```

A sessão deve então ser configurada para GCP e/ou AWS (usará as variáveis de ambiente configuradas)

```scala
spark.setupGCP
spark.setupAWS
```

Com isso, já será possível ler e escrever de protocolos `s3a://` e `gs:///`.

```scala
val s3df = spark.read.json("s3a://bucket/data/")
val gsdf = spark.read.json("gs://bucket/data/")
```

## Spark

Para utilizar é preciso incluir a biblioteca [gcs-connector-hadoop2-latest](https://storage.googleapis.com/hadoop-lib/gcs/gcs-connector-hadoop2-latest.jar) no classpath. 