package day11

import parseFile

fun main() {

    println("Part 1: " + part1(parseFile("src/main/kotlin/day11/input") { it.map { it.map { c -> Character.getNumericValue(c) }.toMutableList() } }))
    println("Part 2: " + part2(parseFile("src/main/kotlin/day11/input") { it.map { it.map { c -> Character.getNumericValue(c) }.toMutableList() } }))
}

fun part1(input: List<MutableList<Int>>): Long {
    var flashes = 0L

    repeat(100) {
        step(input)

        flashes += input.map { it.count { it == 0 } }.sum()
    }

    return flashes
}

fun part2(input: List<MutableList<Int>>): Int {
    var count = 1

    while (true) {
        step(input)

        if (input.map { it.count { it == 0 } }.sum() == input.size * input[0].size) {
            return count
        }
        count++
    }
}

private fun step(input: List<MutableList<Int>>) {
    val alreadyFlashed = mutableSetOf<String>()
    for (r in input.indices) {
        for (c in input[0].indices) {
            input[r][c] = input[r][c] + 1
        }
    }

    for (r in input.indices) {
        for (c in input[0].indices) {
            if (input[r][c] > 9 && !alreadyFlashed.contains("$r$c")) {
                flash(r, c, input, alreadyFlashed)
            }
        }
    }

    for (r in input.indices) {
        for (c in input[0].indices) {
            if (input[r][c] > 9) {

                if (!alreadyFlashed.contains("$r$c")) {
                    error("$r, $c")
                }
                input[r][c] = 0
            }
        }
    }
}

private fun flash(r: Int, c: Int, input: List<MutableList<Int>>, alreadyFlashed: MutableSet<String>) {
    alreadyFlashed.add("$r$c")
    val left = input[r].getOrElse(c - 1) { -1 }
    val topLeft = if (r > 0) input[r - 1].getOrElse(c - 1) { -1 } else -1
    val right = input[r].getOrElse(c + 1) { -1 }
    val topRight = if (r > 0) input[r - 1].getOrElse(c + 1) { -1 } else -1
    val top = if (r > 0) input[r - 1][c] else -1
    val bottom = if (r < input.size - 1) input[r + 1][c] else -1
    val bottomLeft = if (r < input.size - 1) input[r + 1].getOrElse(c - 1) { -1 } else -1
    val bottomRight = if (r < input.size - 1) input[r + 1].getOrElse(c + 1) { -1 } else -1

    if (left != -1) {
        input[r][c - 1] = left + 1
    }
    if (topLeft != -1) {
        input[r - 1][c - 1] = topLeft + 1
    }
    if (right != -1) {
        input[r][c + 1] = right + 1
    }
    if (topRight != -1) {
        input[r - 1][c + 1] = topRight + 1
    }
    if (top != -1) {
        input[r - 1][c] = top + 1
    }
    if (bottom != -1) {
        input[r + 1][c] = bottom + 1
    }
    if (bottomRight != -1) {
        input[r + 1][c + 1] = bottomRight + 1
    }
    if (bottomLeft != -1) {
        input[r + 1][c - 1] = bottomLeft + 1
    }

    if (left >= 9 && !alreadyFlashed.contains("$r${c - 1}")) {
        flash(r, c - 1, input, alreadyFlashed)
    }

    if (right >= 9 && !alreadyFlashed.contains("${r}${c + 1}")) {
        flash(r, c + 1, input, alreadyFlashed)
    }

    if (top >= 9 && !alreadyFlashed.contains("${r - 1}${c}")) {
        flash(r - 1, c, input, alreadyFlashed)
    }

    if (topLeft >= 9 && !alreadyFlashed.contains("${r - 1}${c - 1}")) {
        flash(r - 1, c - 1, input, alreadyFlashed)
    }
    if (topRight >= 9 && !alreadyFlashed.contains("${r - 1}${c + 1}")) {
        flash(r - 1, c + 1, input, alreadyFlashed)
    }
    if (bottom >= 9 && !alreadyFlashed.contains("${r + 1}${c}")) {
        flash(r + 1, c, input, alreadyFlashed)
    }
    if (bottomRight >= 9 && !alreadyFlashed.contains("${r + 1}${c + 1}")) {
        flash(r + 1, c + 1, input, alreadyFlashed)
    }
    if (bottomLeft >= 9 && !alreadyFlashed.contains("${r + 1}${c - 1}")) {
        flash(r + 1, c - 1, input, alreadyFlashed)
    }

}