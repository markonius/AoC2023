import kotlin.time.measureTime

fun main() {
   val problemNumber = arrayOf(10, 1)

   val problems =
         arrayOf(
               trebuchet,
               cubeConundrum,
               gearRatios,
               scratchcards,
               fertilizer,
               waitForIt,
               camelCards,
               hauntedWasteland,
               mirageMaintenance,
               pipeMaze,
         )

   val problem = problems[problemNumber[0] - 1][problemNumber[1]]
   var output: String
   val time = measureTime { output = problem() }
   println(output)
   println("Time elapsed: $time")
}
