package day09

import parseFile
import kotlin.math.abs

fun main() {
    val input = parseFile("src/main/kotlin/day09/input") { it.map { it.map { c -> Character.getNumericValue(c) } } }

    println("Part 1: " + part1(input))
    println("Part 2: " + part2(input))
}

fun part1(input: List<List<Int>>): Int {
    println(input)
    var count = 0
    for (r in input.indices) {
        for (c in input[0].indices) {

            val left = input.get(r).getOrElse(c - 1, { 10 })
            val right = input.get(r).getOrElse(c + 1, { 10 })
            val top = if (r > 0) input[r - 1][c] else 10
            val bottom = if (r < input.size - 1) input[r + 1][c] else 10

            val cur = input[r][c]

            if (cur < left && cur < right && cur < top && cur < bottom) {
                count += (1 + cur)
            }
        }
    }
    return count
}

fun part2(input: List<List<Int>>): Int {

    val basinsCount = mutableListOf<Int>()

    for (r in input.indices) {
        for (c in input[0].indices) {

            val left = input.get(r).getOrElse(c - 1, { 99 })
            val right = input.get(r).getOrElse(c + 1, { 99 })
            val top = if (r > 0) input[r - 1][c] else 99
            val bottom = if (r < input.size - 1) input[r + 1][c] else 99

            val cur = input[r][c]

            if (cur < left && cur < right && cur < top && cur < bottom) {
                val basins = getBasins(r, c, input, mutableListOf(cur), mutableSetOf("$r$c"))
                basinsCount.add(basins.count())
            }
        }
    }

    basinsCount.sortDescending()

    return basinsCount.take(3).reduce(Int::times)
}

private fun getBasins(r: Int, c: Int, input: List<List<Int>>, basins: MutableList<Int>, addedPositions: MutableSet<String>): List<Int> {
    val cur = input[r][c]

    val left = input[r].getOrElse(c - 1) { -1 }
    if (left > cur && !addedPositions.contains("$r${c - 1}") && left != 9) {

        basins.add(left)
        addedPositions.add("$r${c - 1}")
        getBasins(r, c - 1, input, basins, addedPositions)
    }

    val right = input[r].getOrElse(c + 1) { -1 }
    if (right > cur && !addedPositions.contains("$r${c + 1}") && right != 9) {
        basins.add(right)
        addedPositions.add("$r${c + 1}")
        getBasins(r, c + 1, input, basins, addedPositions)
    }

    val top = if (r > 0) input[r - 1][c] else -1
    if (top > cur && !addedPositions.contains("${r - 1}$c") && top != 9) {
        basins.add(top)
        addedPositions.add("${r - 1}$c")
        getBasins(r - 1, c, input, basins, addedPositions)
    }

    val bottom = if (r < input.size - 1) input[r + 1][c] else -1
    if (bottom > cur && !addedPositions.contains("${r + 1}$c") && bottom != 9) {
        basins.add(bottom)
        addedPositions.add("${r + 1}$c")
        getBasins(r + 1, c, input, basins, addedPositions)
    }


    return basins
}