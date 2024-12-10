package day3

import scala.io.Source
import scala.util.Using
import scala.util.matching.Regex

object Solution2 extends App {
  private def findEnabledMulOps(str: String): List[(Int, Int)] = {

    val mulPattern: Regex = """mul\((\d+),(\d+)\)""".r
    val doPattern: Regex = """do\(\)""".r
    val dontPattern: Regex = """don't\(\)""".r

    val allMatches = (mulPattern.findAllMatchIn(str).toList ++
      doPattern.findAllMatchIn(str).toList ++
      dontPattern.findAllMatchIn(str).toList)
      .sortBy(_.start)
    
    var enabled = true
    val enabledMulOps = allMatches.foldLeft(List.empty[(Int, Int)]) { (acc, m) =>
      m.matched match {
        case doPattern() =>
          enabled = true
          acc
        case dontPattern() =>
          enabled = false
          acc
        case mulPattern(x, y) if enabled =>
          acc :+ (x.toInt, y.toInt)
        case _ =>
          acc
      }
    }

    enabledMulOps
  }

  private def solution() = {

    val filePath: String = "src/main/scala/day3/input.txt"
    val lines = Using.resource(Source.fromFile(filePath)) { source =>
      source.getLines().toList
    }

    val flatLines = List(lines.mkString(", \n"))

    //val input = List("xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))")

    val results = flatLines
      .flatMap { str => findEnabledMulOps(str) }
      .map { case (a, b) => a * b }
      .sum

    results
  }

  println(solution())
}
