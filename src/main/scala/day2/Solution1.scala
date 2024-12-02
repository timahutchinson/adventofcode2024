package day2

import scala.io.Source
import scala.util.Using

object Solution1 extends App {
  private def isGraduallyAscending(levels: List[Int]): Boolean = {
    levels.zip(levels.tail).forall { case (a, b) => a < b && (b - a) <= 3 }
  }

  private def isGraduallyDescending(levels: List[Int]): Boolean = {
    levels.zip(levels.tail).forall { case (a, b) => a > b && a - b <= 3 }
  }

  private def solution() = {
    val startTime = System.nanoTime()

    val filePath: String = "src/main/scala/day2/input.txt"
    val lines = Using.resource(Source.fromFile(filePath)) { source =>
      source.getLines().toList
    }
    val allLevels = lines
      .map(_.trim.split("\\s+").toList.map(_.toInt))

    val safeLevelCount = allLevels
      .map { levels =>
        val isSafe = isGraduallyAscending(levels) || isGraduallyDescending(levels)
        if (isSafe) 1 else 0
      }
      .sum

    val endTime = System.nanoTime()
    val elapsedTime = (endTime - startTime) / 1e6

    (safeLevelCount, elapsedTime)
  }

  private val (solutions, times) = (1 to 20).map { _ => solution() }.unzip

  println(s"Solution: ${solutions.head}")
  private val slicedTimes = times.drop(5) // skipping JVM warmup
  println(f"Average runtime: ${slicedTimes.sum / slicedTimes.size}%.3f ms")
}
