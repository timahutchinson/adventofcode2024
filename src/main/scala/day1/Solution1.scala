package day1

import scala.io.Source

object Solution1 extends App {
  private def solution() = {
    val startTime = System.nanoTime()

    val filePath: String = "src/main/scala/day1/input.txt"
    val lines = Source.fromFile(filePath).getLines().toList
    val (col1, col2) = lines
      .map(_.trim.split("\\s+"))
      .filter(_.length == 2)
      .map { case Array(a, b) => (a.toInt, b.toInt) }
      .unzip

    val sortedCol1 = col1.sorted
    val sortedCol2 = col2.sorted

    val sumOfDifferences = sortedCol1
      .zip(sortedCol2)
      .map { case (a, b) => math.abs(a - b) }
      .sum

    val endTime = System.nanoTime()
    val elapsedTime = (endTime - startTime) / 1e6

    (sumOfDifferences, elapsedTime)
  }

  private val (solutions, times) = (1 to 20).map { _ => solution() }.unzip

  println(s"Solution: ${solutions.head}")
  private val slicedTimes = times.drop(5)
  println(f"Average runtime: ${slicedTimes.sum / slicedTimes.size}%.3f ms")
}
