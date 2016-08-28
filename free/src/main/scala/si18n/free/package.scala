package si18n

import cats.Monad
import cats.free.{ Free, FreeTInstances}
import si18n.core._

package object free {
  def rawString(value: String): Free[I18n, String] =
    Free.liftF(I18n.RawString(value))
  def formatInt(value: Int): Free[I18n, String] =
    Free.liftF(I18n.FormatInt(value))
  def stringContext(parts: List[String], vars: List[String]): Free[I18n, String] =
    Free.liftF(I18n.Context(parts, vars))

  implicit def instance(implicit F: Monad[Free[I18n, ?]]): MonadI18n[Free[I18n, ?]] = new MonadI18n[Free[I18n, ?]] {
    override def rawString(value: String): Free[I18n, String] = Free.liftF(I18n.RawString(value))
    override def formatInt(value: Int): Free[I18n, String] = Free.liftF(I18n.FormatInt(value))
    override def stringContext(parts: List[String], vars: List[String]): Free[I18n, String] = Free.liftF(I18n.Context(parts, vars))

    override def pure[A](x: A): Free[I18n, A] = F.pure(x)
    override def flatMap[A, B](fa: Free[I18n, A])(f: (A) => Free[I18n, B]): Free[I18n, B] = F.flatMap(fa)(f)
    override def tailRecM[A, B](a: A)(f: (A) => Free[I18n, Either[A, B]]): Free[I18n, B] = F.tailRecM(a)(f)
  }
}
