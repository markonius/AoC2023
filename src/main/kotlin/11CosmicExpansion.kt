import kotlin.math.abs

val cosmicExpansion = arrayOf(
    ::cosmicExpansion0,
    ::cosmicExpansion1,
)

fun cosmicExpansion0(): String {
    val lines = input.lines()
    val expandedLines = ArrayList<String>()

    for (line in lines) {
        expandedLines.add(line)
        if (!line.contains('#')) {
            expandedLines.add(line)
        }
    }

    val expandedSpace = expandedLines.map { ArrayList<Byte>() }
    for (x in 0..<expandedLines[0].length) {
        var hadGalaxy = false
        for (y in 0..<expandedLines.size) {
            val c = expandedLines[y][x]
            val v = if (c == '#') {
                hadGalaxy = true
                1.toByte()
            } else {
                0.toByte()
            }
            expandedSpace[y].add(v)
        }
        if (!hadGalaxy) {
            for (y in 0..<expandedLines.size) {
                expandedSpace[y].add(0)
            }
        }
    }

    for (y in expandedSpace.indices) {
        for (x in expandedSpace[0].indices) {
            val v = expandedSpace[y][x]
            val glyph = if (v == 1.toByte()) '#' else ' '
            print(glyph)
        }
        println()
    }

    val galaxies = expandedSpace.mapIndexed { y, l ->
        val xs = l.mapIndexed { x, c -> x to c }
            .filter { p -> p.second == 1.toByte() }
            .map { it.first }
        y to xs
    }.flatMap { p -> p.second.map { x -> x to p.first } }

    val distances = galaxies.flatMapIndexed { i, g ->
        val others = galaxies.subList(i + 1, galaxies.size)
        val distancesToThis = others.map { o -> abs(o.first - g.first) + abs(o.second - g.second) }
        distancesToThis
    }

    return distances.sum().toString()
}

fun cosmicExpansion1(): String {
    val lines = input.lines()
    val expandedSpace = ArrayList<IntArray>()
    for (line in lines) {
        expandedSpace.add(
            line.map { c -> if (c == '#') -1 else 1 }.toIntArray()
        )
    }

    val emptyYs = expandedSpace
        .mapIndexed { y, a -> y to a }
        .filter { p -> p.second.all { it == 1 } }
        .map { p -> p.first }
        .toSet()

    val emptyXs = (0..<expandedSpace[0].size)
        .filter { x ->
            (0..<expandedSpace.size).all { y ->
                expandedSpace[y][x] == 1
            }
        }.toSet()

    // Expand space
    for (y in expandedSpace.indices) {
        for (x in expandedSpace[0].indices) {
            if (emptyYs.contains(y) || emptyXs.contains(x)) {
                expandedSpace[y][x] = 1000000
            }
        }
    }

    for (y in expandedSpace.indices) {
        for (x in expandedSpace[0].indices) {
            val v = expandedSpace[y][x]
            val glyph = if (v == -1) '#' else if (v == 1) ' ' else '+'
            print(glyph)
        }
        println()
    }

    val galaxies = expandedSpace.mapIndexed { y, l ->
        val xs = l.mapIndexed { x, c -> x to c }
            .filter { p -> p.second == -1 }
            .map { it.first }
        y to xs
    }.flatMap { p -> p.second.map { x -> x to p.first } }

    fun measure(a: Pair<Int, Int>, b: Pair<Int, Int>): Long {
        val xs = listOf(a.first, b.first).sorted()
        val ys = listOf(a.second, b.second).sorted()
        var distance = 0L
        for (x in xs[0]+1..xs[1]) {
            distance += abs(expandedSpace[a.second][x])
        }
        for (y in ys[0]..<ys[1]) {
            distance += abs(expandedSpace[y][b.first])
        }
        return distance
    }

    val distances = galaxies.flatMapIndexed { i, g ->
        val others = galaxies.subList(i + 1, galaxies.size)
        val distancesToThis = others.map { o -> measure(g, o) }
        distancesToThis
    }

    return distances.sum().toString()
}

private const val testInput =
    """..........
..#.......
....#.....
..........
......#...
..........
......#...
..........
..........
.........."""

private val input get() = loadInput(11)
