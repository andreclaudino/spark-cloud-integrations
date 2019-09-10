name := "spark-cloud-integrations"
organization := "com.b2wdigital.iafront"
scalaVersion := "2.11.12"

version := "0.1-SNAPSHOT"

val sparkVersion = "2.4.4"

resolvers ++= Seq(
  Resolver.sonatypeRepo("releases")
)

libraryDependencies ++=Seq(
  "org.apache.spark" %% "spark-sql" % sparkVersion % "provided" exclude("commons-beanutils", "commons-beanutils"),
  "org.apache.spark" %% "spark-core" % sparkVersion % "provided" exclude("commons-beanutils", "commons-beanutils"),

  "com.google.cloud.bigdataoss" % "gcs-connector" % "hadoop2-2.0.0" % "provided"
    exclude("javax.jms", "jms")
    exclude("com.sun.jdmk", "jmxtools")
    exclude("com.sun.jmx", "jmxri")
    exclude("org.apache.hadoop", "hadopp-common"),

  "com.amazonaws" % "aws-java-sdk" % "1.7.4",
  "org.apache.hadoop" % "hadoop-aws" % "2.7.3",
  "commons-beanutils" % "commons-beanutils" % "1.9.4"
)

/// Configurações para execução
run in Compile :=
  Defaults.runTask(fullClasspath in Compile, mainClass in (Compile, run), runner in (Compile, run)).evaluated
runMain in Compile := Defaults.runMainTask(fullClasspath in Compile, runner in(Compile, run)).evaluated


/// Deploy
val nexusRepository = sys.env.getOrElse("NEXUS_REPOSITORY", "")
val username = sys.env.getOrElse("DEPLOY_USER", "")
val password = sys.env.getOrElse("DEPLOY_PASSWORD", "")

publishConfiguration := publishConfiguration.value.withOverwrite(true)
publishLocalConfiguration := publishLocalConfiguration.value.withOverwrite(true)
credentials += Credentials("Sonatype Nexus Repository Manager", nexusRepository, username, password)
publishTo := Some("snapshots" at "http://" + nexusRepository + "/repository/maven-private/")
publishMavenStyle := true

/// Gestão de conflitos
assemblyMergeStrategy in assembly := {
  case PathList("org", "apache", "commons", xs @ _*) => MergeStrategy.last
  case x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}

artifact in (Compile, assembly) := {
  val art = (artifact in (Compile, assembly)).value
  art.withClassifier(Some("assembly"))
}

// Configurações de publicação no S3
publishConfiguration := publishConfiguration.value.withOverwrite(true)
publishLocalConfiguration := publishLocalConfiguration.value.withOverwrite(true)
val mlFreightRepository = "s3://s3-us-east-1.amazonaws.com/ml-freight/applications/"
publishMavenStyle := false
publishTo := Some(Resolver.url("S3 ML freight Artifacts", url(mlFreightRepository))(Resolver.ivyStylePatterns))