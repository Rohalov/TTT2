class Registration(dataBaseSize : Int) {
    private val dataBase = mutableListOf<User>()

    init {
        var i = 0
        while (i < dataBaseSize ){
            println("Гравець ${i + 1} зареєструйтесь, будь ласка.")
            var login = createLogin()
            //перевірка на унікальність логіну
            while (checkLogin(login)) {
                println("!Данний логін вже використовується")
                login = createLogin()
            }
            println("Логін успішно встановлено. Ваш логін $login")
            val password = createPassword()
            println("Пароль успішно встановлено. Ваш пароль $password")
            //додати до масиву
            val user = User(login, password)
            dataBase.add(user)
            i++
        }
    }

    /**
     * Перевірка логіну на наявність у масиві users
     */
    private fun checkLogin(login: String): Boolean {
        //перевірка всіх ключів у мапі
        for (user in users) {
            if (user.login == login) {
                //збіг знайдено
                return true
            }
        }
        //збігів не знайдено
        return false
    }

    /**
     * Запросити логін у користувача та перевірити на відповідність вимогам
     * @return Логін користувача
     */
    private fun createLogin(): String {
        while (true) {
            print("Введіть логін: ")
            //приймаємо ввід від користувача
            val userInput = readln()

            //перевіряємо наявність літер у логіні
            if (!userInput.any { it.isLetter() }) {
                println("!Логін має містити хоча б одну букву")
                //перевіряємо наявність цифр у логіні
            } else if (!userInput.any { it.isDigit() }) {
                println("!Логін має містити хоча б одну цифру")
            } else {
                return userInput
            }
        }
    }

    /**
     * Запросити пароль у користувача та перевірити на відповідність вимогам
     * @return Пароль користувача
     */
    private fun createPassword(): String {
        while (true) {
            print("Введіть пароль: ")
            //приймаємо ввід від користувача
            val userInput = readln()

            //перевірка довжини пароля
            if (userInput.length < 8) {
                println("!Пароль має бути довшим за 8 символів")
                //перевіряємо наявність літер у паролі
            } else if (!userInput.any { it.isLetter() }) {
                println("!Пароль має містити хоча б одну букву")
                //перевіряємо наявність цифр у паролі
            } else if (!userInput.any { it.isDigit() }) {
                println("!Пароль має містити хоча б одну цифру")
            } else {
                return userInput
            }
        }
    }

    fun takeDataBase(): MutableList<User> {
        return dataBase
    }
}