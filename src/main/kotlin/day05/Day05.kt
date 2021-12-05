package day05

import javafx.geometry.Pos
import listOfInt
import parseFile
import java.util.regex.Matcher
import java.util.regex.Pattern

fun main() {

    val testInput = parsePositions("""
        0,9 -> 5,9
        8,0 -> 0,8
        9,4 -> 3,4
        2,2 -> 2,1
        7,0 -> 7,4
        6,4 -> 2,0
        0,9 -> 2,9
        3,4 -> 1,4
        0,0 -> 8,8
        5,5 -> 8,2
    """.trimIndent().lines())

    val input = parseFile("src/main/kotlin/day05/input") { parsePositions(it) }

    println("Part 1: " + part1(testInput))
    println("Part 1: " + part1(input))
}

typealias Position = Pair<Int, Int>
typealias Line = Pair<Position, Position>

fun part1(input: List<Line>) =
        input.flatMap { getPositionsBetween(it) }
                .groupBy { it }
                .count { it.value.size > 1 }

private fun getPositionsBetween(line: Line): List<Position> {

    val start = line.first
    val end = line.second

    return if (start.first == end.first && start.second == end.second) {
        listOf(start)
    } else if (start.first == end.first && start.second < end.second) {
        (start.second..end.second).map { Position(start.first, it) }
    } else if (start.first == end.first && start.second > end.second) {
        (end.second..start.second).map { Position(start.first, it) }
    } else if (start.second == end.second && start.first < end.first) {
        (start.first..end.first).map { Position(it, start.second) }
    } else if (start.second == end.second && start.first > end.first) {
        (end.first..start.first).map { Position(it, start.second) }
    } else {
        // diagonal - ignore
        listOf()
    }
}

private fun parsePositions(input: List<String>): List<Line> {

    val pattern = Pattern.compile("^([0-9]+),([0-9]+) -> ([0-9]+),([0-9]+)");

    return input.map {
        val matcher = pattern.matcher(it)

        if (matcher.matches()) {
            val from = Position(matcher.group(1).toInt(), matcher.group(2).toInt())
            val to = Position(matcher.group(3).toInt(), matcher.group(4).toInt())
            Line(from, to)
        } else error("Must match")
    }
}