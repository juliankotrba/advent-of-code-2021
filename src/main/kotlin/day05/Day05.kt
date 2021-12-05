package day05

import parseFile
import java.util.regex.Pattern

fun main() {
    val input = parseFile("src/main/kotlin/day05/input") { parsePositions(it) }

    println("Part 1: " + part1(input))
    println("Part 2: " + part2(input))
}

typealias Position = Pair<Int, Int>
typealias Line = Pair<Position, Position>

fun part1(input: List<Line>) =
        input.flatMap { getPositionsBetween(it, false) }
                .groupBy { it }
                .count { it.value.size > 1 }

fun part2(input: List<Line>) =
        input.flatMap { getPositionsBetween(it, true) }
                .groupBy { it }
                .count { it.value.size > 1 }

private fun getPositionsBetween(line: Line, includeDiagonal: Boolean): List<Position> {

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
        return if (includeDiagonal) {
            val xStep = if (start.first > end.first) -1 else 1
            val yStep = if (start.second > end.second) -1 else 1

            val positions = mutableListOf<Position>()

            var curr = line.first
            positions.add(curr)
            while (curr != line.second) {
                curr = Position(curr.first + xStep, curr.second + yStep)
                positions.add(curr)
            }

            positions
        } else emptyList()
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