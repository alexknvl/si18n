package si18n.core

import cats.Monad

trait MonadI18n[F[_]] extends Monad[F] {
  def rawString(value: String): F[String]
  def formatInt(value: Int): F[String]
  def stringContext(parts: List[String], vars: List[String]): F[String]
}