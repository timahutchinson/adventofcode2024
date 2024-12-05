package day4

import scala.io.Source
import scala.util.Using

object Solution1 extends App {
  private def countWordOccurrences(grid: Seq[String]): Int = {
    val word = "XMAS"
    val directions = Seq(
      (0, 1),
      (0, -1),
      (1, 0),
      (-1, 0),
      (1, 1),
      (1, -1),
      (-1, 1),
      (-1, -1)
    )

    val rows = grid.length
    val cols = grid.head.length

    def wordCoordinates(x: Int, y: Int, dx: Int, dy: Int): Option[Seq[(Int, Int)]] = {
      val positions = (0 until word.length).map { i =>
        (x + i * dx, y + i * dy)
      }
      if (positions.forall { case (nx, ny) => nx >= 0 && nx < rows && ny >= 0 && ny < cols }) {
        Some(positions)
      } else {
        None
      }
    }

    def matchesWord(positions: Seq[(Int, Int)]): Boolean = {
      positions.zip(word).forall { case ((x, y), char) =>
        grid(x)(y) == char
      }
    }

    (for {
      x <- 0 until rows
      y <- 0 until cols
      (dx, dy) <- directions
      positions <- wordCoordinates(x, y, dx, dy)
      if matchesWord(positions)
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
