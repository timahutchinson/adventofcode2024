package day4

import scala.io.Source
import scala.util.Using

object Solution2 extends App {
  private def countWordOccurrences(grid: Seq[String]): Int = {
    val word = "MAS"
    val rows = grid.length
    val cols = grid.head.length

    def generateCoordinates(x: Int, y: Int): List[List[(Int, Int)]] = {
      List(
        List((x - 1, y + 1), (x, y), (x + 1, y - 1)),
        List((x - 1, y - 1), (x, y), (x + 1, y + 1)),
        List((x + 1, y + 1), (x, y), (x - 1, y - 1)),
        List((x + 1, y - 1), (x, y), (x - 1, y + 1))
      )
    }

    def wordCoordinates(x: Int, y: Int): Option[List[List[(Int, Int)]]] = {
      val positions = generateCoordinates(x, y)

      if (positions.flatten.forall { case (nx, ny) => nx >= 0 && nx < rows && ny >= 0 && ny < cols }) {
        Some(positions)
      } else {
        None
      }
    }

    def isXMas(positions: List[List[(Int, Int)]]): Boolean = {
      val matchedSublist = positions.map { sublist =>
        sublist.zip(word).forall { case ((x, y), char) =>
          grid(x)(y) == char
        }
      }

      matchedSublist.count(identity) == 2
    }

    (for {
      x <- 0 until rows
      y <- 0 until cols
      positions <- wordCoordinates(x, y)
      if isXMas(positions)
    } yield 1).sum
  }

  private def solution() = {

    val filePath: String = "src/main/scala/day4/input.txt"
    val lines = Using.resource(Source.fromFile(filePath)) { source =>
      source.getLines().toSeq
    }

    countWordOccurrences(lines)
  }

  println(solution())
}
