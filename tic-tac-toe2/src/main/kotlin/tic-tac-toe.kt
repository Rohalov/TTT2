/**
 * Кількість гравців
 * Програма не оброблює сітки з непарною кількістю гравців
 * Можливі значення 2, 4, 8, 16, 32, ...
 */
val NUMBER_OF_PLAYER = 2

/**
 * База даних логінів та паролів усіх користувачів
 */
var users = mutableListOf<User>()


/**
 * Реалізує гру
 */
fun main() {
    users = Registration(NUMBER_OF_PLAYER).takeDataBase()
    do {
        println(
            "Почати гру? Якщо так, пропишіть в консолі \"у\" "
        )
        val userInput = readln()
    } while (userInput != "y")
    startGame()
    println("Гра завершена.")
}


/**
 * Почати гру з зареєстрованими користувачами
 */
fun startGame() {
    println(
        "Починаємо гру! Учасники будуть розподілені на групи по 2 людини. " +
                "Спочатку відбудеться півфінал, після чого переможці будуть змагатися у фінальній грі"
    )

    //перемішати масив
    var shuffledPlayers = users.shuffled()
    //кількість гравців у етапі сітки
    var playersInStage = NUMBER_OF_PLAYER
    var indexRound = 0
    while (playersInStage > 1) {
        //гравці, які перемогли у своїй парі
        val winners = mutableListOf<User>()
        var indexPlayer = 0
        var indexStage = 0
        //вивести в консоль пари
        showPairs(shuffledPlayers)
        //обіграш кожної пари етапу
        while (indexStage < playersInStage / 2) {
            val winner = startRound(indexRound, users[indexPlayer], users[indexPlayer + 1])
            indexStage++
            indexRound++
            indexPlayer += 2
            //додати гравця в масив
            winners.add(winner)
        }
        //перетасувати масив переможців
        shuffledPlayers = winners.shuffled()
        //гравців у кожному наступному етапі менше наполовину
        playersInStage /= 2
    }
    println("Переможець турніру: ${shuffledPlayers[0].login}")
}

/**
 * Виводить пари у консоль
 */
fun showPairs(players: List<User>) {
    val size = players.size
    var indexPair = 0
    var indexPlayer = 0
    while (indexPair < size / 2) {
        println("Пара ${indexPair + 1} ")
        println(players[indexPlayer].login)
        println(players[indexPlayer + 1].login)
        indexPlayer += 2
        indexPair++
    }
}

/**
 * Починає раунд. Виконує підтвердження та авторизацію. Після чого гра починається
 * @param p1 Перший гравець
 * @param p2 Другий гравець
 * @return Нік переможця раунду
 */
fun startRound(round: Int, p1: User, p2: User): User {
    confirmRound(round + 1, p1.login, p2.login)
    p1.authorize()
    p2.authorize()
    println("гравці успішно авторизовані, починаємо гру!")
    val game = Game(p1.login, p2.login, round + 1)
    return if (game.takeWinner() == p1.login) {
        p1
    } else {
        p2
    }
}

/**
 * Підтвердити початок раунду. В консоль виводиться повідомлення, після чого приймається ввід від користувача.
 * Повідомлення виводиться доти, доки в консоль не вписано "у"
 */
fun confirmRound(round: Int, p1: String, p2: String) {
    var message = round.toString()

    //окреме повідомлення для фінального раунду
    if (round == NUMBER_OF_PLAYER - 1) {
        message = "фінальний"
    }

    while (true) {
        println(
            "щоб почати $message раунд гравців пари $p1, $p2 , впишіть \"у\" в консолі. " +
                    "Далі Вам необхідно буде підтвердити свою особистість, ввівши пароль"
        )
        val userInput = readln()
        if (userInput == "y") {
            break
        }
    }
}
