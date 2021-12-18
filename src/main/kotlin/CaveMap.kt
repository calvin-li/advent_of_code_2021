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
    fun getHeight(p: Pair<Int, Int>) = heights[p.second][p.first]
    fun getBasins(min: Pair<Int, Int>): Array<BooleanArray> {
        val basin = Array(length){ BooleanArray(width) }
        val q = mutableListOf(min)
        while (q.isNotEmpty()){
            val cur = q.removeAt(0)
            basin[cur.second][cur.first] = true
            val surr = getSurround(cur).filter {
                !basin[it.second][it.first] && getHeight(it) != 9
            }
            q.addAll(surr)
        }
        return basin
    }

    fun getHeight(x: Int, y: Int) = getHeight(Pair(x,y))
    private fun getSurround(p: Pair<Int, Int>): List<Pair<Int, Int>> =
        getSurround(p.second, p.first)
}