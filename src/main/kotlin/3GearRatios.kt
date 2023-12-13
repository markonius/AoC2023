val gearRatios =
      arrayOf(
            ::gearRatios0,
            ::gearRatios1,
      )

fun gearRatios0(): String {
   fun getPaddedInput(): List<String> {
      val lines = input.lines().toMutableList()
      val length = input.lines()[0].length

      for (i in 0 until lines.size) {
         lines[i] = ".${lines[i]}."
      }

      lines.add(0, ".".repeat(length + 2))
      lines.add(".".repeat(length + 2))

      return lines
   }

   class Number(val value: Int, val position: Pair<Int, Int>, val symbol: Pair<Int, Int>?)

   val paddedInput = getPaddedInput()
   val numberParser = "\\d+".toRegex()

   fun locateSymbol(lineIndex: Int, numberRange: IntRange): Pair<Int, Int>? {
      for (y in (lineIndex - 1)..(lineIndex + 1)) {
         for (x in (numberRange.first - 1)..(numberRange.last + 1)) {
            val maybeSymbol = paddedInput[y][x]
            if (maybeSymbol != '.' && !maybeSymbol.isDigit()) {
               return Pair(x, y)
            }
         }
      }
      return null
   }

   val numbers =
         paddedInput.flatMapIndexed { index, line ->
            val matches = numberParser.findAll(line)
            matches.map { m ->
               val symbol = locateSymbol(index, m.range)
               Number(m.value.toInt(), Pair(m.range.first, index), symbol)
            }
         }

   val partNumbers = numbers.filter { it.symbol != null }
   val result = partNumbers.sumOf { it -> it.value }
   return result.toString()
}

fun gearRatios1(): String {
   fun getPaddedInput(): List<String> {
      val lines = input.lines().toMutableList()
      val length = input.lines()[0].length

      for (i in 0 until lines.size) {
         lines[i] = ".${lines[i]}."
      }

      lines.add(0, ".".repeat(length + 2))
      lines.add(".".repeat(length + 2))

      return lines
   }

   data class Gear(val value: Char, val position: Pair<Int, Int>)
   class Number(val value: Int, val position: Pair<Int, Int>, val gear: Gear?)

   val paddedInput = getPaddedInput()
   val numberParser = "\\d+".toRegex()

   fun locateGear(lineIndex: Int, numberRange: IntRange): Gear? {
      for (y in (lineIndex - 1)..(lineIndex + 1)) {
         for (x in (numberRange.first - 1)..(numberRange.last + 1)) {
            val maybeGear = paddedInput[y][x]
            if (maybeGear == '*') {
               return Gear(maybeGear, Pair(x, y))
            }
         }
      }
      return null
   }

   val numbers =
         paddedInput.flatMapIndexed { index, line ->
            val matches = numberParser.findAll(line)
            matches.map { m ->
               val gear = locateGear(index, m.range)
               Number(m.value.toInt(), Pair(m.range.first, index), gear)
            }
         }

   val partNumbers = numbers.filter { it.gear != null }
   val gears = partNumbers.groupBy { it.gear!! }
   val validGears = gears.filter { it.value.size == 2 }
   val gearRatios = validGears.values.map { ns -> ns[0].value * ns[1].value }
   val result = gearRatios.sum()
   return result.toString()
}

private val input get() = loadInput(3)
