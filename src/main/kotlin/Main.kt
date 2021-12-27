const val filename = "input.txt"
const val testFile = "test.txt"
val input = Reader(if (System.getenv("TEST") == "True") testFile else filename)

@Suppress("UNUSED_PARAMETER")
fun main(args: Array<String>) {
    val template = input.readLine()
    input.readLine()
    val rules = input.readLines().associate {
        it.split(" -> ").let { (pair, rule) -> pair to rule}
    }
    val endLetters = "${template.first()}${template.last()}"
    var pairCount: Map<String, Long> = template.dropLast(1).zip(template.drop(1)).map {
        "${it.first}${it.second}"
    }.groupingBy { it }.eachCount().toList().associate{ it.first to it.second.toLong() }
    val steps = 40
    (1..steps).forEach { _ ->
        pairCount = step(pairCount, rules)
    }
    val letterCount = mutableMapOf<Char, Long>()
    pairCount.forEach { (t, u) ->
        t.forEach { c ->
            letterCount[c] = (letterCount[c] ?: 0) + u
        }
    }
    endLetters.forEach { letterCount[it] = letterCount[it]!! + 1 }
    letterCount.forEach { (t, _) -> letterCount[t] = letterCount[t]!!/2 }

    print(letterCount.values.maxOf { it } - letterCount.values.minOf { it })
}

fun step(pairCount: Map<String, Long>, rules: Map<String, String>): Map<String, Long> {
    val newPairCount = mutableMapOf<String, Long>()
    pairCount.keys.forEach { pair ->
        if(rules[pair] != null){
            val newLetter = rules[pair]
            val count = pairCount[pair]!!
            listOf("${pair[0]}$newLetter", "$newLetter${pair[1]}").forEach { i ->
                newPairCount[i] = (newPairCount[i] ?: 0) + count
            }
        }
    }
    return newPairCount
}
