package day5

import scala.collection.mutable
import scala.io.Source
import scala.util.Using

object Solution2 extends App {
  def isCorrectlyOrdered(update: List[Int]): Boolean = {
    rules.forall { case (a, b) =>
      val aIndex = update.indexOf(a)
      val bIndex = update.indexOf(b)
      (aIndex < 0 || bIndex < 0) || aIndex < bIndex
    }
  }

  def topoSort(update: List[Int]): List[Int] = {
    val inUpdate = update.toSet
    val subGraph = graph.view.filterKeys(inUpdate).mapValues(_.filter(inUpdate)).toMap

    val inDegree = mutable.Map[Int, Int]().withDefaultValue(0)
    subGraph.values.flatten.foreach { to =>
      inDegree(to) += 1
    }

    val queue = mutable.Queue[Int]()
    update.foreach { node =>
      if (inDegree(node) == 0) queue.enqueue(node)
    }

    val fixedUpdate = mutable.ListBuffer[Int]()
    while (queue.nonEmpty) {
      val node = queue.dequeue()
      fixedUpdate += node
      subGraph(node).foreach { neighbor =>
        inDegree(neighbor) -= 1
        if (inDegree(neighbor) == 0) queue.enqueue(neighbor)
      }
    }

    fixedUpdate.toList
  }

  // Read and prep input data
  val filePath: String = "src/main/scala/day5/input.txt"
  val lines = Using.resource(Source.fromFile(filePath)) { source =>
    source.getLines().toSeq
  }
  val (rulesRaw, updatesRaw) = lines.span(_.nonEmpty)
  val rules = rulesRaw.map(_.split("\\|").toList.map(_.toInt) match {
    case List(a, b) => (a, b)
  })
  val updates = updatesRaw.drop(1).map(_.split(",").toList.map(_.toInt))

  // Create directed graph of rules
  val graph: Map[Int, List[Int]] = rules.foldLeft(Map.empty[Int, List[Int]].withDefaultValue(Nil)) {
    case (acc, (x, y)) =>
      acc.updated(x, y :: acc(x))
  }

  // Filter to bad updates, fix them, find the middle value, and sum
  val reorderedUpdates = updates.filterNot(isCorrectlyOrdered).map(topoSort
  val sumOfMiddlePages = reorderedUpdates.map(update => update(update.length / 2)).sum

  println(sumOfMiddlePages)
}
