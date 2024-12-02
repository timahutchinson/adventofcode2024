package day1

import scala.io.Source
import scala.util.Using

object Solution2 extends App {
  private def solution() = {
    val startTime = System.nanoTime()

    val filePath: String = "src/main/scala/day1/input.txt"
    val lines = Using.resource(Source.fromFile(filePath)) { source =>
      source.getLines().toList
    }
    val (col1, col2) = lines
      .map(_.trim.split("\\s+"))
      .filter(_.length == 2)
      .map { case Array(a, b) => (a.toInt, b.toInt) }
      .unzip

    val col2FrequencyMap = col2.groupBy(identity).view.mapValues(_.size).toMap
    val col1Frequencies = col1.map(value => col2FrequencyMap.getOrElse(value, 0))
    val similarityScore = col1
      .zip(col1Frequencies)
      .map { case (a, b) => a * b }
      .sum

    val endTime = System.nanoTime()
    val elapsedTime = (endTime - startTime) / 1e6

    (similarityScore, elapsedTime)
  }

  private val (solutions, times) = (1 to 100).map { _ => solution() }.unzip

  println(s"Solution: ${solutions.head}")
  private val slicedTimes = times.drop(5) // skipping JVM warmup
  println(f"Average runtime: ${slicedTimes.sum / slicedTimes.size}%.3f ms")
}