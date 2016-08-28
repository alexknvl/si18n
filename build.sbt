lazy val scalaStdDependencies =
  List(
    "org.atnos" %% "eff-cats" % "2.0.0-RC4",
    "eu.timepit" %% "refined" % "0.4.0",
    "com.github.mpilquist" %% "simulacrum" % "0.8.0",
    "org.spire-math" %% "algebra" % "0.4.0",
    "org.typelevel" %% "cats" % "0.7.0",
    "org.typelevel" %% "dogs-core" % "0.2.2",
    "com.chuusai" %% "shapeless" % "2.3.0") ++
    ((version: String) => List(
      "com.github.julien-truffaut"  %%  "monocle-core" % version,
      "com.github.julien-truffaut"  %%  "monocle-generic" % version,
      "com.github.julien-truffaut"  %%  "monocle-macro" % version,
      "com.github.julien-truffaut"  %%  "monocle-state" % version,
      "com.github.julien-truffaut"  %%  "monocle-refined" % version,
      "com.github.julien-truffaut"  %%  "monocle-law" % version % "test"))
      .apply("1.2.2") ++
    List(
      "org.scalacheck" %% "scalacheck" % "1.12.5" % "test",
      "org.typelevel" %% "discipline" % "0.4" % "test",
      "org.scalatest" %% "scalatest" % "3.0.0-M9" % "test")

lazy val commonSettings = List(
  addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.7.1"),
  addCompilerPlugin("com.milessabin" % "si2712fix-plugin_2.11.8" % "1.2.0"),
  organization := "com.alexknvl",
  version := "0.1-SNAPSHOT",
  scalaVersion := "2.11.8",
  scalacOptions ++= List(
    "-deprecation",
    "-encoding", "UTF-8",
    "-feature",
    "-language:existentials",
    "-language:higherKinds",
    "-unchecked",
    "-Yno-adapted-args",
    "-Ywarn-dead-code",
    "-Ywarn-numeric-widen",
    "-Xfuture"),
  resolvers ++= List(
    Resolver.sonatypeRepo("snapshots"),
    Resolver.sonatypeRepo("releases")),
  libraryDependencies ++= scalaStdDependencies,
  wartremoverWarnings ++= Warts.all)

lazy val core = (project in file("core")).
  settings(name := "si18n").
  settings(commonSettings: _*)

lazy val free = (project in file("free")).
  settings(name := "si18n-free").
  settings(commonSettings: _*).
  dependsOn(core)

lazy val eff = (project in file("eff")).
  settings(name := "si18n-eff").
  settings(commonSettings: _*).
  dependsOn(core)

lazy val root = (project in file(".")).
  settings(name := "si18n").
  settings(commonSettings: _*).
  aggregate(core, eff, free)
