fun main() {
   val problemNumber = arrayOf(1, 1)

   val problems =
         arrayOf(
               trebuchet,
         )

   val problem = problems[problemNumber[0] - 1][problemNumber[1]]
   val output = problem()
   println(output)
}
