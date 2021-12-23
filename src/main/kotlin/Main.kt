const val filename = "input.txt"
const val testFile = "test.txt"
val input = Reader(if (System.getenv("TEST") == "True") testFile else filename)

@Suppress("UNUSED_PARAMETER")
fun main(args: Array<String>) {
    val arr = input.readLines().map { i ->
        i.map { j ->
            j.code - '0'.code
        }.toTypedArray()
    }.toTypedArray()
    val grid = Grid(arr)

    val willFlash = mutableListOf<Pair<Int,Int>>()

    var stepNum = 1
    while(true) {
        if(step(grid, willFlash) == 100){
            println(stepNum)
            return
        }
        stepNum++
    }
}

private fun step(
    grid: Grid<Int>,
    willFlash: MutableList<Pair<Int, Int>>,
): Int {
    var flashes = 0
    while (willFlash.isNotEmpty()) {
        val box = grid.getBox(willFlash.removeAt(0))
        box.forEach { p ->
            grid[p.first][p.second] += 1
            if (grid[p.first][p.second] == 9) {
                willFlash.add(p)
            }
        }
    }
    grid.forEachCell { x, y ->
        if(grid[x][y] >= 9){
            grid[x][y] = 0
            flashes++
        }
        else {
            grid[x][y] += 1
            if (grid[x][y] == 9) {
                willFlash.add(Pair(x, y))
            }
        }
    }
    return flashes
}
