val cubeConundrum =
      arrayOf(
            ::cubeConundrum0,
            ::cubeConundrum1,
      )

fun cubeConundrum0(): String {
   val bag =
         object {
            val red = 12
            val green = 13
            val blue = 14
         }

   val gameIdParser = "\\d+".toRegex()
   val valueParser = "(\\d+) (\\w+)".toRegex()

   val result =
         input.lines().sumOf { l ->
            val splitLine = l.split(":")

            val gameId = gameIdParser.find(splitLine[0])!!.value.toInt()

            val drawsLine = splitLine[1]
            val draws = drawsLine.split(";")

            for (draw in draws) {
               var red = 0
               var green = 0
               var blue = 0

               val matches = valueParser.findAll(draw)
               for (match in matches) {
                  val count = match.groupValues[1].toInt()
                  val type = match.groupValues[2]
                  when (type) {
                     "red" -> red = count
                     "green" -> green = count
                     "blue" -> blue = count
                  }
               }

               if (red > bag.red || green > bag.green || blue > bag.blue) {
                  return@sumOf 0
               }
            }

            gameId.toInt()
         }

   return result.toString()
}

fun cubeConundrum1(): String {
   val valueParser = "(\\d+) (\\w+)".toRegex()

   val result =
         input.lines().sumOf { l ->
            val splitLine = l.split(":")

            val drawsLine = splitLine[1]
            val draws = drawsLine.split(";")

            var minimumRed = 0
            var minimumGreen = 0
            var minimumBlue = 0

            for (draw in draws) {

               val matches = valueParser.findAll(draw)
               for (match in matches) {
                  val count = match.groupValues[1].toInt()
                  val type = match.groupValues[2]
                  when (type) {
                     "red" -> minimumRed = Math.max(minimumRed, count)
                     "green" -> minimumGreen = Math.max(minimumGreen, count)
                     "blue" -> minimumBlue = Math.max(minimumBlue, count)
                  }
               }
            }

            val power = minimumRed * minimumGreen * minimumBlue
            power
         }

   return result.toString()
}

private val input get() = loadInput(2)
