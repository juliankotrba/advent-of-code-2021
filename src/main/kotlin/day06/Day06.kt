package day06

import parseFile

fun main() {
    val input = parseFile("src/main/kotlin/day06/input") { it }[0].split(",").map { it.toInt() }

    println("Part 1: " + part1(input))
    println("Part 2: " + part2(input))
}

fun part1(input: List<Int>) =
        input.groupBy { it.toLong() }
                .map {
                    countFish(it.key, 80) * it.value.size
                }
                .sum() + input.size


private fun part2(input: List<Int>) = input
        .groupBy { it.toLong() }
        .map {
            countFish(it.key, 256) * it.value.size
        }
        .sum() + input.size

private fun countFish(n: Long, days: Int): Long {
    var c = 0L

    var tmpDays = days
    var tmpN: Long = n
    while (tmpDays - (tmpN + 1) >= 0L) {
        tmpDays -= (tmpN.toInt() + 1)
        c += (1 + countFish(8, tmpDays))
        tmpN = 6
    }
    return c
}
