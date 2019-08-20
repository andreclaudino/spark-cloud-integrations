name := "spark-cloud-integrations"
organization := "com.b2wdigital.iafront"
scalaVersion := "2.11.12"

version := "0.1"

val sparkVersion = "2.4.3"

resolvers ++= Seq(
  "Artima Maven Repository" at "http://repo.artima.com/releases",
  "Palantir" at "https://dl.bintray.com/palantir/releases",
  "Palantir" at "https://dl.bintray.com/palantir/releases",
  Resolver.sonatypeRepo("releases")
)

libraryDependencies ++=Seq(
  "org.apache.spark" %% "spark-sql"       % sparkVersion % "provided",
  "org.apache.spark" %% "spark-core"      % sparkVersion % "provided",
  "org.apache.spark" %% "spark-hadoop-cloud" % "2.4.0-palantir.38",
  "com.github.samelamin" %% "spark-bigquery" % "0.2.7-SNAPSHOT",
  // Test
  "org.scalatest" %% "scalatest" % "3.0.8" % "test"
)

assemblyMergeStrategy in assembly := {
  case PathList("javax", "servlet", xs @ _*) => MergeStrategy.last
  case PathList("javax", "activation", xs @ _*) => MergeStrategy.last
  case PathList("org", "apache", xs @ _*) => MergeStrategy.last
  case PathList("com", "google", xs @ _*) => MergeStrategy.last
  case PathList("com", "esotericsoftware", xs @ _*) => MergeStrategy.last
  case PathList("com", "codahale", xs @ _*) => MergeStrategy.last
  case PathList("com", "yammer", xs @ _*) => MergeStrategy.last
  case PathList("com", "amazonaws", xs @ _*) => MergeStrategy.last
  case PathList("com", "syncron", xs @ _*) => MergeStrategy.last
  case PathList("javax", "inject", xs @ _*) => MergeStrategy.last
  case PathList("javax", "ws", xs @ _*) => MergeStrategy.last
  case PathList("org", "aopalliance", xs @ _*) => MergeStrategy.last
  case PathList("org", "fusesource", xs @ _*) => MergeStrategy.last
  case PathList("com", "sun", xs @ _*) => MergeStrategy.last
  case "about.html" => MergeStrategy.last
  case "META-INF/ECLIPSEF.RSA" => MergeStrategy.last
  case "META-INF/mailcap" => MergeStrategy.last
  case "META-INF/mimetypes.default" => MergeStrategy.last
  case "plugin.properties" => MergeStrategy.last
  case "log4j.properties" => MergeStrategy.last
  case "mozilla/public-suffix-list.txt" => MergeStrategy.last
  case x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}

// Configurações para execução
run in Compile :=
  Defaults.runTask(fullClasspath in Compile, mainClass in (Compile, run), runner in (Compile, run)).evaluated
runMain in Compile := Defaults.runMainTask(fullClasspath in Compile, runner in(Compile, run)).evaluated