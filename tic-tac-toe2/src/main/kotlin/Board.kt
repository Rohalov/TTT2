class Board {
    private var board = createBoard()

    fun takeBoard(): Array<Array<String>> {return board}

    /**
     * Створити поле і заповнити його значеннями, які відповідають коду клітинки
     * @return двомірний масив з кодами клітинок
     */
    private fun createBoard(): Array<Array<String>> {
        val board = Array(3) { Array(3) { "" } }
        for (row in board.indices) {
            for (col in board[row].indices) {
                board[row][col] = "${row + 1}" + "${col + 1}"
            }
        }
        return  board
    }

    /**
     * Вивести поточне значення поля.
     */
    fun showBoard() {
        for (row in this.board.indices) {
            for (col in this.board[row].indices) {
                print("|")
                print(" ${this.board[row][col]} ")
            }
            println("|")
            println("----------------")
        }
    }

    fun setCell(cell: String, symbol: Char) {
        board[cell[0].toString().toInt() - 1][cell[1].toString().toInt() - 1] = symbol.toString()
    }
}