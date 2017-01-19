lazy val emailListAdder = (project in file("."))
  .settings(
    name := "email-list-adder",
    organization := "com.brianmccutchon",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := "2.12.0"
  )

libraryDependencies +=
  "org.scala-lang.modules" % "scala-swing_2.12" % "2.0.0-M2"
