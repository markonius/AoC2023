val trebuchet =
      arrayOf(
            ::trebuchet0,
            ::trebuchet1,
      )

fun trebuchet0(): String {
   val lines = input.lines()
   val sum =
         lines.sumOf { l ->
            val first = l.first { c -> c.isDigit() }.digitToInt()
            val last = l.last { c -> c.isDigit() }.digitToInt()
            val total = first * 10 + last
            total
         }
   return sum.toString()
}

fun trebuchet1(): String {
   val stringValues =
         mapOf(
               "1" to 1,
               "2" to 2,
               "3" to 3,
               "4" to 4,
               "5" to 5,
               "6" to 6,
               "7" to 7,
               "8" to 8,
               "9" to 9,
               "0" to 0,
               "one" to 1,
               "two" to 2,
               "three" to 3,
               "four" to 4,
               "five" to 5,
               "six" to 6,
               "seven" to 7,
               "eight" to 8,
               "nine" to 9,
         )

   val regex = "(1|2|3|4|5|6|7|8|9|0|one|two|three|four|five|six|seven|eight|nine)".toRegex()

   fun parseLine(l: String): List<Int> {
      val matches = regex.findAll(l)
      return matches.map { stringValues[it.value]!! }.toList()
   }

   val lines = input.lines()
   val sum =
         lines.sumOf { l ->
            val values = parseLine(l)
            values.first() * 10 + values.last()
         }
   return sum.toString()
}

private val input get() = loadInput(1)
