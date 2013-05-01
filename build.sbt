//sbtVersion := "0.12.0"

organization := "org.scala-ide"

name := "org.scala-ide.sbt.full.library"

version := "2.1.0-SNAPSHOT"

resolvers += "Typesafe IDE" at "http://repo.typesafe.com/typesafe/ivy-releases"

libraryDependencies ++= Seq(
    "org.scala-sbt" % "compiler-interface" % "95.95.95" artifacts(Artifact("compiler-interface-bin")),
    "org.scala-sbt" % "incremental-compiler" % "95.95.95",
    "org.scala-sbt" % "api" % "95.95.95",
    "org.scala-sbt" % "persist" % "95.95.95",
    "org.scala-sbt" % "classfile" % "95.95.95",
    "org.scala-sbt" % "compile" % "95.95.95",
    "org.scala-sbt" % "compiler-integration" % "95.95.95"
)

osgiSettings

OsgiKeys.embeddedJars <<= (Keys.externalDependencyClasspath in Compile, Keys.baseDirectory in ThisBuild) map { (deps,base) =>
  deps flatMap { d => d.data.getName match {
    case s if (s.startsWith("scala-")) => None
    case s if (s.startsWith("compiler-interface-src")) => None
    case s if (s.startsWith("compiler-interface-bin")) =>
      val ren=s.replaceFirst("compiler-interface-bin","compiler-interface")
      println("Renaming "+s+" to "+ren)
      val dest=new java.io.File(base,"target/"+ren)
      if (!dest.isFile())
        sbt.IO.copyFile(d.data,dest,false)
      Some(dest)
    case _ => Some(d.data)
  }}}

OsgiKeys.exportPackage := Seq("sbt.*")
