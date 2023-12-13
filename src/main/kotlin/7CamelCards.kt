val camelCards = arrayOf(
    ::camelCards0,
    ::camelCards1,
)

fun camelCards0(): String {
    val cardValues = mapOf(
        'A' to 14,
        'K' to 13,
        'Q' to 12,
        'J' to 11,
        'T' to 10,
        '9' to 9,
        '8' to 8,
        '7' to 7,
        '6' to 6,
        '5' to 5,
        '4' to 4,
        '3' to 3,
        '2' to 2,
    )

    data class Hand(val cards: String, val bid: Int)

    fun score(hand: String): Int {
        val cardsByValue = hand.groupBy { it }
        return if (cardsByValue.any { e -> e.value.size == 5 }) {
            5
        } else if (cardsByValue.any { e -> e.value.size == 4 }) {
            4
        } else if (cardsByValue.any { e -> e.value.size == 3 } && cardsByValue.any { e -> e.value.size == 2 }) {
            3
        } else if (cardsByValue.any { e -> e.value.size == 3 }) {
            2
        } else if (cardsByValue.filter { e -> e.value.size == 2 }.size == 2) {
            1
        } else if (cardsByValue.filter { e -> e.value.size == 2 }.size == 1) {
            0
        } else {
            -1
        }
    }

    fun compare(hand1: String, hand2: String): Int {
        val score1 = score(hand1)
        val score2 = score(hand2)
        return if (score1 != score2) {
            score1 - score2
        } else {
            hand1.zip(hand2).map { p -> cardValues[p.first]!! - cardValues[p.second]!! }.firstOrNull { it != 0 } ?: 0
        }
    }

    val handParser = "(\\w+) (\\d+)".toRegex()
    val hands = input.lines().map { l ->
        val parts = handParser.find(l)!!.groupValues
        Hand(parts[1], parts[2].toInt())
    }

    val sortedHands = hands.sortedWith { l, r -> compare(l.cards, r.cards)}
    return sortedHands.mapIndexed { i, h -> (i + 1) * h.bid }
        .sum()
        .toString()
}

fun camelCards1(): String {
    val cardValues = mapOf(
        'A' to 14,
        'K' to 13,
        'Q' to 12,
        'T' to 10,
        '9' to 9,
        '8' to 8,
        '7' to 7,
        '6' to 6,
        '5' to 5,
        '4' to 4,
        '3' to 3,
        '2' to 2,
        'J' to 1,
    )

    data class Hand(val cards: String, val bid: Int)

    fun score(hand: String): Int {
        var cardsByValue = hand.groupBy { it }
        if (hand.contains('J') && hand.any { c -> c != 'J' }) {
            val maxCard = cardsByValue.filter { e -> e.value[0] != 'J' }.maxBy { e -> e.value.size }
            val newHand = hand.replace('J', maxCard.value[0])
            cardsByValue = newHand.groupBy { it }
        }
        return if (cardsByValue.any { e -> e.value.size == 5 }) {
            5
        } else if (cardsByValue.any { e -> e.value.size == 4 }) {
            4
        } else if (cardsByValue.any { e -> e.value.size == 3 } && cardsByValue.any { e -> e.value.size == 2 }) {
            3
        } else if (cardsByValue.any { e -> e.value.size == 3 }) {
            2
        } else if (cardsByValue.filter { e -> e.value.size == 2 }.size == 2) {
            1
        } else if (cardsByValue.filter { e -> e.value.size == 2 }.size == 1) {
            0
        } else {
            -1
        }
    }

    fun compare(hand1: String, hand2: String): Int {
        val score1 = score(hand1)
        val score2 = score(hand2)
        return if (score1 != score2) {
            score1 - score2
        } else {
            hand1.zip(hand2).map { p -> cardValues[p.first]!! - cardValues[p.second]!! }.firstOrNull { it != 0 } ?: 0
        }
    }

    val handParser = "(\\w+) (\\d+)".toRegex()
    val hands = input.lines().map { l ->
        val parts = handParser.find(l)!!.groupValues
        Hand(parts[1], parts[2].toInt())
    }

    val sortedHands = hands.sortedWith { l, r -> compare(l.cards, r.cards)}
    return sortedHands.mapIndexed { i, h -> (i + 1) * h.bid }
        .sum()
        .toString()
}

private val input get() = loadInput(7)
