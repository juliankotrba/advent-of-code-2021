package day03

import parseFile

fun main() {
    val input = parseFile("src/main/kotlin/day03/input") { it }

    println("Part 1: " + part1(input))
    println("Part 2: " + part2(input))
}

fun part1(input: List<String>) =
        getMostCommonBitString(input).let {
            val gamma = Integer.parseInt(it, 2)
            val epsilon = Integer.parseInt(invertBin(it), 2)
            gamma * epsilon
        }

fun part2(input: List<String>): Int {
    var currInput = input
    var currIndex = 0

    // TODO: Simplify the two while loops to one

    while (currInput.size != 1) {
        val currMostCommonBits = getMostCommonBitString((currInput))
        currInput = currInput.filter { it[currIndex] == currMostCommonBits[currIndex] }
        currIndex += 1
    }

    val oxValue = Integer.parseInt(currInput[0], 2)

    currInput = input
    currIndex = 0

    while (currInput.size != 1) {
        val currLeastCommonBits = invertBin(getMostCommonBitString((currInput)))
        currInput = currInput.filter { it[currIndex] == currLeastCommonBits[currIndex] }
        currIndex += 1
    }


    val co2Value = Integer.parseInt(currInput[0], 2)

    return oxValue * co2Value
}

private fun getMostCommonBitString(input: List<String>): String {
    // TODO: Optimize

    val mutableCountMap = mutableMapOf<Int, Int>()

    input.forEach {
        it.forEachIndexed { i, c ->
            if (c == '1') {
                mutableCountMap[i] = mutableCountMap.getOrDefault(i, 0) + 1
            }
        }
    }

    val max = input.size

    return (input[0].indices).map {
        if (mutableCountMap[it] ?: 0 >= (max - (mutableCountMap[it] ?: 0))) {
            "1"
        } else {
            "0"
        }
    }.joinToString(separator = "")
}

private fun invertBin(b: String): String {
    return b.map { if (it == '1') "0" else "1" }.joinToString(separator = "")
}