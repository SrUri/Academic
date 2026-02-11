ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.12"

libraryDependencies += "junit" % "junit" % "4.12" % Test

lazy val root = (project in file("."))
  .settings(
    name := "ScalaTAP"
  )

