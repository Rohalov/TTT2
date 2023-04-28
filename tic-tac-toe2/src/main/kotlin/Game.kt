import kotlin.random.Random

class Game (p1: String, p2: String, round: Int){
    private var winner : String

    init {
        winner = startPlay(p1,p2, round)
    }

    /**
     * Почати гру в хрестики нолики.
     * Цей метод описує основні механіки гри.
     * @return Нік переможця гри
     */
    private fun startPlay(p1: String, p2: String, round: Int): String {
        val b = Board()
        //символи для гри
        //можна змінювати символи, але не кількість
        val chars = arrayListOf('X', '0')
        //визначаємо гравця, який буде робити перший хід(хрестиками)
        val firstMovePlayer = getRandomPlayer(arrayListOf(p1, p2))
        val secondMovePlayer = if (firstMovePlayer == p1) {
            p2
        } else {
            p1
        }
        println("$firstMovePlayer - грає хрестиками\n$secondMovePlayer- грає ноликами")
        val board = b.takeBoard()
        //кількість зроблених кроків гравцями
        val numberOfMoves = Array(2) { 0 }
        val players = arrayListOf(firstMovePlayer, secondMovePlayer)

        /**
         * Перевірити чи задана клітинка зайнята. Якщо вільна повертає false
         */
        fun checkCell(cell: String): Boolean {
            return board[cell[0].toString().toInt() - 1][cell[1].toString().toInt() - 1] == chars[0].toString()
                    || board[cell[0].toString().toInt() - 1][cell[1].toString().toInt() - 1] == chars[1].toString()
        }

        while (true) {
            //визначити гравця, який робить хід
            val index = (numberOfMoves[0] + numberOfMoves[1]) % 2
            //виводити стан поля після кожного ходу
            b.showBoard()
            println("Гравець ${players[index]}, будь ласка, оберіть та впишіть нижче код клітинки для свого ходу")

            //клітинка, яку обрав гравець
            var cell = makeMove()
            //поки користувач не введе вільну клітинку, питати код клітинки
            while (checkCell(cell)) {
                println("Обране Вами поле зайняте, спробуйте інше")
                cell = makeMove()
            }

            //замінити код клітинки на символ
            b.setCell(cell, chars[index])
            numberOfMoves[index]++

            //перевірка на наявність переможця
            if (hasWinner(board)) {
                val message = if (round == NUMBER_OF_PLAYER - 1) {
                    "фінальну"
                } else if (round in (NUMBER_OF_PLAYER - 3)..(NUMBER_OF_PLAYER - 1)) {
                    "півфінальну"
                } else {
                    ""
                }
                println("Гравець ${players[index]} виграв $message гру за ${numberOfMoves[index]}")
                return players[index]
            }

            //всі поля заповнені, але жоден гравець не зібрав 3 свої символи підряд
            if (numberOfMoves[0] + numberOfMoves[1] == board.size * board[0].size) {
                println("Нічия. Гравці $p1, $p2 мають зіграти повторно")
                return startPlay(p1, p2, round)
            }
        }
    }
    /**
     * Перевірка клітинок на наявність комбінації.
     * Комбінація - 3 символи у горизонтальний/вертикальний ряд або у діагональ
     * @return true якщо комбінацію знайдено
     */
    fun hasWinner(board: Array<Array<String>>): Boolean {
        //перевірити рядки
        for (row in board) {
            if (row[0] == row[1] && row[1] == row[2]) {
                return true
            }
        }

        //перевірити колонки
        for (col in 0..2) {
            if (board[0][col] == board[1][col] && board[1][col] == board[2][col]) {
                return true
            }
        }

        // перевірити діагоналі
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            return true
        }
        if (board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            return true
        }

        return false
    }

    /**
     * Зробити хід. Приймати від гравця код клітинки та перевіряти на відповідність вимогам.
     * @return Код обраної клітинки
     */
    private fun makeMove(): String {
        while (true) {
            val userInput = readln()
            //перевірити довжину коду
            if (userInput.length == 2) {
                //кожна цифра має бути в діапазоні [1;3].
                if (userInput[0].toString().toInt() in 1..3 &&
                    userInput[1].toString().toInt() in 1..3
                ) {
                    //обрана клітинка
                    return "${userInput[0].toString().toInt()}" + "${userInput[1].toString().toInt()}"
                } else {
                    println("Ви ввели не валідний номер клітинки, спробуйте знову")
                }
            } else {
                println("Код має складатися з двох цифр")
            }
        }
    }

    /**
     * Метод приймає масив та повертає рандомне значення з нього
     */
    fun getRandomPlayer(players: ArrayList<String>): String {
        val randomizer = Random
        return players[randomizer.nextInt(0, players.size - 1)]
    }

    fun takeWinner(): String {
        return winner
    }
}