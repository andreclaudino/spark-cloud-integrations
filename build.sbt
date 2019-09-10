name := "spark-cloud-integrations"
organization := "com.b2wdigital.iafront"
scalaVersion := "2.11.12"

version := "1.0"

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

/// Gestão de conflitos
assemblyMergeStrategy in assembly := {
  case PathList("org", "apache", "commons", xs @ _*) => MergeStrategy.last
  case x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}

/// Deploy
test in assembly := {}

artifact in (Compile, assembly) := {
  val art = (artifact in (Compile, assembly)).value
  art.withClassifier(Some("assembly"))
}
addArtifact(artifact in (Compile, assembly), assembly)

val nexusRepository = sys.env.getOrElse("NEXUS_REPOSITORY", "")
val username = sys.env.getOrElse("DEPLOY_USER", "")
val password = sys.env.getOrElse("DEPLOY_PASSWORD", "")

publishLocalConfiguration := publishLocalConfiguration.value.withOverwrite(true)
credentials += Credentials("Sonatype Nexus Repository Manager", nexusRepository, username, password)
publishMavenStyle := true
publishTo := Some("snapshots" at "http://" + nexusRepository + "/repository/maven-private/")
