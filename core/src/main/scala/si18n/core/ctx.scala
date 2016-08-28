package si18n.core

import cats.instances.list._
import internal._

object ctx {
  implicit final class I18nStringContext(val sc: StringContext) extends AnyVal {
    def i18n[F[_]](args: (Instance[T, Format] forSome { type T })*)(implicit F: MonadI18n[F]): F[String] =
      F.flatMap(F.traverse(args.toList) {
        case a: Instance[t, Format] => a.typeclass.format(a.value)
      })(vars => F.stringContext(sc.parts.toList, vars))
  }
}
