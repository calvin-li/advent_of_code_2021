const val filename = "input.txt"
const val testFile = "test.txt"
val input = Reader(if (System.getenv("TEST") == "True") testFile else filename)

@Suppress("UNUSED_PARAMETER")
fun main(args: Array<String>) {
    val min = mutableListOf<Pair<Int, Int>>()
    val cave = CaveMap(input.readLines())
    for(i in 0 until cave.width){
        for(j in 0 until cave.length){
            val curPair = Pair(i, j)
            val height = cave.getHeight(curPair)
            val surround = cave.getSurround(j, i).map { p ->
                cave.heights[p.second][p.first]
            }
            if(surround.all { s -> s > height }){
                min.add(curPair)
            }
        }
    }

    val basins = min.map { cave.getBasins(it) }
    val sizes = basins.map {
        it.sumOf { i ->
            i.sumOf { j ->
                val ans: Int = if(j){1}else{0}
                ans
            }
        }
    }
    println(sizes.sorted().takeLast(3).reduce { acc, i -> acc*i })
}
