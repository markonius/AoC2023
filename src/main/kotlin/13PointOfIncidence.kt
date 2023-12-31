import java.lang.Exception

val pointOfIncidence = arrayOf(
    ::pointOfIncidence0,
    ::pointOfIncidence1,
)

typealias Pattern = ArrayList<List<Boolean>>

fun pointOfIncidence0(): String {
    val lines = input.lines()

    val patterns = ArrayList<Pattern>()
    patterns.add(Pattern())
    for (l in lines) {
        if (l.isNotEmpty()) {
            patterns.last().add(l.map { c -> c == '#' })
        } else {
            patterns.add(Pattern())
        }
    }

    fun scan(height: Int, width: Int, index: (x: Int, y: Int) -> Boolean): Int? {
        outermost@ for (axis in 0..<height / 2) {
            val mirrored0 = axis * 2 + 1
            for (y in 0..axis) {
                val mirroredY = mirrored0 - y
                for (x in 0..<width) {
                    if (index(x, y) != index(x, mirroredY)) {
                        continue@outermost
                    }
                }
            }
            return axis + 1
        }
        return null
    }

    val result = patterns.sumOf { pattern ->
        val height = pattern.size
        val width = pattern[0].size

        val top = scan(height, width) { x, y -> pattern[y][x] }
        if (top != null) return@sumOf 100 * top
        val bottom = scan(height, width) { x, y -> pattern[height - 1 - y][x] }
        if (bottom != null) return@sumOf 100 * (height - bottom)
        val left = scan(width, height) { x, y -> pattern[x][y] }
        if (left != null) return@sumOf left
        val right = scan(width, height) { x, y -> pattern[x][width - 1 - y] }
        if (right != null) return@sumOf width - right

        throw Exception("No reflection.")
    }
    return result.toString()
}

fun pointOfIncidence1(): String {
    val lines = input.lines()

    val patterns = ArrayList<Pattern>()
    patterns.add(Pattern())
    for (l in lines) {
        if (l.isNotEmpty()) {
            patterns.last().add(l.map { c -> c == '#' })
        } else {
            patterns.add(Pattern())
        }
    }

    fun scan(height: Int, width: Int, index: (x: Int, y: Int) -> Boolean): Int? {
        outermost@ for (axis in 0..<height / 2) {
            val mirrored0 = axis * 2 + 1
            var discrepancies = 0
            for (y in 0..axis) {
                val mirroredY = mirrored0 - y
                for (x in 0..<width) {
                    if (index(x, y) != index(x, mirroredY)) {
                        discrepancies++
                        if (discrepancies > 1)
                            continue@outermost
                    }
                }
            }
            if (discrepancies == 1)
                return axis + 1
        }
        return null
    }

    val result = patterns.sumOf { pattern ->
        val height = pattern.size
        val width = pattern[0].size

        val top = scan(height, width) { x, y -> pattern[y][x] }
        if (top != null) return@sumOf 100 * top
        val bottom = scan(height, width) { x, y -> pattern[height - 1 - y][x] }
        if (bottom != null) return@sumOf 100 * (height - bottom)
        val left = scan(width, height) { x, y -> pattern[x][y] }
        if (left != null) return@sumOf left
        val right = scan(width, height) { x, y -> pattern[x][width - 1 - y] }
        if (right != null) return@sumOf width - right

        throw Exception("No reflection.")
    }
    return result.toString()
}

private val input get() = realInput

private const val testInput = """#.#.#.#.
......#.
......#.
#.#.#.#.
....#...

....#...
#.#.#.#.
......#.
......#.
#.#.#.#.

.#..#..
##..##.
.#..#..
##..##.
.####..
.#..#..

..#..#.
.##..##
..#..#.
.##..##
..####.
..#..#."""

private val realInput get() = loadInput(13)
