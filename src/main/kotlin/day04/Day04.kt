package day04

import parseFile

fun main() {
    val input = parseFile("src/main/kotlin/day04/input", ::parseGameData)

    println("Part 1: " + part1(input))
    println("Part 2: " + part2(input))
}

fun part1(input: GameData): Int {

    val markedList: Array<MutableSet<Position>> = Array(input.fieldData.size) { mutableSetOf<Position>() }

    input.numbers.forEach { drawnNumber ->

        input.fieldData.forEachIndexed { fieldIndex, field ->

            field.first[drawnNumber]?.let {
                markedList[fieldIndex].add(it)
            }

        }

        markedList.withIndex().firstOrNull { checkWon(it.value) }?.let {

            val all = input.fieldData[it.index].second
            markedList[it.index].forEach { markedPosition ->
                all.remove(markedPosition)
            }

            return all.values.sum() * drawnNumber
        }
    }

    error("One field must win")
}

fun part2(input: GameData): Int {

    var points = 0
    val markedList: Array<MutableSet<Position>> = Array(input.fieldData.size) { mutableSetOf<Position>() }
    val alreadyWonIndices = mutableSetOf<Int>()

    input.numbers.forEach { drawnNumber ->

        input.fieldData.forEachIndexed { fieldIndex, field ->

            field.first[drawnNumber]?.let {
                markedList[fieldIndex].add(it)
            }
        }


        markedList.withIndex()
                .filter { checkWon(it.value) && !alreadyWonIndices.contains(it.index) }
                .forEach {
                    val all = input.fieldData[it.index].second
                    markedList[it.index].forEach { markedPosition ->
                        all.remove(markedPosition)
                    }

                    points = all.values.sum() * drawnNumber
                    alreadyWonIndices.add(it.index)
                }

    }

    return points
}

private fun checkWon(marked: Set<Position>): Boolean {
    return marked.containsAll((0..4).map { Pair(0, it) }) ||
            marked.containsAll((0..4).map { Pair(1, it) }) ||
            marked.containsAll((0..4).map { Pair(2, it) }) ||
            marked.containsAll((0..4).map { Pair(3, it) }) ||
            marked.containsAll((0..4).map { Pair(4, it) }) ||

            marked.containsAll((0..4).map { Pair(it, 0) }) ||
            marked.containsAll((0..4).map { Pair(it, 1) }) ||
            marked.containsAll((0..4).map { Pair(it, 2) }) ||
            marked.containsAll((0..4).map { Pair(it, 3) }) ||
            marked.containsAll((0..4).map { Pair(it, 4) })

}

typealias Position = Pair<Int, Int>
typealias FieldValue = Int
typealias FieldValueToPositionMap = MutableMap<FieldValue, Position>
typealias PositionToFieldValueMap = MutableMap<Position, FieldValue>

data class GameData(
        val numbers: List<Int>,
        val fieldData: List<Pair<FieldValueToPositionMap, PositionToFieldValueMap>>
)

private fun parseGameData(list: List<String>): GameData {
    val numbers = list[0].split(",").map { it.toInt() }

    val fieldData = mutableListOf<Pair<FieldValueToPositionMap, PositionToFieldValueMap>>()
    var currentFieldValueToPositionMap: FieldValueToPositionMap = mutableMapOf()
    var currentPositionToFieldValueMap: PositionToFieldValueMap = mutableMapOf()

    var row = 0

    (2 until list.size).forEach { line ->
        if (list[line].isBlank()) {
            fieldData.add(Pair(currentFieldValueToPositionMap, currentPositionToFieldValueMap))
            row = 0
            currentFieldValueToPositionMap = mutableMapOf()
            currentPositionToFieldValueMap = mutableMapOf()
        } else {
            list[line].split(" ").filter { it.isNotBlank() }.forEachIndexed { i, fieldValue ->
                currentFieldValueToPositionMap[fieldValue.toInt()] = Pair(row, i)
                currentPositionToFieldValueMap[Pair(row, i)] = fieldValue.toInt()
            }

            row++
        }
    }

    fieldData.add(Pair(currentFieldValueToPositionMap, currentPositionToFieldValueMap))

    return GameData(numbers, fieldData)
}
