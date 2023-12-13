val allNumbersParser = "-?\\d+".toRegex()

fun getAllNumbers(s: String): List<Long> {
   return allNumbersParser.findAll(s).map { m -> m.value.toLong() }.toList()
}

fun getPrimes(upTo: Long): List<Long> {
   return (2..upTo).filter { n -> (2 ..< n).none { d -> n % d == 0L } }
}

fun findLeastCommonMultiple(numbers: List<Long>): Long {
   val primes = getPrimes(numbers.max())
   val factors = ArrayList<Long>()
   var currentStep = numbers.toMutableList()

   while (!currentStep.all { x -> x == 1L }) {
      primal@ for (prime in primes) {
         var divisible = false
         for (i in 0 ..< currentStep.size) {
            if (currentStep[i] % prime == 0L) {
               divisible = true
               currentStep[i] /= prime
            }
         }
         if (divisible) {
            factors.add(prime)
            break@primal
         }
      }
   }
   return factors.reduce { a, f -> a * f }
}

fun getPadded2DInput(input: String): List<String> {
   val lines = input.lines().toMutableList()
   val length = input.lines()[0].length

   for (i in 0 until lines.size) {
      lines[i] = ".${lines[i]}."
   }

   lines.add(0, ".".repeat(length + 2))
   lines.add(".".repeat(length + 2))

   return lines
}

fun loadResource(filename: String): String {
   return object {}::class.java.getResource(filename)!!.readText()
}

fun loadInput(day: Int): String {
    return loadResource("input$day.txt").trimEnd().trimEnd('\n', '\r')
}
