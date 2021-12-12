package day12

import parseFile

fun main() {

    val input = parseFile("src/main/kotlin/day12/input") { it.map { Pair(it.split("-")[0], it.split("-")[1]) } }

    val testInput = """
                    fs-end
                    he-DX
                    fs-he
                    start-DX
                    pj-DX
                    end-zg
                    zg-sl
                    zg-pj
                    pj-he
                    RW-he
                    fs-DX
                    pj-RW
                    zg-RW
                    start-pj
                    he-WI
                    zg-he
                    pj-fs
                    start-RW
    """.trimIndent().lines().map {
        Pair(it.split("-")[0], it.split("-")[1])
    }

    println("Part 1: " + part1(testInput))
    println("Part 1: " + part1(input))
}

fun part1(input: List<Pair<String, String>>): Int {

    val allPaths = input + input.map { Pair(it.second, it.first) }

    val mapped: Map<String, Set<String>> = allPaths.groupBy({ it.first }, { it.second }).mapValues { it.value.toSet() }

    val paths = mutableSetOf<List<String>>()

    mapped["start"]?.forEach {
        paths(mapped, paths, mutableListOf("start"))
    }

    return paths.size
}

private fun paths(mappedInput: Map<String, Set<String>>, paths: MutableSet<List<String>>, path: MutableList<String>) {
    mappedInput[path.last()]?.forEach {
        if (it.allUpperCase() || !path.contains(it)) {
            if (it == "start") return@forEach
            if (it == "end") {
                paths.add(path.toMutableList().apply { add(it) })
            } else {
                paths(mappedInput, paths, path.toMutableList().apply {
                    add(it)
                })
            }
        }
    }
}

private fun String.allUpperCase(): Boolean = this.all { it.isUpperCase() }
