import kotlin.system.exitProcess

const val filename = "input.txt"
const val testFile = "test.txt"

val input = Reader(if (System.getenv("TEST") == "True") testFile else filename)

@Suppress("UNUSED_PARAMETER")
fun main(args: Array<String>) {
    val lines = input.readAll().split("\n\n")
    val moves = lines[0].split(',').map { it.toInt() }
    val boards = lines.drop(1).map{
        Board(it)
    }

    moves.forEach { move ->
        boards.forEach { board ->
            val last = (!board.winning && boards.minus(board).all{it.winning})
            board.play(move)
            if(board.winning && last){
                println(move*board.score)
                return
            }
        }
    }
}