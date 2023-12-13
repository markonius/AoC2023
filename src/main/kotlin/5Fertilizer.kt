val fertilizer = arrayOf(
    ::fertilizer0,
    ::fertilizer1,
)

fun fertilizer0(): String {
    val lines = input.lines()
    val seeds = allNumbersParser.findAll(lines.first()).map { m -> m.value.toLong() }

    fun parseMaps(): List<List<Pair<LongRange, Long>>> {
        val splitLines = ArrayList<ArrayList<String>>()
        for (line in lines.subList(1, lines.size)
            .filter { l -> l.isNotEmpty() }
        ) {
            if (line.first().isLetter()) {
                splitLines.add(ArrayList())
            } else {
                splitLines.last().add(line)
            }
        }

        return splitLines.map { set ->
            set.map { l ->
                val numbers = allNumbersParser.findAll(l).map { m -> m.value.toLong() }.toList()
                Pair(LongRange(numbers[1], numbers[1] + numbers[2] - 1), numbers[0])
            }
        }
    }

    val maps = parseMaps()

    return seeds.minOf { s ->
        var n = s
        for (map in maps) {
            val entry = map.firstOrNull { p -> p.first.contains(n) }
            if (entry == null) {
                continue
            } else {
                n = entry.second + (n - entry.first.first)
            }
        }
        n
    }.toString()
}

fun fertilizer1(): String {
    val lines = input.lines()
    val seedRanges = allNumbersParser.findAll(lines.first()).map { m -> m.value.toLong() }
    val seeds = seedRanges.mapIndexed { i, n -> Pair(i, n) }
        .groupBy { p -> p.first / 2 }
        .asSequence()
        .flatMap { e -> LongRange(e.value[0].second, e.value[0].second + e.value[1].second - 1) }

    fun parseMaps(): List<List<Pair<LongRange, Long>>> {
        val splitLines = ArrayList<ArrayList<String>>()
        for (line in lines.subList(1, lines.size)
            .filter { l -> l.isNotEmpty() }
        ) {
            if (line.first().isLetter()) {
                splitLines.add(ArrayList())
            } else {
                splitLines.last().add(line)
            }
        }

        return splitLines.map { set ->
            set.map { l ->
                val numbers = allNumbersParser.findAll(l).map { m -> m.value.toLong() }.toList()
                Pair(LongRange(numbers[1], numbers[1] + numbers[2] - 1), numbers[0])
            }
        }
    }

    val maps = parseMaps()

    return seeds.minOf { s ->
        var n = s
        for (map in maps) {
            val entry = map.firstOrNull { p -> p.first.contains(n) }
            if (entry == null) {
                continue
            } else {
                n = entry.second + (n - entry.first.first)
            }
        }
        n
    }.toString()
}

private const val inputTest = """seeds: 79 14 55 13

seed-to-soil map:
50 98 2
52 50 48

soil-to-fertilizer map:
0 15 37
37 52 2
39 0 15

fertilizer-to-water map:
49 53 8
0 11 42
42 0 7
57 7 4

water-to-light map:
88 18 7
18 25 70

light-to-temperature map:
45 77 23
81 45 19
68 64 13

temperature-to-humidity map:
0 69 1
1 0 69

humidity-to-location map:
60 56 37
56 93 4"""

private val input get() = loadInput(5)
