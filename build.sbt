name := """skalholt-core"""

version := "0.1.2"

scalaVersion := "2.11.5"

organization := "skalholt"

mainClass in Compile := Some("Skalholt")

resolvers += "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases"

libraryDependencies ++= List(
  "com.typesafe.slick" %% "slick" % "2.1.0",
  "com.typesafe.slick" %% "slick-codegen" % "2.1.0",
  "com.h2database" % "h2" % "1.3.176",
  "postgresql" % "postgresql" % "9.1-901.jdbc4",
  "org.scalatest" %% "scalatest" % "2.2.4" % "test",
  "org.specs2" %% "specs2" % "2.4.16",
  "com.typesafe" % "config" % "1.2.1",
  "com.typesafe.scala-logging" %% "scala-logging-slf4j" % "2.1.2",
  "org.slf4j" % "slf4j-api" % "1.7.10",
  "ch.qos.logback" % "logback-classic" % "1.1.2",
   "com.typesafe.akka" %% "akka-actor" % "2.3.4"
)
