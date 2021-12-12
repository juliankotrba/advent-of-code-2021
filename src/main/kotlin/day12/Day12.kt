package day12

import parseFile

fun main() {

    val input = parseFile("src/main/kotlin/day12/input") { it.map { Pair(it.split("-")[0], it.split("-")[1]) } }

    println("Part 1: " + part1(input))
    println("Part 2: " + part2(input))
}

fun part1(input: List<Pair<String, String>>): Int {

    val allPaths = input + input.map { Pair(it.second, it.first) }
    val mapped: Map<String, Set<String>> = allPaths.groupBy({ it.first }, { it.second }).mapValues { it.value.toSet() }

    val paths = mutableSetOf<List<String>>()

    mapped["start"]?.forEach {
        paths1(mapped, paths, mutableListOf("start"))
    }

    return paths.size
}

fun part2(input: List<Pair<String, String>>): Int {

    val allPaths = input + input.map { Pair(it.second, it.first) }
    val mapped: Map<String, Set<String>> = allPaths.groupBy({ it.first }, { it.second }).mapValues { it.value.toSet() }

    val paths = mutableSetOf<List<String>>()

    mapped["start"]?.forEach {
        paths2(mapped, paths, mutableListOf("start"), false)
    }

    return paths.size
}

private fun paths1(mappedInput: Map<String, Set<String>>, paths: MutableSet<List<String>>, path: MutableList<String>) {
    mappedInput[path.last()]?.forEach {
        if (it.allUpperCase() || !path.contains(it)) { // TODO: Optimize contains check
            if (it == "start") return@forEach
            if (it == "end") {
                paths.add(path.toMutableList().apply { add(it) })
            } else {
                paths1(mappedInput, paths, path.toMutableList().apply {
                    add(it)
                })
            }
        }
    }
}

private fun paths2(mappedInput: Map<String, Set<String>>, paths: MutableSet<List<String>>, path: MutableList<String>, alreadyTwice: Boolean) {
    mappedInput[path.last()]?.forEach {
        // TODO: Restructure nesting
        if (it.allUpperCase()) {

            if (it == "start") return@forEach
            if (it == "end") {
                paths.add(path.toMutableList().apply { add(it) })
            } else {
                paths2(mappedInput, paths, path.toMutableList().apply {
                    add(it)
                }, alreadyTwice)
            }
        } else {
            if (!path.contains(it)) {  // TODO: Optimize contains check
                if (it == "start") return@forEach
                if (it == "end") {
                    paths.add(path.toMutableList().apply { add(it) })
                } else {
                    paths2(mappedInput, paths, path.toMutableList().apply {
                        add(it)
                    }, alreadyTwice)
                }
            } else if (path.contains(it) && !alreadyTwice) { // TODO: Optimize contains check
                if (it == "start") return@forEach
                if (it == "end") {
                    paths.add(path.toMutableList().apply { add(it) })
                } else {
                    paths2(mappedInput, paths, path.toMutableList().apply {
                        add(it)
                    }, true)
                }
            }
        }
    }
}

private fun String.allUpperCase(): Boolean = this.all { it.isUpperCase() }
