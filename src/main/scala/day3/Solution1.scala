package day3

import scala.io.Source
import scala.util.Using
import scala.util.matching.Regex

object Solution1 extends App {
  private def solution() = {

    val filePath: String = "src/main/scala/day3/input.txt"
    val lines = Using.resource(Source.fromFile(filePath)) { source =>
      source.getLines().toList
    }

    val pattern: Regex = """mul\((\d+),(\d+)\)""".r

    val results = lines.map { str =>
      pattern.findAllMatchIn(str).map { m =>
        (m.group(1).toInt, m.group(2).toInt)
      }.toList
    }

    val sumOfMulOps = results
      .flatten
      .map { case (a, b) => a * b }
      .sum

    sumOfMulOps
  }

  println(solution())
}
