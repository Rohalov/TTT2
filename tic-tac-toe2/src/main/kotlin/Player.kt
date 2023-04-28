class User(val login: String, private val password: String) {

    /**
     * Провести авторизацію гравця. Гравець має ввести свій пароль в консоль.
     * Перевірити введене значення на відповідність тому значенню, яке було введене при реєстрації
     */
    fun authorize() {
        println("Користувач $login, введіть пароль, будь ласка")
        var userInput = readln()
        while (userInput != password) {
            println("пароль вказаний невірно")
            userInput = readln()
        }
    }
}