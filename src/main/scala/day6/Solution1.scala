package day6

import scala.io.Source
import scala.util.Using

object Solution1 extends App {
  val directions = List('^', '>', 'v', '<')
  val moves = Map(
    '^' -> (-1, 0),
    '>' -> (0, 1),
    'v' -> (1, 0),
    '<' -> (0, -1)
  )

  def simulatePatrol(grid: Array[Array[Char]]): Int = {
    val rows = grid.length
    val cols = grid(0).length

    val (startRow, startCol, startDirection) = {
      for {
        row <- 0 until rows
        col <- 0 until cols
        if directions.contains(grid(row)(col))
      } yield (row, col, grid(row)(col))
    }.head

    var visited = Set((startRow, startCol))
    var currentRow = startRow
    var currentCol = startCol
    var currentDirection = startDirection

    def isInBounds(r: Int, c: Int): Boolean = r >= 0 && r < rows && c >= 0 && c < cols

    while (true) {
      val (dr, dc) = moves(currentDirection)
      val nextRow = currentRow + dr
      val nextCol = currentCol + dc

      if (!isInBounds(nextRow, nextCol)) {
        return visited.size
      }

      if (grid(nextRow)(nextCol) == '#') {
        currentDirection = directions((directions.indexOf(currentDirection) + 1) % 4)
      } else {
        currentRow = nextRow
        currentCol = nextCol
        visited += ((currentRow, currentCol))
      }
    }

    visited.size
  }

  val filePath: String = "src/main/scala/day6/input.txt"
  val grid = Using.resource(Source.fromFile(filePath)) { source =>
    source.getLines().map(_.toCharArray).toArray
  }

  val result = simulatePatrol(grid)
  println(s"Distinct positions visited: $result")
}
