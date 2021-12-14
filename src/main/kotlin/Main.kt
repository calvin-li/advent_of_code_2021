const val filename = "input.txt"
const val testFile = "test.txt"

val input = Reader(if (System.getenv("TEST") == "True") testFile else filename)

@Suppress("UNUSED_PARAMETER")
fun main(args: Array<String>) {
    val lines = input.readLines()
    val vents = mutableListOf<IntArray>()
    lines.forEach {line ->
        vents.add(line.replace(" -> ", ",").split(',').map { it.toInt() }.toIntArray())
    }

    val xMax = 1 + vents.maxOf { it.filterIndexed { index, _ -> index % 2 == 0 }.maxOf{i->i} }
    val yMax = 1 + vents.maxOf { it.filterIndexed { index, _ -> index % 2 == 1 }.maxOf{i->i} }

    val smoke = Array<IntArray>(yMax){ IntArray(xMax) }

    vents.forEach { vent ->
        val x1 = vent[0]
        val y1 = vent[1]
        val x2 = vent[2]
        val y2 = vent[3]

        getRange(x1, y1, x2, y2).forEach{
            smoke[it.second][it.first]++
        }
    }
    println(smoke.sumOf { it.count { j -> j>1 }})
}

fun getRange(x1: Int, y1: Int, x2: Int, y2: Int): List<Pair<Int, Int>> {
    if (x1 == x2){
        val r = if(y1 <= y2){(y1..y2)}else{(y1 downTo y2)}
        return r.map { Pair(x1, it) }
    }
    else if (y1 == y2){
        val r = if(x1 <= x2){(x1..x2)}else{(x1 downTo x2)}
        return r.map { Pair(it, y1) }
    }
    else{
        var start = Pair(x1, y1)
        var end = Pair(x2, y2)
        if(x1 > x2){
            val temp = start
            start = end
            end = temp
        }
        return (start.first..end.first).mapIndexed { index, x ->
            if(start.second < end.second){
                Pair(x, start.second + index)
            }else{
                Pair(x, start.second - index)
            }
        }
    }
}
