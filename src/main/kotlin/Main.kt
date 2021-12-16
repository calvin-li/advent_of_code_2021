const val filename = "input.txt"
const val testFile = "test.txt"

val input = Reader(if (System.getenv("TEST") == "True") testFile else filename)
val sevenSeg = mapOf(
    0 to "abcefg".toSet(),
    1 to "cf".toSet(),
    2 to "acdeg".toSet(),
    3 to "acdfg".toSet(),
    4 to "bcdf".toSet(),
    5 to "abdfg".toSet(),
    6 to "abdefg".toSet(),
    7 to "acf".toSet(),
    8 to "abcdefg".toSet(),
    9 to "abcdfg".toSet(),
)
val segCounts = mapOf(
    2 to setOf(1),
    3 to setOf(7),
    4 to setOf(4),
    5 to setOf(2,3,5),
    6 to setOf(0,6,9),
    7 to setOf(8),
)
val abcdefg = "abcdefg".toSet()

@Suppress("UNUSED_PARAMETER")
fun main(args: Array<String>) {
    var score = 0
    input.forEachLine {
        val inputs = it.split('|')[0].trim().split(' ').sortedBy { i -> i.count() }
        val outputs = it.split('|')[1].trim().split(' ')
        val mappings = mutableMapOf(
            'a' to abcdefg,
            'b' to abcdefg,
            'c' to abcdefg,
            'd' to abcdefg,
            'e' to abcdefg,
            'f' to abcdefg,
            'g' to abcdefg,
        )
        inputs.take(3).forEach { i ->
            unique(i, mappings)
        }
        val cde = getCDE(inputs)
        processCDE(cde, mappings)

        val converted = outputs.map { o ->
            o.map{i -> mappings[i]!!.first()}.toSet()
        }
        val values = converted.map { i ->
            sevenSeg.filter { j -> j.value == i }.keys.first()
        }
        score += values.reduce { acc, i -> acc*10 + i }
    }
    println(score)
}

fun processCDE(cde: Set<Char>, mappings: MutableMap<Char, Set<Char>>) {
    val segment = "cde".toSet()
    cde.forEach { k ->
        mappings[k] = mappings[k]!! intersect (segment)
    }
    abcdefg.subtract(cde.toSet()).forEach { k ->
        mappings[k] = mappings[k]!!.subtract(segment)
    }
}

fun getCDE(inputs: List<String>): Set<Char> {
    val cde = inputs.slice((6..8)).reduce { acc, s -> acc+s }.toList()
    return cde.filter { i -> cde.count { j -> j==i } == 2 }.sorted().toSet()
}

fun unique(key: String, mappings: MutableMap<Char, Set<Char>>) {
    val segCount: Int = segCounts[key.count()]!!.first()
    key.forEach { k ->
        mappings[k] = mappings[k]!! intersect (sevenSeg[segCount]!!)
    }
    abcdefg.subtract(key.toSet()).forEach { k ->
        mappings[k] = mappings[k]!!.subtract(sevenSeg[segCount]!!)
    }
}
