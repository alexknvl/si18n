package si18n.core

trait Format[A] {
  def format[F[_]](value: A)(implicit F: MonadI18n[F]): F[String]
}

object Format {
  def apply[A](implicit A: Format[A]): Format[A] = A

  def universal[A]: Format[A] = new Format[A] {
    override def format[F[_]](value: A)(implicit F: MonadI18n[F]): F[String] = F.rawString(value.toString)
  }

  implicit val intFormat: Format[Int] = new Format[Int] {
    override def format[F[_]](value: Int)(implicit F: MonadI18n[F]): F[String] = F.formatInt(value)
  }
}