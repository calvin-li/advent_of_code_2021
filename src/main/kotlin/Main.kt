const val filename = "input.txt"
const val testFile = "test.txt"
val input = Reader(if (System.getenv("TEST") == "True") testFile else filename)

@Suppress("UNUSED_PARAMETER")
fun main(args: Array<String>) {
    val text = input.readAll()
    var dots = text.split("\n\n")[0].split('\n').map {
        it.split(',').map { s -> s.toInt() }
    }.map{ Pair(it[0], it[1])}.toSet()
    val folds = text.split("\n\n")[1].split('\n').map {
        it.split(' ').last().split('=')
    }.map{ Pair(it[0], it[1])}

    folds.forEach {
        dots = fold(dots, it)
    }

    val width = dots.maxOf { it.first }+1
    val height = dots.maxOf { it.second }+1

    val cells = Array(height){CharArray(width){'.'} }
    dots.forEach { cells[it.second][it.first] = '#' }

    cells.forEach {
        it.forEach { i ->
            (1..3).forEach { _ -> print(i) }
        }
        println()
    }
}

fun fold(dots: Set<Pair<Int, Int>>, fold: Pair<String, String>):
        Set<Pair<Int, Int>> {
    val horizontal = fold.first == "x"
    val line = fold.second.toInt()

    return dots.map { foldOne(it, horizontal, line) }.toSet()
}

fun foldOne(dot: Pair<Int, Int>, horizontal: Boolean, line: Int): Pair<Int, Int> {
    return if(horizontal){
        val diff = kotlin.math.max(dot.first-line, 0)
        Pair(dot.first-2*diff, dot.second)
    } else{
        val diff = kotlin.math.max(dot.second-line, 0)
        Pair(dot.first, dot.second-2*diff)
    }

}
