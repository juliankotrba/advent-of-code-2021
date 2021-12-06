package day06

import parseFile

fun main() {

    val testInput = """3,4,3,1,2""".trimIndent().split(",").map { it.toInt() }

    val input = parseFile("src/main/kotlin/day06/input") { it }[0].split(",").map { it.toInt() }

    println("Part 1: " + part1(testInput, 80))
    println("Part 1: " + part1(input, 80))
    println("Part 2: " + part1(input, 256)) // TODO: Way to slow for part 2
}

fun part1(input: List<Int>, days: Int): Int {
    var mutableList: MutableList<Int> = input.toMutableList()

    repeat(days) {

        val copy = mutableList

        (0 until mutableList.size).forEach { index ->
            val newValue = copy[index] - 1

            if (newValue == -1) {
                copy[index] = 6
                copy.add(8)
            } else {
                copy[index] = newValue
            }
        }

        mutableList = copy
    }

    return mutableList.size
}

