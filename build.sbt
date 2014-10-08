name := """skalholt-core"""

version := "0.1.1"

scalaVersion := "2.11.1"

organization := "skalholt"

mainClass in Compile := Some("Skalholt")

libraryDependencies ++= List(
  "com.typesafe.slick" %% "slick" % "2.1.0",
  "com.typesafe.slick" %% "slick-codegen" % "2.1.0",
  "com.h2database" % "h2" % "1.3.175",
  "postgresql" % "postgresql" % "9.1-901.jdbc4",
  "org.scalatest" %% "scalatest" % "2.1.6" % "test",
  "org.specs2" %% "specs2" % "2.3.12",
  "com.typesafe" % "config" % "1.2.1",
  "com.typesafe.scala-logging" %% "scala-logging-slf4j" % "2.1.1",
  "org.slf4j" % "slf4j-api" % "1.7.1",
  "ch.qos.logback" % "logback-classic" % "1.0.7",
   "com.typesafe.akka" %% "akka-actor" % "2.3.3"
)
