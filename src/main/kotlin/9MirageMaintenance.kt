val mirageMaintenance =
      arrayOf(
            ::mirageMaintenance0,
            ::mirageMaintenance1,
      )

fun mirageMaintenance0(): String {
   val lines = input.lines().map { getAllNumbers(it) }

   fun getNext(numbers: List<Long>): Long {
      if (numbers.all { it == 0L }) {
         return 0
      }
      val nextLine = (1 ..< numbers.size).map { i -> numbers[i] - numbers[i - 1] }
      val next = getNext(nextLine)
      return numbers.last() + next
   }

   val result = lines.sumOf { ns -> getNext(ns) }
   return result.toString()
}

fun mirageMaintenance1(): String {
   val lines = input.lines().map { getAllNumbers(it) }

   fun getPrevious(numbers: List<Long>): Long {
      if (numbers.all { it == 0L }) {
         return 0
      }
      val nextLine = (1 ..< numbers.size).map { i -> numbers[i] - numbers[i - 1] }
      val next = getPrevious(nextLine)
      return numbers.first() - next
   }

   val result = lines.sumOf { ns -> getPrevious(ns) }
   return result.toString()
}

private val input get() = loadInput(9)
