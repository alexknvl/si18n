import cats.Id
import cats.arrow.FunctionK
import org.scalatest.{Matchers, FunSuite}

class TestFree extends FunSuite with Matchers {
  test("Running") {
    import si18n.core._
    import si18n.core.ctx._
    import si18n.free._

    val x = 0
    val y = 1

    val a = i18n"$x $y"

    val b = i18n"$x $y".compile(new FunctionK[I18n, Id] {
      override def apply[A](fa: I18n[A]): Id[A] = (fa match {
        case I18n.RawString(s) => s
        case I18n.FormatInt(i) => i.toString
        case I18n.Context(parts, vars) => parts.zipAll(vars, "", "").map(t => t._1 + t._2).mkString("")
      }).asInstanceOf[A]
    }).run

    b should be("0 1")
  }
}