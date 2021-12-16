const val filename = "input.txt"
const val testFile = "test.txt"
val input = Reader(if (System.getenv("TEST") == "True") testFile else filename)

@Suppress("UNUSED_PARAMETER")
fun main(args: Array<String>) {
    val min = mutableListOf<Int>()
    val cave = CaveMap(input.readLines())
    for(i in 0 until cave.width){
        for(j in 0 until cave.length){
            val height = cave.heights[j][i]
            val surround = cave.getSurround(j, i).map { p ->
                cave.heights[p.second][p.first]
            }
            if(surround.all { s -> s > height }){
                min.add(height)
            }
        }
    }
    print(min.sumOf { it+1 })
}
