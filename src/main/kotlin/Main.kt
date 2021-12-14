import kotlin.math.pow

const val filename = "C:\\Users\\clvn1\\Downloads\\input"
const val testFile = "test"
val input = Reader(filename)

@Suppress("UNUSED_PARAMETER")
fun main(args: Array<String>) {
    val rows: List<String> = input.readLines()
    val strSize = rows[0].count()

    var oxygen: List<String> = rows
    var co2: List<String> = rows

    for (index in 0 until strSize){
        val common = common(transpose(oxygen))
        oxygen = oxygen.filter{
            it[index] == common[index]
        }
        if (oxygen.count() == 1){
            break
        }
    }
    for (index in 0 until strSize){
        val uncommon = flip(common(transpose(co2)))
        co2 = co2.filter{
            it[index] == uncommon[index]
        }
        if (co2.count() == 1){
            break
        }
    }

    val oValue = toBinary(oxygen)
    val cValue = toBinary(co2)

    println(oxygen)
    println(oValue)
    println(co2)
    println(cValue)

    println()
    println(oValue * cValue)
}

private fun toBinary(oxygen: List<String>) = oxygen[0].fold(0) { v, i ->
    if (i == '1') {
        v * 2 + 1
    } else {
        v * 2
    }
}

private fun common(cols: List<String>) = cols.map { col ->
    if (col.count { it == '1' } >= col.count { it == '0' }) {
        '1'
    } else {
        '0'
    }
}

private fun flip(common: List<Char>) = common.map {
    if (it == '0') {
        '1'
    } else {
        '0'
    }
}

private fun transpose(rows: List<String>): List<String> {
    val cols = (0 until rows[0].count()).map { i ->
        rows.fold("") { c, r ->
            c + r[i]
        }
    }
    return cols
}