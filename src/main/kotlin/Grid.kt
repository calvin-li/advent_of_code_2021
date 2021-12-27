@Suppress("MemberVisibilityCanBePrivate")
class Grid<E>(input: Array<Array<E>>): Collection<E>{
    private val cells = input
    val height = input.size
    val width = if(height==0){0}else{input[0].size}

    override val size = width*height

    operator fun get(i: Int) = cells[i]
    operator fun get(p: Pair<Int, Int>): E = cells[p.first][p.second]
    operator fun set(index: Int, value: E) {
        cells[index/height][index%width] = value
    }
    operator fun set(p: Pair<Int, Int>, value: E) {
        cells[p.first][p.second] = value
    }
    override fun contains(element: E): Boolean = this.contains(element)
    override fun containsAll(elements: Collection<E>): Boolean = this.containsAll(elements)
    @Suppress("ReplaceSizeZeroCheckWithIsEmpty")
    override fun isEmpty(): Boolean = size == 0

    override fun iterator(): Iterator<E> {
        return cells.reduce { acc, es -> acc.plus(es)  }.iterator()
    }

    fun getBox(y: Int, x:Int): List<Pair<Int, Int>> {
        val box = mutableListOf<Pair<Int, Int>>()
        for(i in y-1..y+1){
            for(j in x-1..x+1){
                box.add(Pair(i,j))
            }
        }
        return box.filterNot { !inBounds(it) || it.first == y && it.second == x }
    }

    fun getPlus(y: Int, x:Int): List<Pair<Int, Int>> {
        val plus = mutableListOf<Pair<Int, Int>>()
        plus.add(Pair(y-1, x))
        plus.add(Pair(y+1, x))
        plus.add(Pair(y, x-1))
        plus.add(Pair(y, x+1))
        return plus.filterNot { !inBounds(it) }
    }

    private fun inBounds(cell: Pair<Int, Int>): Boolean {
        return cell.first in 0 until height &&
                cell.second in 0 until width
    }

    fun forEachCell(action: (x: Int, y:Int) -> Unit) {
        for(i in 0 until height){
            for(j in 0 until width){
                action(i,j)
            }
        }
    }

    fun mapCell(action: (x: Int, y:Int) -> E) {
        forEachCell { i, j -> cells[i][j] = action(i,j)}
    }

    fun filterCellIndex(action: (x: Int, y: Int) -> Boolean):
            List<Pair<Int, Int>> {
        val result = mutableListOf<Pair<Int, Int>>()
        forEachCell { i, j ->
            if(action(i,j)){
                result.add(Pair(i, j))
            }
        }
        return result.toList()
    }

    fun getBox(p: Pair<Int, Int>): List<Pair<Int, Int>> = getBox(p.first, p.second)

    fun prettyPrint(): String =
        cells.joinToString("\n") { i ->
            i.joinToString(" ") { j -> j.toString() }
        }

    fun getPlus(p: Pair<Int, Int>): List<Pair<Int, Int>> = getPlus(p.first, p.second)
}