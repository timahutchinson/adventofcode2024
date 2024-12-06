package day5

import scala.io.Source
import scala.util.Using

object Solution1 extends App {
  val filePath: String = "src/main/scala/day5/input.txt"
  val lines = Using.resource(Source.fromFile(filePath)) { source =>
    source.getLines().toSeq
  }

  val (rulesRaw, updatesRaw) = lines.span(_.nonEmpty)

  val rules = rulesRaw.map(_.split("\\|").toList.map(_.toInt) match {
    case List(a, b) => (a, b)
  })
  val updates = updatesRaw.drop(1).map(_.split(",").toList.map(_.toInt))

  val total = updates.map { update =>
    val isCorrect = rules.forall { case (a, b) =>
      val aIndex = update.indexOf(a)
      val bIndex = update.indexOf(b)
      (aIndex < 0 || bIndex < 0) || aIndex < bIndex
    }
    if (isCorrect) update(update.length / 2) else 0
  }.sum

  println(total)
}