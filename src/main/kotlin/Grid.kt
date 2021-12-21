class Grid(input: List<String>) {
    val nums = input.map {
        i -> i.map {
            j -> j.code - '0'.code
        }.toIntArray()
    }.toTypedArray()

    fun getAround(x: Int, y:Int){

    }
}