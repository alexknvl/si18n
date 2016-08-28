package si18n

import si18n.core._

package object eff {
  import org.atnos.eff._
  import org.atnos.eff.all._

  def rawString[R](value: String)(implicit R: I18n <= R): Eff[R, String] =
    send[I18n, R, String](I18n.RawString(value))
  def formatInt[R](value: Int)(implicit R: I18n <= R): Eff[R, String] =
    send[I18n, R, String](I18n.FormatInt(value))
  def stringContext[R](parts: List[String], vars: List[String])(implicit R: I18n <= R): Eff[R, String] =
    send[I18n, R, String](I18n.Context(parts, vars))

  implicit def instance[R](implicit R: I18n <= R): MonadI18n[Eff[R, ?]] = new MonadI18n[Eff[R, ?]] {
    override def rawString(value: String): Eff[R, String] =
      send[I18n, R, String](I18n.RawString(value))
    override def formatInt(value: Int): Eff[R, String] =
      send[I18n, R, String](I18n.FormatInt(value))
    override def stringContext(parts: List[String], vars: List[String]): Eff[R, String] =
      send[I18n, R, String](I18n.Context(parts, vars))

    override def pure[A](x: A): Eff[R, A] =
      Eff.pure(x)
    override def flatMap[A, B](fa: Eff[R, A])(f: (A) => Eff[R, B]): Eff[R, B] =
      fa.flatMap(f)
    override def tailRecM[A, B](a: A)(f: (A) => Eff[R, Either[A, B]]): Eff[R, B] =
      defaultTailRecM(a)(f)
  }
}
