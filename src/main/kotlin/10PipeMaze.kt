val pipeMaze =
      arrayOf(
            ::pipeMaze0,
            ::pipeMaze1,
      )

fun pipeMaze0(): String {
   val lines = getPadded2DInput(input)

   class Part(val x: Int, val y: Int, val connections: List<Pair<Int, Int>>) {
      val position: Pair<Int, Int>
         get() = x to y
      val neighbours: List<Pair<Int, Int>>
         get() = connections.map { c -> c.first + x to c.second + y }
   }

   val startPosition = lines.mapIndexed { y, l -> l.indexOf('S') to y }.first { p -> p.first != -1 }
   val startPart =
         Part(
               startPosition.first,
               startPosition.second,
               listOf(1 to 0, 0 to 1, -1 to 0, 0 to -1),
         )
   val connectionsBySygil =
         mapOf(
               'S' to listOf(1 to 0, 0 to 1, -1 to 0, 0 to -1),
               '|' to listOf(0 to 1, 0 to -1),
               '-' to listOf(1 to 0, -1 to 0),
               'L' to listOf(1 to 0, 0 to -1),
               'J' to listOf(-1 to 0, 0 to -1),
               '7' to listOf(-1 to 0, 0 to 1),
               'F' to listOf(1 to 0, 0 to 1),
               '.' to emptyList()
         )

   var previousPart = startPart
   var currentPart = startPart
   var partCount = 0
   do {
      val nextPart =
            currentPart.neighbours
                  .map { n ->
                     val sygil = lines[n.second][n.first]
                     val connections = connectionsBySygil[sygil]!!
                     Part(n.first, n.second, connections)
                  }
                  .first { p ->
                     p.position != previousPart.position &&
                           p.neighbours.contains(currentPart.position)
                  }
      previousPart = currentPart
      currentPart = nextPart
      partCount++
   } while (currentPart.position != startPart.position)

   return (partCount / 2).toString()
}

fun pipeMaze1(): String {
   val lines = getPadded2DInput(input)

   class Part(val x: Int, val y: Int, val connections: List<Pair<Int, Int>>) {
      val position: Pair<Int, Int>
         get() = x to y
      val neighbours: List<Pair<Int, Int>>
         get() = connections.map { c -> c.first + x to c.second + y }
   }

   // Find parts
   val startPosition = lines.mapIndexed { y, l -> l.indexOf('S') to y }.first { p -> p.first != -1 }
   val startPart =
         Part(
               startPosition.first,
               startPosition.second,
               listOf(1 to 0, 0 to 1, -1 to 0, 0 to -1),
         )
   val connectionsBySygil =
         mapOf(
               'S' to listOf(1 to 0, 0 to 1, -1 to 0, 0 to -1),
               '|' to listOf(0 to 1, 0 to -1),
               '-' to listOf(1 to 0, -1 to 0),
               'L' to listOf(1 to 0, 0 to -1),
               'J' to listOf(-1 to 0, 0 to -1),
               '7' to listOf(-1 to 0, 0 to 1),
               'F' to listOf(1 to 0, 0 to 1),
               '.' to emptyList()
         )

   val parts = ArrayList<Part>()
   parts.add(startPart)
   var previousPart = startPart
   var currentPart = startPart
   do {
      val nextPart =
            currentPart.neighbours
                  .map { n ->
                     val sygil = lines[n.second][n.first]
                     val connections = connectionsBySygil[sygil]!!
                     Part(n.first, n.second, connections)
                  }
                  .first { p ->
                     p.position != previousPart.position &&
                           p.neighbours.contains(currentPart.position)
                  }
      previousPart = currentPart
      currentPart = nextPart
      parts.add(nextPart)
   } while (currentPart.position != startPart.position)

   // Flood fill
   val searchBoard = Array<ByteArray>(lines.size * 2) { _ -> ByteArray(lines[0].length * 2) }

   fun paintPart(currentPosition: Pair<Int, Int>, previousPosition: Pair<Int, Int>) {

      val difference =
            currentPosition.first - previousPosition.first to
                  currentPosition.second - previousPosition.second
      val inBetweenPosition =
            currentPosition.first * 2 - difference.first to
                  currentPosition.second * 2 - difference.second
      searchBoard[currentPosition.second * 2][currentPosition.first * 2] = 1
      searchBoard[inBetweenPosition.second][inBetweenPosition.first] = 1
   }

   searchBoard[startPosition.second * 2][startPosition.first * 2] = 1
   for (i in 1 ..< parts.size) {
      val currentPosition = parts[i].position
      val previousPosition = parts[i - 1].position
      paintPart(currentPosition, previousPosition)
   }

   val width = searchBoard[0].size
   val height = searchBoard.size
   fun floodFill() {
      val stack = ArrayDeque<Pair<Int, Int>>()
      stack.addLast(0 to 0)

      while (stack.isNotEmpty()) {
         val position = stack.removeLast()
         if (position.first < 0 ||
                     position.first >= width ||
                     position.second < 0 ||
                     position.second >= height ||
                     searchBoard[position.second][position.first] != 0.toByte()
         ) {
            continue
         }
         searchBoard[position.second][position.first] = 1
         stack.addLast(position.first + 1 to position.second)
         stack.addLast(position.first - 1 to position.second)
         stack.addLast(position.first to position.second + 1)
         stack.addLast(position.first to position.second - 1)
      }
   }

   floodFill()

   for (l in searchBoard) {
      for (c in l) {
         print(if (c == 1.toByte()) '#' else ' ')
      }
      println()
   }

   return searchBoard
         .filterIndexed { i, _ -> i % 2 == 0 }
         .flatMap { l -> l.filterIndexed { i, _ -> i % 2 == 0 } }
         .count { b -> b == 0.toByte() }
         .toString()
}

private val input get() = loadInput(10)
