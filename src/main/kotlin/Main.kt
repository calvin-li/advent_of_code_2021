import kotlin.math.min

const val filename = "input.txt"
const val testFile = "test.txt"
val input = Reader(if (System.getenv("TEST") == "True") testFile else filename)

@Suppress("UNUSED_PARAMETER")
fun main(args: Array<String>) {
    val risk = Grid(input.readLines().map {
        it.toCharArray().map {
            c -> c.code - '0'.code }.toTypedArray()
    }.toTypedArray())
    val size = risk[0].size
    val visited = Grid(Array(size){ Array(size){false} })
    val cost = Grid(Array(size){ Array(size){Int.MAX_VALUE} })
    cost[0][0] = 0

    val riskPretty = risk.prettyPrint()
    var visitedPretty = visited.prettyPrint()
    var costPretty = cost.prettyPrint()

    while(!visited[size-1][size-1]){
        val nonVisited = visited.filterCellIndex { x, y -> !visited[x][y] }
        val minCostIndex = nonVisited.minByOrNull { cost[it] }!!
        val minCost = cost[minCostIndex]
        val plus = risk.getPlus(minCostIndex)

        plus.forEach {
            val newCost = minCost + risk[it]
            cost[it] = min(newCost, cost[it])
        }
        visited[minCostIndex] = true
        visitedPretty = visited.prettyPrint()
        costPretty = cost.prettyPrint()
    }
    println(cost[size-1][size-1])
}
