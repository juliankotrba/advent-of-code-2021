package day21

fun main() {

    val input = Pair(9,10)

    println("Part 1: " + part1(input))
}

fun part1(input: Pair<Int, Int>): Int {

    var diceValue = 6
    var diceRollCount = 0

    var p1Score = 0
    var p1Space = input.first

    var p2Score = 0
    var p2Space = input.second

    while(p1Score < 1000 && p2Score < 1000) {

         if((p1Space + diceValue) % 10 == 0) {
             p1Score += 10
            p1Space = 10
        } else {
             p1Score += (p1Space + diceValue) % 10
             p1Space = (p1Space + diceValue) % 10
        }

        diceValue = newDiceValue(diceValue)
        diceRollCount+=3

        if (p1Score >=1000){
            return p2Score*diceRollCount
        }

        // TODO: Generalize duplicated code
        if((p2Space + diceValue) % 10 == 0) {
            p2Score += 10
            p2Space = 10
        } else {
            p2Score += (p2Space + diceValue) % 10
            p2Space = (p2Space + diceValue) % 10
        }

        diceValue = newDiceValue(diceValue)
        diceRollCount+=3

        if (p2Score >=1000){
            return p1Score*diceRollCount
        }
    }

    error("One player must win")
}

private fun newDiceValue(cur: Int): Int {
    return when (cur) {
        297 -> return 200
        200 -> return 103
        103 -> return 6
        else -> cur+9
    }
}
