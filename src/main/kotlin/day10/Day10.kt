package day10

import parseFile
import java.util.*

fun main() {
    val input = parseFile("src/main/kotlin/day10/input") { it }

    println("Part 1: " + part1(input))
    println("Part 2: " + part2(input))
}

fun part1(input: List<String>): Long = input.map { check(it) }.sum()
fun part2(input: List<String>): Long {

    val allPoints = input.filter {
        check(it) == 0L
    }.map { getClosingTags(it) }.sorted()

    return allPoints[allPoints.size.div(2)]
}

fun check(s: String): Long {
    val stack: Deque<Char> = ArrayDeque<Char>()

    s.forEach {
        if (it.isOpeningChunk()) {
            stack.add(it)
        } else {
            if (it.isCorrectClosing(stack.peekLast())) {
                stack.pollLast()
            } else {
                return it.points1()
            }
        }
    }

    return 0
}

fun getClosingTags(s: String): Long {
    val stack: Deque<Char> = ArrayDeque<Char>()
    var score = 0L

    s.forEach {
        if (it.isOpeningChunk()) {
            stack.add(it)
        } else {
            stack.pollLast()
        }
    }

    stack.reversed().map {
        when (it) {
            '(' -> ')'
            '{' -> '}'
            '[' -> ']'
            '<' -> '>'
            else -> error("Illegal character")
        }
    }.forEach {
        score *= 5
        score += it.points2()
    }


    return score
}

private fun Char.points1() = when (this) {
    ')' -> 3L
    '}' -> 1197L
    ']' -> 57L
    '>' -> 25137L
    else -> error("Illegal character $this")
}

private fun Char.points2() = when (this) {
    ')' -> 1L
    '}' -> 3L
    ']' -> 2L
    '>' -> 4L
    else -> error("Illegal character $this")
}

private fun Char.isCorrectClosing(headOfStack: Char): Boolean {
    return when (headOfStack) {
        '(' -> this == ')'
        '{' -> this == '}'
        '[' -> this == ']'
        '<' -> this == '>'
        else -> error("Illegal character $this")
    }
}

private fun Char.isOpeningChunk(): Boolean {
    return this == '(' || this == '[' || this == '{' || this == '<'
}