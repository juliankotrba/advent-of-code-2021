package day07

import parseFile
import kotlin.math.abs

fun main() {

    val input = parseFile("src/main/kotlin/day07/input") { it }[0].split(",").map { it.toInt() }

    println("Part 1: " + part1(input))
    println("Part 2: " + part2(input))
}

fun part1(input: List<Int>) = input.map { alignP1(it, input) }.min()!!
fun part2(input: List<Int>) = input.map { alignP2(it, input) }.min()!!

private fun alignP1(pos: Int, input: List<Int>) = input.map { abs(it - pos) }.sum()

private fun alignP2(pos: Int, input: List<Int>) =
        input.map {
            val n = abs(it - pos)
            (n * (n + 1) / 2)
        }.sum()

