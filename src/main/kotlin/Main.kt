const val filename = "input.txt"
const val testFile = "test.txt"
val input = Reader(if (System.getenv("TEST") == "True") testFile else filename)

@Suppress("UNUSED_PARAMETER")
fun main(args: Array<String>){
    val lines = input.readLines().map {
        it.replace("end", "END")
    }
    val nodes = lines.map { i ->
        i.split('-')
    }.fold(setOf()){ acc: Set<String>, j: List<String> ->
        acc + j
    }

    val edgeMap = nodes.associateWith<String, MutableSet<String>> {
        mutableSetOf()
    }
    lines.forEach {
        val split = it.split('-')
        edgeMap[split[0]]!!.add(split[1])
        edgeMap[split[1]]!!.add(split[0])
    }
    val graph = edgeMap.keys.associateWith {
        edgeMap[it]!!.toSet()
    }
    val paths = mutableListOf<List<String>>()
    graph["start"]!!

    explore("start", graph, listOf("start"), paths)

    println(paths.size)
}

fun explore(
    current: String,
    graph: Map<String, Set<String>>,
    curPath: List<String>,
    paths: MutableList<List<String>>)
{
    val edges = graph[current]!!
    if(current == "END"){
        paths.add(curPath)
        println(curPath)
        return
    }
    val unexplored = edges.filter{
        it.all{i -> i.isUpperCase()} || !curPath.contains(it)
    }
    unexplored.forEach {
        explore(it, graph, curPath.plus(it), paths)
    }
}

