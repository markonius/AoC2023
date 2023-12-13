import kotlin.io.println
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

val hauntedWasteland =
      arrayOf(
            ::hauntedWasteland0,
            ::hauntedWasteland1,
      )

fun hauntedWasteland0(): String {
   val lines = input.lines()
   val instructions = lines[0].map { c -> if (c == 'L') 0 else 1 }.toIntArray()

   val mapParser = "(\\w+) = \\((\\w+), (\\w+)\\)".toRegex()
   val map =
         lines.drop(2).associate { l ->
            val match = mapParser.find(l)
            val key = match!!.groupValues[1]
            val value = arrayOf(match.groupValues[2], match.groupValues[3])
            key to value
         }

   var target = "AAA"
   var instructionIndex = 0
   var stepCounter = 0
   while (true) {
      stepCounter++
      val options = map[target]!!
      target = options[instructions[instructionIndex]]
      if (target == "ZZZ") {
         return stepCounter.toString()
      }
      instructionIndex = (instructionIndex + 1) % instructions.size
   }
}

fun hauntedWasteland1(): String {
   val lines = input.lines()
   val instructions = lines[0].map { c -> if (c == 'L') 0 else 1 }.toIntArray()

   val mapParser = "(\\w+) = \\((\\w+), (\\w+)\\)".toRegex()
   val map =
         lines.drop(2).associate { l ->
            val match = mapParser.find(l)
            val key = match!!.groupValues[1]
            val value = arrayOf(match.groupValues[2], match.groupValues[3])
            key to value
         }

   data class Loop(
         val stepOffset: Long,
         val startingNode: String,
         val firstInstructionIndex: Int,
         val period: Long
   )

   data class Path(val loopInfo: Loop, val potentialEnds: List<Long>)

   fun calculatePath(startingNode: String): Path {
      fun findLoop(): Loop {
         val visitCache = map.keys.associateWith { _ -> HashMap<Int, Long>() }

         var target = startingNode
         var instructionIndex = 0
         var stepCounter = 0L
         while (true) {
            val cachedVisit = visitCache[target]!!
            val visitedAtStep = cachedVisit[instructionIndex]
            if (visitedAtStep != null) {
               println("Found loop: $startingNode, $target")
               return Loop(
                     visitedAtStep,
                     target,
                     instructionIndex,
                     stepCounter - visitedAtStep,
               )
            } else {
               cachedVisit[instructionIndex] = stepCounter
            }

            val options = map[target]!!
            target = options[instructions[instructionIndex]]
            instructionIndex = (instructionIndex + 1) % instructions.size
            stepCounter++
         }
      }

      fun findPotentialEnds(
            startingNode: String,
            firstInstructionIndex: Int,
            stepOffset: Long
      ): ArrayList<Long> {
         val potentialEnds = ArrayList<Long>()
         var target = startingNode
         var instructionIndex = firstInstructionIndex
         var stepCounter = stepOffset
         while (true) {
            val options = map[target]!!
            if (target[2] == 'Z') {
               potentialEnds.add(stepCounter)
               println("Potential end for $startingNode: $target")
            }
            target = options[instructions[instructionIndex]]
            instructionIndex = (instructionIndex + 1) % instructions.size
            stepCounter++
            if (target == startingNode && instructionIndex == firstInstructionIndex) {
               return potentialEnds
            }
         }
      }

      val loop = findLoop()
      val potentialEnds =
            findPotentialEnds(loop.startingNode, loop.firstInstructionIndex, loop.stepOffset)
      return Path(loop, potentialEnds)
   }

   val starts = map.keys.filter { k -> k[2] == 'A' }
   val paths = runBlocking(Dispatchers.Default) { starts.map { s -> calculatePath(s) } }

   for (path in paths) {
      println(path)
   }

   // There are hidden invariants in the data:
   // 1. All paths loop.
   // 2. There are nodes that are traversed in the beginning that are not part of the loop.
   // 3. All paths only ever reach one potential end.
   // 4. All ends are reached at steps equal to the period of the loop.
   //		In other words, the number of steps made before the loop starts can be ignored.
   val lcm = findLeastCommonMultiple(paths.map { p -> p.potentialEnds[0] })
   return lcm.toString()
}

private val input get() = loadInput(8)
