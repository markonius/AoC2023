import kotlin.math.pow

val scratchcards = arrayOf(
    ::scratchcards0,
    ::scratchcards1
)

fun scratchcards0(): String {
    val numberParser = "\\d+".toRegex()
    val result = input.lines().map { l ->
        val parts = l.split(':', '|')
        val winningNumbers = numberParser.findAll(parts[1]).map { m ->
            m.value.toInt()
        }.toSet()
        val playedNumbers = numberParser.findAll(parts[2]).map { m ->
            m.value.toInt()
        }
        val guesses = playedNumbers.count { n -> winningNumbers.contains(n) }
        val score = if (guesses == 0) 0 else 2.0.pow(guesses - 1).toInt()
        score
    }.sum()
    return result.toString()
}

fun scratchcards1(): String {
    val numberParser = "\\d+".toRegex()

    fun getGuesses(l: String): Int {
        val parts = l.split(':', '|')
        val winningNumbers = numberParser.findAll(parts[1]).map { m ->
            m.value.toInt()
        }.toSet()
        val playedNumbers = numberParser.findAll(parts[2]).map { m ->
            m.value.toInt()
        }
        val guesses = playedNumbers.count { n -> winningNumbers.contains(n) }
        return guesses
    }

    val lines = input.lines()
    val scoreCache = HashMap<Int, Int>()

    fun processCard(index: Int): Int {
        val cached = scoreCache[index]
        if (cached != null) {
            return cached
        }

        val l = lines[index]
        val guesses = getGuesses(l)
        val subguesses = ((index + 1)..<(index + 1 + guesses)).sumOf { j ->
            processCard(j)
        }
        val score = guesses + subguesses
        scoreCache[index] = score
        return score
    }
    val result = List(lines.size) { i ->
        processCard(i)
    }.sum() + lines.size
    return result.toString()
}

private val input get() = loadInput(4)
