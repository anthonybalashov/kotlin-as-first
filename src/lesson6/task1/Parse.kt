@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson6.task1

/**
 * Пример
 *
 * Время представлено строкой вида "11:34:45", содержащей часы, минуты и секунды, разделённые двоеточием.
 * Разобрать эту строку и рассчитать количество секунд, прошедшее с начала дня.
 */
fun timeStrToSeconds(str: String): Int {
    val parts = str.split(":")
    var result = 0
    for (part in parts) {
        val number = part.toInt()
        result = result * 60 + number
    }
    return result
}

/**
 * Пример
 *
 * Дано число n от 0 до 99.
 * Вернуть его же в виде двухсимвольной строки, от "00" до "99"
 */
fun twoDigitStr(n: Int) = if (n in 0..9) "0$n" else "$n"

/**
 * Пример
 *
 * Дано seconds -- время в секундах, прошедшее с начала дня.
 * Вернуть текущее время в виде строки в формате "ЧЧ:ММ:СС".
 */
fun timeSecondsToStr(seconds: Int): String {
    val hour = seconds / 3600
    val minute = (seconds % 3600) / 60
    val second = seconds % 60
    return String.format("%02d:%02d:%02d", hour, minute, second)
}

/**
 * Пример: консольный ввод
 */
fun main() {
    println("Введите время в формате ЧЧ:ММ:СС")
    val line = readLine()
    if (line != null) {
        val seconds = timeStrToSeconds(line)
        if (seconds == -1) {
            println("Введённая строка $line не соответствует формату ЧЧ:ММ:СС")
        } else {
            println("Прошло секунд с начала суток: $seconds")
        }
    } else {
        println("Достигнут <конец файла> в процессе чтения строки. Программа прервана")
    }
}


/**
 * Средняя
 *
 * Дата представлена строкой вида "15 июля 2016".
 * Перевести её в цифровой формат "15.07.2016".
 * День и месяц всегда представлять двумя цифрами, например: 03.04.2011.
 * При неверном формате входной строки вернуть пустую строку.
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30.02.2009) считается неверными
 * входными данными.
 */
val monthList = mapOf(
    "января" to Pair("1", 31),
    "февраля" to Pair("2", 28),
    "марта" to Pair("3", 31),
    "апреля" to Pair("4", 30),
    "мая" to Pair("5", 31),
    "июня" to Pair("6", 30),
    "июля" to Pair("7", 31),
    "августа" to Pair("8", 31),
    "сентября" to Pair("9", 30),
    "октября" to Pair("10", 31),
    "ноября" to Pair("11", 30),
    "декабря" to Pair("12", 31)
)

fun dateStrToDigit(str: String): String {
    val parts = str.split(" ")
    if (parts.size != 3) return ""
    if (!monthList.containsKey(parts[1])) return ""
    val month = (monthList[parts[1]]?.first)?.toInt() ?: 0
    val day: Int
    val year: Int
    try {
        day = parts[0].toInt()
        year = parts[2].toInt()
    } catch (e: NumberFormatException) {
        return ""
    }
    if ((year < 0) || (parts[2].toLong() > Int.MAX_VALUE)) return ""
    val isYearLeap: Boolean = ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0))
    if ((day > monthList[parts[1]]?.second ?: 0) || (day < 0)) {
        if (month != 2 || !isYearLeap || day != 29) return ""
    }
    var string = ""
    if ((day / 10) == 0) {
        string += "0"
    }
    string += "$day."
    if ((month / 10) == 0) {
        string += "0"
    }
    string += "$month."
    string += parts[2]
    return string
}

/**
 * Средняя
 *
 * Дата представлена строкой вида "15.07.2016".
 * Перевести её в строковый формат вида "15 июля 2016".
 * При неверном формате входной строки вернуть пустую строку
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30 февраля 2009) считается неверными
 * входными данными.
 */

val digitalMonthList = monthList.map { it.value.first.toInt() to Pair(it.key, it.value.second) }.toMap()

fun dateDigitToStr(digital: String): String {
    val parts = digital.split(".")
    if (parts.size != 3) return ""
    val day: Int
    val month: Int
    val year: Int

    try {
        day = parts[0].toInt()
        month = parts[1].toInt()
        year = parts[2].toInt()
    } catch (e: NumberFormatException) {
        return ""
    }
    if (!digitalMonthList.containsKey(month)) return ""

    if ((year < 0) || (parts[2].toLong() > Int.MAX_VALUE)) return ""
    val isYearLeap: Boolean = ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0))

    if ((day > digitalMonthList[month]?.second ?: 0) || (day < 0)) {
        if (month != 2 || !isYearLeap || day != 29) return ""
    }
    var string = ""
    string += "$day "
    string += digitalMonthList[month]?.first + " "
    string += "$year"
    return string
}

/**
 * Средняя
 *
 * Номер телефона задан строкой вида "+7 (921) 123-45-67".
 * Префикс (+7) может отсутствовать, код города (в скобках) также может отсутствовать.
 * Может присутствовать неограниченное количество пробелов и чёрточек,
 * например, номер 12 --  34- 5 -- 67 -89 тоже следует считать легальным.
 * Перевести номер в формат без скобок, пробелов и чёрточек (но с +), например,
 * "+79211234567" или "123456789" для приведённых примеров.
 * Все символы в номере, кроме цифр, пробелов и +-(), считать недопустимыми.
 * При неверном формате вернуть пустую строку.
 *
 * PS: Дополнительные примеры работы функции можно посмотреть в соответствующих тестах.
 */

val otherElements: Set<Char> = setOf(
    '(',
    ')',
    '-',
    '+',
    ' '
)
val numericElements: Set<Char> = setOf(
    '0',
    '1',
    '2',
    '3',
    '4',
    '5',
    '6',
    '7',
    '8',
    '9'
)

fun flattenPhoneNumber(phone: String): String {
    var string = ""
    var plusAppearance = false
    var firstBracketAppearance = false
    var secondBracketAppearance = false
    var numberInBracketsAppearance = false
    var anyNumberAppearance = false
    if (phone == "") return ""
    if (phone[0] == '+') {
        string += '+'
    }
    for (letter in phone) {
        if (letter !in numericElements && letter !in otherElements) return ""
        if (letter == '+') {
            if (plusAppearance) return ""
            plusAppearance = true
        }
        if (letter in numericElements) {
            if (firstBracketAppearance) numberInBracketsAppearance = true
            string += letter
            if (!anyNumberAppearance) anyNumberAppearance = true
        }
        if (letter == '(') {
            if (firstBracketAppearance) return ""
            firstBracketAppearance = true
        }
        if (letter == ')') {
            if (secondBracketAppearance) return ""
            if (!(firstBracketAppearance && numberInBracketsAppearance)) return ""
            secondBracketAppearance = true
        }

    }
    if (!anyNumberAppearance) return ""
    return string
}

/**
 * Средняя
 *
 * Результаты спортсмена на соревнованиях в прыжках в длину представлены строкой вида
 * "706 - % 717 % 703".
 * В строке могут присутствовать числа, черточки - и знаки процента %, разделённые пробелами;
 * число соответствует удачному прыжку, - пропущенной попытке, % заступу.
 * Прочитать строку и вернуть максимальное присутствующее в ней число (717 в примере).
 * При нарушении формата входной строки или при отсутствии в ней чисел, вернуть -1.
 */

fun bestLongJump(jumps: String): Int {
    val jumpList = jumps.split(" ")
    var bestJump = -1
    for (jump in jumpList) {
        try {
            val tempJump = jump.toInt()
            if (tempJump > bestJump) bestJump = tempJump
        } catch (e: NumberFormatException) {
            if (!(jump == "-" || jump == "%")) return -1
        }
    }
    return bestJump
}

/**
 * Сложная
 *
 * Результаты спортсмена на соревнованиях в прыжках в высоту представлены строкой вида
 * "220 + 224 %+ 228 %- 230 + 232 %%- 234 %".
 * Здесь + соответствует удачной попытке, % неудачной, - пропущенной.
 * Высота и соответствующие ей попытки разделяются пробелом.
 * Прочитать строку и вернуть максимальную взятую высоту (230 в примере).
 * При нарушении формата входной строки, а также в случае отсутствия удачных попыток,
 * вернуть -1.
 */

val allowedSymbols = setOf('-', '%', '+')

fun bestHighJump(jumps: String): Int {
    val jumpList = jumps.split(" ")
    var bestJump = -1
    for ((index, jump) in jumpList.withIndex()) {
        try {
            val tempJump = jump.toInt()
            if ((index < jumpList.size - 1) && jumpList[index + 1].contains("+")) {
                if (tempJump > bestJump) bestJump = tempJump
            }
        } catch (e: NumberFormatException) {
            for (symbol in jump) {
                if (!allowedSymbols.contains(symbol)) return -1
            }
        }
    }
    return bestJump
}

/**
 * Сложная
 *
 * В строке представлено выражение вида "2 + 31 - 40 + 13",
 * использующее целые положительные числа, плюсы и минусы, разделённые пробелами.
 * Наличие двух знаков подряд "13 + + 10" или двух чисел подряд "1 2" не допускается.
 * Вернуть значение выражения (6 для примера).
 * Про нарушении формата входной строки бросить исключение IllegalArgumentException
 */

fun plusMinus(expression: String): Int {
    val numbersAndSymbols = expression.split(" ")
    var result: Int
    val firstElement: String
    if (numbersAndSymbols.isEmpty()) throw IllegalArgumentException()
    firstElement = numbersAndSymbols[0]
    try {
        if (isItNumber(firstElement)) result = firstElement.toInt()
        else throw IllegalArgumentException()
    } catch (e: NumberFormatException) {
        throw IllegalArgumentException()
    }
    var index = 1
    while (index < numbersAndSymbols.size) {
        val currentSymbol = numbersAndSymbols[index]
        var nextNumber: Int
        if (index < numbersAndSymbols.size - 1) {
            val currentNumber = numbersAndSymbols[index + 1]
            if (isItNumber(currentNumber)) nextNumber = currentNumber.toInt()
            else throw IllegalArgumentException()
        } else throw IllegalArgumentException()
        if (currentSymbol != "+" && currentSymbol != "-") throw IllegalArgumentException()
        else {
            if (currentSymbol == "-") nextNumber *= -1
            result += nextNumber
        }
        index += 2
    }
    return result
}

fun isItNumber(number: String): Boolean {
    if (Regex("""[^0123456789]""").find(number) != null) return false
    return true
}

/**
 * Сложная
 *
 * Строка состоит из набора слов, отделённых друг от друга одним пробелом.
 * Определить, имеются ли в строке повторяющиеся слова, идущие друг за другом.
 * Слова, отличающиеся только регистром, считать совпадающими.
 * Вернуть индекс начала первого повторяющегося слова, или -1, если повторов нет.
 * Пример: "Он пошёл в в школу" => результат 9 (индекс первого 'в')
 */
fun firstDuplicateIndex(str: String): Int {
    if (str.contains(Regex("""\s\s"""))) return -1
    val words = str.split(" ")
    var symbolIndex = 0
    for ((index, word) in words.withIndex()) {
        if (index < words.size - 1 && words[index + 1].toLowerCase() == word.toLowerCase()) return symbolIndex
        symbolIndex += word.length + 1
    }
    return -1
}

/**
 * Сложная
 *
 * Строка содержит названия товаров и цены на них в формате вида
 * "Хлеб 39.9; Молоко 62; Курица 184.0; Конфеты 89.9".
 * То есть, название товара отделено от цены пробелом,
 * а цена отделена от названия следующего товара точкой с запятой и пробелом.
 * Вернуть название самого дорогого товара в списке (в примере это Курица),
 * или пустую строку при нарушении формата строки.
 * Все цены должны быть больше либо равны нуля.
 */
fun mostExpensive(description: String): String {
    if (!description.matches(Regex("""(((\S)+ \d+((\.)\d*)?); )*((\S)+ \d+(\.\d*)?)"""))) return ""
    val products = description.split(Regex("""(;? )"""))
    var theMostExpensiveProduct: Pair<String, Double> = Pair(products[0], products[1].toDouble())
    val productsSize = products.size
    var index = 2
    while (index < productsSize) {
        val currentProductCost = products[index + 1].toDouble()
        if (currentProductCost > theMostExpensiveProduct.second) theMostExpensiveProduct =
            Pair(products[index], currentProductCost)
        index += 2
    }
    return theMostExpensiveProduct.first
}

/**
 * Сложная
 *
 * Перевести число roman, заданное в римской системе счисления,
 * в десятичную систему и вернуть как результат.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: XXIII = 23, XLIV = 44, C = 100
 *
 * Вернуть -1, если roman не является корректным римским числом
 */
val romanList = listOf("M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I")
val decimalEquivalent = listOf(1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1)
val regex = Regex("""M*((CM)?|(DC{0,3})?|(D)?|(CD)?|C{0,3}?)?((XC)?|(LX{0,3})?|(L)?|(XL)?|(X{0,3})?)?((IX)?|(VI{0,3})?|(V)?|(IV)?|(I{0,3})?)?""")
fun fromRoman(roman: String): Int {
    if (roman == "" || (!roman.matches(regex))) {
        return -1
    }
    var workingString = roman
    val rankList = mutableListOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    for ((index, element) in romanList.withIndex()) {
        if ((workingString.length > 1) && (workingString.substring(0, 2)) == element) {
            rankList[index] = 1
            if (workingString.length == 2) break
            else workingString = workingString.substring(2)
        }
        while ((workingString.isNotEmpty()) && workingString[0].toString() == element) {
            rankList[index]++
            if (workingString.length == 1) break
            else workingString = workingString.substring(1)
        }
    }
    var decimalFromRoman = 0
    for (index in 0..12) {
        decimalFromRoman += decimalEquivalent[index] * rankList[index]
    }
    return decimalFromRoman
}

/**
 * Очень сложная
 *
 * Имеется специальное устройство, представляющее собой
 * конвейер из cells ячеек (нумеруются от 0 до cells - 1 слева направо) и датчик, двигающийся над этим конвейером.
 * Строка commands содержит последовательность команд, выполняемых данным устройством, например +>+>+>+>+
 * Каждая команда кодируется одним специальным символом:
 *	> - сдвиг датчика вправо на 1 ячейку;
 *  < - сдвиг датчика влево на 1 ячейку;
 *	+ - увеличение значения в ячейке под датчиком на 1 ед.;
 *	- - уменьшение значения в ячейке под датчиком на 1 ед.;
 *	[ - если значение под датчиком равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей следующей командой ']' (с учётом вложенности);
 *	] - если значение под датчиком не равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей предыдущей командой '[' (с учётом вложенности);
 *      (комбинация [] имитирует цикл)
 *  пробел - пустая команда
 *
 * Изначально все ячейки заполнены значением 0 и датчик стоит на ячейке с номером N/2 (округлять вниз)
 *
 * После выполнения limit команд или всех команд из commands следует прекратить выполнение последовательности команд.
 * Учитываются все команды, в том числе несостоявшиеся переходы ("[" при значении под датчиком не равном 0 и "]" при
 * значении под датчиком равном 0) и пробелы.
 *
 * Вернуть список размера cells, содержащий элементы ячеек устройства после завершения выполнения последовательности.
 * Например, для 10 ячеек и командной строки +>+>+>+>+ результат должен быть 0,0,0,0,0,1,1,1,1,1
 *
 * Все прочие символы следует считать ошибочными и формировать исключение IllegalArgumentException.
 * То же исключение формируется, если у символов [ ] не оказывается пары.
 * Выход за границу конвейера также следует считать ошибкой и формировать исключение IllegalStateException.
 * Считать, что ошибочные символы и непарные скобки являются более приоритетной ошибкой чем выход за границу ленты,
 * то есть если в программе присутствует некорректный символ или непарная скобка, то должно быть выброшено
 * IllegalArgumentException.
 * IllegalArgumentException должен бросаться даже если ошибочная команда не была достигнута в ходе выполнения.
 *
 */
fun computeDeviceCells(cells: Int, commands: String, limit: Int): List<Int> {
    var tempCommands = commands
    while (tempCommands.length > 100) {
        if (!tempCommands.substring(0, 100).matches(Regex("""((>)*(<)*(\+)*(\[)*(\])*( )*(-)*)+"""))) {
            throw IllegalArgumentException()
        }
        tempCommands = tempCommands.substring(100, tempCommands.length)
    }
    if (!tempCommands.matches(Regex("""((>)*(<)*(\+)*(\[)*(\])*( )*(-)*)+"""))) {
        throw IllegalArgumentException()
    }
    if (cells == 0) return listOf()
    val cellsList = mutableListOf<Int>()
    for (i in 0 until cells) cellsList.add(0)
    if (limit == 0 || commands == "") return cellsList
    val offsettingCommandsCount = mutableListOf(0, 0)
    for (command in commands) {
        when (command) {
            '[' -> offsettingCommandsCount[0]++
            ']' -> offsettingCommandsCount[1]++
        }
        if (offsettingCommandsCount[1] > offsettingCommandsCount[0]) throw IllegalArgumentException()
    }
    if (offsettingCommandsCount[0] != offsettingCommandsCount[1]) throw IllegalArgumentException()
    var currentCell = cells / 2
    var nestingLevel = 0
    var index = 0
    val nestingLevelIndexes = mutableMapOf<Int, Int>()
    var implementedCommandsCounter = 0
    while ((index in commands.indices) && (implementedCommandsCounter < limit)) {
        when (commands[index]) {
            '+' -> {
                cellsList[currentCell]++
                index++
            }
            '-' -> {
                cellsList[currentCell]--
                index++
            }
            '<' -> {
                currentCell--
                if (currentCell < 0) throw IllegalStateException()
                index++
            }
            '>' -> {
                currentCell++
                if (currentCell > cells - 1) throw IllegalStateException()
                index++
            }
            '[' -> {
                if (cellsList[currentCell] != 0) {
                    nestingLevel++
                    nestingLevelIndexes[nestingLevel] = index
                    index++
                } else {
                    var tempNestingLevel = 1
                    while (tempNestingLevel != 0) {
                        index++
                        when (commands[index]) {
                            '[' -> tempNestingLevel++
                            ']' -> tempNestingLevel--
                        }
                    }
                    index++
                }
            }
            ']' -> {
                if (cellsList[currentCell] != 0) {
                    index = (nestingLevelIndexes[nestingLevel] ?: 0) + 1
                } else {
                    nestingLevelIndexes.remove(nestingLevel)
                    nestingLevel--
                    index++
                }
            }
            ' ' -> index++
        }
        implementedCommandsCounter++
    }

    return cellsList
}