package day10

import parseFile
import java.util.*

fun main() {
    val input = parseFile("src/main/kotlin/day10/input") { it }

    val testInput = """
        [({(<(())[]>[[{[]{<()<>>
        [(()[<>])]({[<{<<[]>>(
        {([(<{}[<>[]}>{[]{[(<()>
        (((({<>}<{<{<>}{[]{[]{}
        [[<[([]))<([[{}[[()]]]
        [{[{({}]{}}([{[{{{}}([]
        {<[[]]>}<{[{[{[]{()[[[]
        [<(<(<(<{}))><([]([]()
        <{([([[(<>()){}]>(<<{{
        <{([{{}}[<[[[<>{}]]]>[]]
    """.trimIndent().lines()

    println("Part 1: " + part1(input ))
    //println("Part 2: " + part2(input))
}

fun part1(input: List<String>): Long = input.map { check(it) }.sum()

fun check(s: String): Long {
    val stack: Deque<Char> = ArrayDeque<Char>()

    s.forEach {
        if (it.isOpeningChunk()) {
            stack.add(it)
        } else {
            if (it.isCorrectClosing(stack.peekLast())) {
                stack.pollLast()
            } else {
                return it.points()
            }
        }
    }

    return 0
}

private fun Char.points() = when (this) {
    ')' -> 3L
    '}' -> 1197L
    ']' -> 57L
    '>' -> 25137L
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