const val filename = "input.txt"
const val testFile = "test.txt"
val input = Reader(if (System.getenv("TEST") == "True") testFile else filename)

@Suppress("UNUSED_PARAMETER")
fun main(args: Array<String>) {
    val openers = listOf('(', '[', '{', '<')
    val closers = listOf(')', ']', '}', '>')
    val scores = mutableListOf<Long>()
    val incomplete = mutableListOf<CharArray>()

    input.forEachLine {
        val stack = mutableListOf<Char>()
        it.forEach { ch ->
            if(openers.contains(ch)){
                stack.add(0, ch)
            }
            else{
                if(closers.indexOf(ch) == openers.indexOf(stack[0])){
                    stack.removeAt(0)
                }
                else{
                    return@forEachLine
                }
            }
        }
        incomplete.add(stack.toCharArray())
    }

    incomplete.forEach {
        scores.add(it.fold(0){total: Long, ch ->
            val index = openers.indexOf(ch)
            val score: Long = index.toLong()+1
            total*5 + score
        })
    }
    println(scores.sorted()[scores.size/2])
}
