package si18n.core

sealed abstract class I18n[A]
object I18n {
  final case class RawString(value: String) extends I18n[String]
  final case class FormatInt(value: Int) extends I18n[String]
  final case class Context(parts: List[String], vars: List[String]) extends I18n[String]
}
