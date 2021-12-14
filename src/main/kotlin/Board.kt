const val size = 5

class Board (nums: String) {
    private val cells = Array(size){IntArray(size)}
    private val marked = Array(size){BooleanArray(size)}
    var score = 0
    var winning = false

    init {
        nums.split('\n').forEachIndexed { i, row ->
            row.chunked(3).forEachIndexed { j, num ->
                cells[i][j] = num.trim().toInt()
                score += cells[i][j]
            }
        }
    }

    fun play(move: Int){
        val coords = find(move)
        if(coords != null){
            mark(coords)
        }
    }

    private fun find(num: Int): Pair<Int, Int>?{
        for(i in 0 until size){
            for(j in 0 until size){
                if (cells[i][j] == num){
                    return Pair(i, j)
                }
            }
        }
        return null
    }
    private fun mark(p: Pair<Int, Int>) = mark(p.first, p.second)
    private fun mark(i: Int, j: Int){
        marked[i][j] = true
        score -= cells[i][j]
        check(i, j)
    }

    private fun check(x: Int, y: Int){
        checkRow(x)
        checkCol(y)
        //checkDiag(x, y)
    }
    private fun checkRow(x: Int) {
        val row = (0 until size).map {y -> marked[x][y] }
        checkLine(row)
    }
    private fun checkCol(y: Int) {
        val col = (0 until size).map { x -> marked[x][y] }
        checkLine(col)
    }
    private fun checkDiag(x: Int, y: Int) {
        var diag: List<Boolean>? = null
        if(x == y){
            diag = (0 until size).map{d -> marked[d][d]}
        } else if (x + y == size - 1){
            diag = (0 until size).map{d ->
                marked[d][size-d-1]}
        }
        if (diag != null) {
            checkLine(diag)
        }
    }
    private fun checkLine(line: List<Boolean>) {
        line.all { it }.also { winning = it or winning }
    }
}