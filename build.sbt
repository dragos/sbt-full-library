//sbtVersion := "0.12.0"

organization := "org.scala-ide"

name := "sbt.full.library"

version := "4.0.0-SNAPSHOT"

//resolvers += "Typesafe IDE" at "http://repo.typesafe.com/typesafe/ivy-releases"

resolvers += Resolver.url("Typesafe Ivy Releases", url("http://repo.typesafe.com/typesafe/ivy-releases"))(Resolver.ivyStylePatterns)

libraryDependencies ++= Seq(
    "org.scala-sbt" % "compiler-interface" % "0.13.0-M2" artifacts(Artifact("compiler-interface-bin")),
    "org.scala-sbt" % "incremental-compiler" % "0.13.0-M2",
    "org.scala-sbt" % "api" % "0.13.0-M2",
    "org.scala-sbt" % "persist" % "0.13.0-M2",
    "org.scala-sbt" % "classfile" % "0.13.0-M2",
    "org.scala-sbt" % "compile" % "0.13.0-M2",
    "org.scala-sbt" % "compiler-integration" % "0.13.0-M2"
)

resourceDirectories in Compile := Nil

resourceDirectories in Test := Nil

osgiSettings

//OsgiKeys.exportPackage := Seq("sbt.*")

OsgiKeys.embeddedJars <<= (Keys.externalDependencyClasspath in Compile, Keys.baseDirectory in ThisBuild) map { (deps,base) =>
  deps flatMap { d => d.data.getName match {
    case s if (s.startsWith("scala-")) => None
    case s if (s.startsWith("launcher-interface")) => None
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

OsgiKeys.additionalHeaders := Map(
  "Embed-Dependency" -> "*;scope=compile|runtime;inline=false",
  "Embed-Directory" -> ".",
  "Include-Resource" -> "",
  "-exportcontents" -> "sbt.*;version=${sbt.version},xsbt.*;version=${sbt.version},xsbti.*;version=${sbt.version},sbinary.*;version=${sbinary.version},jline.*;version=2.10,org.apache.ivy.*;version=2.2.0"
  )