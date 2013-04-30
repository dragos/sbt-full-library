//sbtVersion := "0.12.0"

organization := "org.scala-ide"

name := "org.scala-ide.sbt.full.library"

version := "2.1.0-SNAPSHOT"

resolvers += "Typesafe IDE" at "http://repo.typesafe.com/typesafe/ivy-releases"

libraryDependencies ++= Seq(
    "org.scala-sbt" % "compiler-interface" % "0.12.2",
    "org.scala-sbt" % "incremental-compiler" % "0.12.2",
    "org.scala-sbt" % "api" % "0.12.2",
    "org.scala-sbt" % "persist" % "0.12.2",
    "org.scala-sbt" % "classfile" % "0.12.2",
    "org.scala-sbt" % "compile" % "0.12.2"
)

osgiSettings

OsgiKeys.embeddedJars <<= Keys.externalDependencyClasspath in Compile map { deps =>
  (deps filter { dep => !(dep.data.getName startsWith "scala-") }
        map { _.data })
  }

OsgiKeys.exportPackage := Seq("sbt.*")
