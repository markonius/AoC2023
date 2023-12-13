val hotSprings =
    arrayOf(
        ::hotSprings0,
        ::hotSprings1,
    )

fun hotSprings0(): String {
    val lines = input.lines()
    val parser = "((\\?|#|\\.)+) (.+)".toRegex()
    val brokenParser = "#+".toRegex()

    val result =
        lines.sumOf { l ->
            val match = parser.find(l)!!
            val record =
                match.groupValues[1].map { c ->
                    when (c) {
                        '?' -> 0
                        '#' -> -1
                        else -> 1
                    }
                }
            val constraints = match.groupValues[3].split(',').map { it.toInt() }

            fun getValidArrangements(constraintIndex: Int, offset: Int): List<List<Int>> {
                if (constraintIndex >= constraints.size) {
                    return listOf(emptyList()) // End
                }
                val constraint = constraints[constraintIndex]
                if (offset > record.size - constraint) {
                    return emptyList() // Doesn't fit
                }

                val validArrangements =
                    (offset..record.size - constraint).flatMap { i ->
                        val fits =
                            (0..<constraint).all { j -> record[i + j] <= 0 } &&
                                    (i + constraint == record.size || record[i + constraint] != -1)
                        if (!fits) {
                            return@flatMap emptyList<List<Int>>()
                        }

                        val spots = (i..<i + constraint).toList()

                        val validSubarrangements =
                            getValidArrangements(constraintIndex + 1, i + constraint + 1).map { arr ->
                                spots + arr
                            }
                        validSubarrangements
                    }
                return validArrangements
            }

            val optimisticValidArrangements = getValidArrangements(0, 0)

            val validArrangements =
                optimisticValidArrangements
                    .map { a ->
                        val filledRecord = record.toMutableList()
                        for (position in a) {
                            filledRecord[position] = -1
                        }
                        val filledString =
                            String(
                                filledRecord
                                    .map { v ->
                                        if (v == 0) '?' else if (v == 1) '.' else '#'
                                    }
                                    .toCharArray()
                            )
                        filledString
                    }
                    .filter { filledString ->
                        val matches = brokenParser.findAll(filledString).toList()
                        val constraintCheck = matches.map { it.value.length }.zip(constraints)
                        matches.size == constraints.size &&
                                constraintCheck.all { it.first == it.second }
                    }
//                        .toSet()

            fun visualize() {
                println("$l | ${validArrangements.size}")
                for (arrangement in validArrangements) {
                    println(arrangement)
                }
            }

            // visualize()

            validArrangements.count()
        }
    return result.toString()
}

fun hotSprings1(): String {
    val lines = input.lines()
    val parser = "((\\?|#|\\.)+) (.+)".toRegex()

    val result =
        lines.sumOf { smallL ->
            val splitL = smallL.split(' ')
            val l =
//                smallL
                "${splitL[0]}?${splitL[0]}?${splitL[0]}?${splitL[0]}?${splitL[0]} ${splitL[1]},${splitL[1]},${splitL[1]},${splitL[1]},${splitL[1]}"

//            println(l)

            val match = parser.find(l)!!
            val record =
                match.groupValues[1].map { c ->
                    when (c) {
                        '?' -> 0
                        '#' -> -1
                        else -> 1
                    }
                }
            val constraints = match.groupValues[3].split(',').map { it.toInt() }

            val existingPositions = record.withIndex()
                .filter { e -> e.value == -1 }
                .map { it.index }

            fun visualize(arrangement: Set<Int>) {
                val filledRecord = record.toMutableList()
                for (position in arrangement) {
                    filledRecord[position] = -1
                }
                val filledString =
                    String(
                        filledRecord
                            .map { v ->
                                if (v == 0) '?' else if (v == 1) '.' else '#'
                            }
                            .toCharArray()
                    )
                println(filledString)
            }

            val memo = HashMap<Pair<Int, Int>, Long>()

            fun countValidArrangements(constraintIndex: Int, offset: Int, arrangementByNow: Set<Int>): Long {
                if (constraintIndex >= constraints.size) {
                    return if (existingPositions.all { p -> arrangementByNow.contains(p) }) {
//                        visualize(arrangementByNow)
                        1 // Valid
                    } else
                        0 // Missed existing broken springs
                }
                val constraint = constraints[constraintIndex]
                if (offset > record.size - constraint) {
                    return 0 // Doesn't fit
                }

                val relevantExistingPositions = existingPositions.filter { p -> p < offset }
                if (!relevantExistingPositions.all { p -> arrangementByNow.contains(p) }) {
                    return 0
                }

                val cache = memo[constraintIndex to offset]
                if (cache != null) {
                    return cache
                }

                val validArrangements =
                    (offset..record.size - constraint).sumOf { i ->
                        val fits =
                            (0..<constraint).all { j -> record[i + j] <= 0 } &&
                                    (i + constraint == record.size || record[i + constraint] != -1)
                        if (fits) {
                            val spots = (i..<i + constraint).toList()

                            val validSubarrangements =
                                countValidArrangements(
                                    constraintIndex + 1,
                                    i + constraint + 1,
                                    arrangementByNow + spots
                                )
                            validSubarrangements
                        } else {
                            0
                        }
                    }
                memo[constraintIndex to offset] = validArrangements
                return validArrangements
            }

            val count = countValidArrangements(0, 0, emptySet())
            count
        }
    return result.toString()
}

private val input
    get() = realInput

private const val testInput =
    """????.??.??. 1,1
##??#??#????????#?.# 10,1,2,1,1
????????#????#?.# 1,2,3,2,1
????#???..?.?? 5,1,1"""

private val realInput get() = loadInput(12)
