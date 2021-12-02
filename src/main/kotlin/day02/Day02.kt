package day02

import parseFile

fun main() {
    val input = parseFile("src/main/kotlin/day02/input") { it }.map {
        val split = it.split(" ")
        Pair(split[0], split[1].toInt())
    }

    println("Part 1: " + part1(input))
    println("Part 2: " + part2(input))
}

fun part1(input: List<Pair<String, Int>>): Int {
    var h = 0
    var d = 0

    input.forEach {
        when(it.first) {
            "forward" -> h += it.second
            "down" -> d += it.second
            "up" -> d -= it.second
        }
    }

    return h*d
}

fun part2(input: List<Pair<String, Int>>): Int {
    var h = 0
    var d = 0
    var a = 0

    input.forEach {
        when(it.first) {
            "forward" -> {
                h += it.second
                d += a * it.second
            }
            "down" -> a += it.second
            "up" -> a -= it.second
        }
    }

    return h*d
}

