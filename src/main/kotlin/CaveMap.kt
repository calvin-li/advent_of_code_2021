class CaveMap (input: List<String>) {
    val width = input[0].count()
    val length = input.size
    val heights = input.map { lines ->
        lines.map { chars ->
            chars.code-'0'.code
        }.toIntArray()
    }.toTypedArray()

    fun getSurround(y: Int, x: Int): List<Pair<Int, Int>> {
        val coords = listOf(
            Pair(x-1, y),
            Pair(x+1, y),
            Pair(x, y-1),
            Pair(x, y+1),
        ).filter { p ->
            p.first in 0 until width && p.second in 0 until length
        }
        return coords
    }
    fun getVal(p: Pair<Int, Int>) = heights[p.second][p.first]
}