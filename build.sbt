
organization := "org.scala-ide"

name := "org.scala-ide.sbt.full.library"

version := "2.1.0-SNAPSHOT"

//resolvers += "Typesafe IDE Repo for 2.10" at "http://repo.typesafe.com/typesafe/ide-2.10"

libraryDependencies ++= Seq(
    "org.scala-sbt" % "classpath" % "0.12.2",
    "org.scala-sbt" % "logging" % "0.12.2"
)

osgiSettings

OsgiKeys.embeddedJars <<= Keys.externalDependencyClasspath in Compile map { deps =>  deps map (_.data) }

OsgiKeys.exportPackage := Seq("sbt.*")

