const val filename = "input.txt"
const val testFile = "test.txt"

val input = Reader(if (System.getenv("TEST") == "True") testFile else filename)

@Suppress("UNUSED_PARAMETER")
fun main(args: Array<String>) {
    val SPAWN = 7
    val DELAY = 2
    val DAYS = 80

    val fish = Array(DAYS){ IntArray(SPAWN+DELAY) }
    fish[0][0] = 2
    for(i in 1 until fish[0].count()){ fish[0][i] = 1}

    for(i in 1 until fish.count()){
        val prev = fish[i - 1]
        fish[i][0] = prev[6] + prev[8]
        for(j in 1 until prev.count()){
            fish[i][j] = prev[j-1]
        }
    }

    var total = 0
    input.readLine().split(',').forEach {
        total += fish.last()[it.toInt()]
    }

    println(total)
}