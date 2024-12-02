package day1

import scala.io.Source

object Solution2 extends App {
  val filePath: String = "src/main/scala/day1/input.txt"

  val lines = Source.fromFile(filePath).getLines().toList

  val (col1, col2) = lines
    .map(_.trim.split("\\s+"))
    .filter(_.length == 2)
    .map { case Array(a, b) => (a.toInt, b.toInt) }
    .unzip

  val col2FrequencyMap = col2.groupBy(identity).view.mapValues(_.size).toMap
  val col1Frenquencies = col1.map(value => col2FrequencyMap.getOrElse(value, 0))
  val similarityScore = col1
    .zip(col1Frenquencies)
    .map { case (a, b) => a * b }
    .sum

  println(s"Solution: $similarityScore")
}
