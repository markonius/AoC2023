fun main() {
   val problemNumber = arrayOf(3, 1)

   val problems =
         arrayOf(
               trebuchet,
               cubeConundrum,
               gearRatios,
         )

   val problem = problems[problemNumber[0] - 1][problemNumber[1]]
   val output = problem()
   println(output)
}
