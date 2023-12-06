val allNumbersParser = "\\d+".toRegex()

fun getAllNumbers(s: String): List<Long> {
    return allNumbersParser.findAll(s).map { m -> m.value.toLong() }.toList()
}
