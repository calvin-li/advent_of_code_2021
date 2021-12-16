import kotlin.math.abs

const val filename = "input.txt"
const val testFile = "test.txt"

val input = Reader(if (System.getenv("TEST") == "True") testFile else filename)

@Suppress("UNUSED_PARAMETER")
fun main(args: Array<String>) {
    val crabs = input.readLine().split(',').map { it.toInt() }
    val max = crabs.maxOf { it }
    val crabCount = IntArray(max+1)
    val fuel = IntArray(max+1)

    crabs.forEach { crabCount[it]++ }

    for(i in 0..max){
        crabCount.forEachIndexed { index, c ->
            val f = abs(index - i)
            fuel[i] += c * f*(f+1)/2
        }
    }
    println(fuel.minOf{it})
}