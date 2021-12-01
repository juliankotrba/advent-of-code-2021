package day01

import listOfInt
import parseFile

fun main() {
    val input = parseFile("src/main/kotlin/day01/input", ::listOfInt)

    println("Part 1: " + part1(input))
    println("Part 2: " + part2(input))

}

fun part1(input: List<Int>): Int = input.zipWithNext().count { it.second > it.first }
fun part2(input: List<Int>): Int = part1(input.triples().map { it.first + it.second + it.third })

private fun <T> List<T>.triples(): List<Triple<T, T, T>> {
    return this.mapIndexed { i, v ->
        if (i + 2 < this.size) {
            Triple(v, this[i + 1], this[i + 2])
        } else null
    }.filterNotNull()
}