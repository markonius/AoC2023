val waitForIt = arrayOf(
    ::waitForIt0,
    ::waitForIt1,
)

fun waitForIt0(): String {
    val lines = input.lines()
    val times = getAllNumbers(lines[0].split(':')[1])
    val distances = getAllNumbers(lines[1].split(':')[1])

    val results = times.zip(distances).map { p ->
        val fastWindups = (1..p.first).map { t ->
            val timeLeft = p.first - t
            val distance = t * timeLeft
            distance
        }.filter { d -> d > p.second }
        fastWindups.count()
    }

    return results.reduce { acc, v -> acc * v }
        .toString()
}

fun waitForIt1(): String {
    val lines = input.lines()
    val targetTime = lines[0].split(':')[1].filter { c -> !c.isWhitespace() }.toLong()
    val targetDistance = lines[1].split(':')[1].filter { c -> !c.isWhitespace() }.toLong()

    val fastWindups = (1..targetTime).map { t ->
        val timeLeft = targetTime - t
        val distance = t * timeLeft
        distance
    }.filter { d -> d > targetDistance }

    return fastWindups.count().toString()
}

private const val input = """Time:        49     78     79     80
Distance:   298   1185   1066   1181
"""
