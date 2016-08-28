package si18n.core.internal

import scala.language.implicitConversions

final case class Instance[T, TC[_]](value: T, typeclass: TC[T])
object Instance {
  implicit def make[TC[_], T: TC](t: T): Instance[T, TC] =
    Instance[T, TC](t, implicitly[TC[T]])
}
