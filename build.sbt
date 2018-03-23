name := "scala-jsoup"

version := "1.0"

scalaVersion := "2.12.5"

unmanagedBase <<= baseDirectory { base => base / "libs" }

//libraryDependencies += "org.jsoup" % "jsoup" % "1.11.2"

libraryDependencies ++= Seq(
  "mysql" % "mysql-connector-java" % "6.0.6",
  "org.jsoup" % "jsoup" % "1.11.2",
  "com.typesafe.slick" %% "slick" % "3.2.2",
  "com.typesafe.slick" %% "slick-codegen" % "3.2.2",
  "org.slf4j" % "slf4j-nop" % "1.7.25"
)