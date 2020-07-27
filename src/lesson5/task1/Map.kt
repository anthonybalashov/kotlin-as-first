@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson5.task1


/**
 * Пример
 *
 * Для заданного списка покупок `shoppingList` посчитать его общую стоимость
 * на основе цен из `costs`. В случае неизвестной цены считать, что товар
 * игнорируется.
 */
fun shoppingListCost(
    shoppingList: List<String>,
    costs: Map<String, Double>
): Double {
    var totalCost = 0.0

    for (item in shoppingList) {
        val itemCost = costs[item]
        if (itemCost != null) {
            totalCost += itemCost
        }
    }

    return totalCost
}

/**
 * Пример
 *
 * Для набора "имя"-"номер телефона" `phoneBook` оставить только такие пары,
 * для которых телефон начинается с заданного кода страны `countryCode`
 */
fun filterByCountryCode(
    phoneBook: MutableMap<String, String>,
    countryCode: String
) {
    val namesToRemove = mutableListOf<String>()

    for ((name, phone) in phoneBook) {
        if (!phone.startsWith(countryCode)) {
            namesToRemove.add(name)
        }
    }

    for (name in namesToRemove) {
        phoneBook.remove(name)
    }
}

/**
 * Пример
 *
 * Для заданного текста `text` убрать заданные слова-паразиты `fillerWords`
 * и вернуть отфильтрованный текст
 */
fun removeFillerWords(
    text: List<String>,
    vararg fillerWords: String
): List<String> {
    val fillerWordSet = setOf(*fillerWords)

    val res = mutableListOf<String>()
    for (word in text) {
        if (word !in fillerWordSet) {
            res += word
        }
    }
    return res
}

/**
 * Пример
 *
 * Для заданного текста `text` построить множество встречающихся в нем слов
 */
fun buildWordSet(text: List<String>): MutableSet<String> {
    val res = mutableSetOf<String>()
    for (word in text) res.add(word)
    return res
}


/**
 * Простая
 *
 * По заданному ассоциативному массиву "студент"-"оценка за экзамен" построить
 * обратный массив "оценка за экзамен"-"список студентов с этой оценкой".
 *
 * Например:
 *   buildGrades(mapOf("Марат" to 3, "Семён" to 5, "Михаил" to 5))
 *     -> mapOf(5 to listOf("Семён", "Михаил"), 3 to listOf("Марат"))
 */
fun buildGrades(grades: Map<String, Int>): Map<Int, List<String>> {
    val gradeMap = mutableMapOf<Int, List<String>>()
    for ((surname, grade) in grades) {
        if (grade in gradeMap) {
            val tempList: List<String> = gradeMap[grade] ?: listOf()
            gradeMap[grade] = tempList + listOf(surname)
        } else {
            gradeMap[grade] = listOf(surname)
        }
    }
    return gradeMap
}

/**
 * Простая
 *
 * Определить, входит ли ассоциативный массив a в ассоциативный массив b;
 * это выполняется, если все ключи из a содержатся в b с такими же значениями.
 *
 * Например:
 *   containsIn(mapOf("a" to "z"), mapOf("a" to "z", "b" to "sweet")) -> true
 *   containsIn(mapOf("a" to "z"), mapOf("a" to "zee", "b" to "sweet")) -> false
 */
fun containsIn(a: Map<String, String>, b: Map<String, String>): Boolean {
    for ((key) in a) {
        if (!(b.containsKey(key) && a[key] == b[key])) return false
    }
    return true
}

/**
 * Простая
 *
 * Удалить из изменяемого ассоциативного массива все записи,
 * которые встречаются в заданном ассоциативном массиве.
 * Записи считать одинаковыми, если и ключи, и значения совпадают.
 *
 * ВАЖНО: необходимо изменить переданный в качестве аргумента
 *        изменяемый ассоциативный массив
 *
 * Например:
 *   subtractOf(a = mutableMapOf("a" to "z"), mapOf("a" to "z"))
 *     -> a changes to mutableMapOf() aka becomes empty
 */
fun subtractOf(a: MutableMap<String, String>, b: Map<String, String>) {
    for ((key) in b) {
        if (a.containsKey(key) && a[key] == b[key]) a.remove(key)
    }
}

/**
 * Простая
 *
 * Для двух списков людей найти людей, встречающихся в обоих списках.
 * В выходном списке не должно быть повторяюихся элементов,
 * т. е. whoAreInBoth(listOf("Марат", "Семён, "Марат"), listOf("Марат", "Марат")) == listOf("Марат")
 */
fun whoAreInBoth(a: List<String>, b: List<String>): List<String> {
    val map = mutableMapOf<String, Boolean>()
    for (name in a) {
        if (!map.containsKey(name)) map[name] = false
    }
    for (name in b) {
        if (map.containsKey(name)) map[name] = true
    }
    val list = mutableListOf<String>()
    for ((key, value) in map) {
        if (value) list += key
    }
    return list
}

/**
 * Средняя
 *
 * Объединить два ассоциативных массива `mapA` и `mapB` с парами
 * "имя"-"номер телефона" в итоговый ассоциативный массив, склеивая
 * значения для повторяющихся ключей через запятую.
 * В случае повторяющихся *ключей* значение из mapA должно быть
 * перед значением из mapB.
 *
 * Повторяющиеся *значения* следует добавлять только один раз.
 *
 * Например:
 *   mergePhoneBooks(
 *     mapOf("Emergency" to "112", "Police" to "02"),
 *     mapOf("Emergency" to "911", "Police" to "02")
 *   ) -> mapOf("Emergency" to "112, 911", "Police" to "02")
 */
fun mergePhoneBooks(mapA: Map<String, String>, mapB: Map<String, String>): Map<String, String> {
    val map = mapB.toMutableMap()
    map.putAll(mapA)
    for ((key, value) in mapA) {
        if (mapB.containsKey(key) && value != mapB[key]) {
            var tempStr = map[key].toString()
            tempStr += ", "
            tempStr += mapB[key]
            map[key] = tempStr
        }
    }
    return map
}

/**
 * Средняя
 *
 * Для заданного списка пар "акция"-"стоимость" вернуть ассоциативный массив,
 * содержащий для каждой акции ее усредненную стоимость.
 *
 * Например:
 *   averageStockPrice(listOf("MSFT" to 100.0, "MSFT" to 200.0, "NFLX" to 40.0))
 *     -> mapOf("MSFT" to 150.0, "NFLX" to 40.0)
 */
fun averageStockPrice(stockPrices: List<Pair<String, Double>>): Map<String, Double> {
    val averageStockPrice = mutableMapOf<String, Double>()
    val numberOfSummations = mutableMapOf<String, Int>()
    for (index in 0 until stockPrices.size) {
        if (averageStockPrice.containsKey(stockPrices[index].first)) {
            val tempCost = (averageStockPrice[stockPrices[index].first] ?: 0.0) + stockPrices[index].second
            averageStockPrice[stockPrices[index].first] = tempCost
            numberOfSummations[stockPrices[index].first] = (numberOfSummations[stockPrices[index].first] ?: 0) + 1
        } else {
            averageStockPrice[stockPrices[index].first] = stockPrices[index].second
            numberOfSummations[stockPrices[index].first] = 1
        }
    }
    for ((key) in averageStockPrice) {
        averageStockPrice[key] = (averageStockPrice[key] ?: 0.0) / (numberOfSummations[key] ?: 1)
    }
    return averageStockPrice
}

/**
 * Средняя
 *
 * Входными данными является ассоциативный массив
 * "название товара"-"пара (тип товара, цена товара)"
 * и тип интересующего нас товара.
 * Необходимо вернуть название товара заданного типа с минимальной стоимостью
 * или null в случае, если товаров такого типа нет.
 *
 * Например:
 *   findCheapestStuff(
 *     mapOf("Мария" to ("печенье" to 20.0), "Орео" to ("печенье" to 100.0)),
 *     "печенье"
 *   ) -> "Мария"
 */
fun findCheapestStuff(stuff: Map<String, Pair<String, Double>>, kind: String): String? {
    var stuffName: String? = null
    var stuffCost = -1.0
    for ((name, typeAndCost) in stuff) {
        val (type, cost) = typeAndCost
        if (/*typeAndCost.first*/ type == kind) {
            if (stuffName == null) {
                stuffName = name
                stuffCost = cost
            } else {
                if ((stuffCost) > cost) {
                    stuffName = name
                    stuffCost = cost
                }
            }
        }
    }
    return stuffName
}

/**
 * Средняя
 *
 * Для заданного набора символов определить, можно ли составить из него
 * указанное слово (регистр символов игнорируется)
 *
 * Например:
 *   canBuildFrom(listOf('a', 'b', 'o'), "baobab") -> true
 */
fun canBuildFrom(chars: List<Char>, word: String): Boolean {
    val setOfCharacters = chars.toSet()
    if (word == "") return true
    for (character in word) {
        if ((character.toLowerCase() !in setOfCharacters) && (character.toUpperCase() !in setOfCharacters)) {
            return false
        }
    }
    return true
}

/**
 * Средняя
 *
 * Найти в заданном списке повторяющиеся элементы и вернуть
 * ассоциативный массив с информацией о числе повторений
 * для каждого повторяющегося элемента.
 * Если элемент встречается только один раз, включать его в результат
 * не следует.
 *
 * Например:
 *   extractRepeats(listOf("a", "b", "a")) -> mapOf("a" to 2)
 */
fun extractRepeats(list: List<String>): Map<String, Int> {
    val mapOfRepeats = mutableMapOf<String, Int>()
    val setOfCheckedElements = mutableSetOf<String>()
    for (element in list) {
        if (setOfCheckedElements.contains(element)) {
            if (element in mapOfRepeats) mapOfRepeats[element] = (mapOfRepeats[element] ?: 0) + 1
            else mapOfRepeats[element] = 2
        } else {
            setOfCheckedElements.add(element)
        }
    }
    return mapOfRepeats
}

/**
 * Средняя
 *
 * Для заданного списка слов определить, содержит ли он анаграммы
 * (два слова являются анаграммами, если одно можно составить из второго)
 *
 * Например:
 *   hasAnagrams(listOf("тор", "свет", "рот")) -> true
 */
fun hasAnagrams(words: List<String>): Boolean {
    val wordsSet: MutableSet<Set<Char>> = mutableSetOf()
    for (element in words) {
        val tempSet = element.toSet()
        if (wordsSet.contains(tempSet)) return true
        wordsSet.add(tempSet)
    }
    for (element in wordsSet) {
        if (element in wordsSet.minusElement(element)) return true
    }

    return false
}

/**
 * Сложная
 *
 * Для заданного ассоциативного массива знакомых через одно рукопожатие `friends`
 * необходимо построить его максимальное расширение по рукопожатиям, то есть,
 * для каждого человека найти всех людей, с которыми он знаком через любое
 * количество рукопожатий.
 * Считать, что все имена людей являются уникальными, а также что рукопожатия
 * являются направленными, то есть, если Марат знает Свету, то это не означает,
 * что Света знает Марата.
 *
 * Например:
 *   propagateHandshakes(
 *     mapOf(
 *       "Marat" to setOf("Mikhail", "Sveta"),
 *       "Sveta" to setOf("Marat"),
 *       "Mikhail" to setOf("Sveta")
 *     )
 *   ) -> mapOf(
 *          "Marat" to setOf("Mikhail", "Sveta"),
 *          "Sveta" to setOf("Marat", "Mikhail"),
 *          "Mikhail" to setOf("Sveta", "Marat")
 *        )
 */
fun propagateHandshakes(friends: Map<String, Set<String>>): Map<String, Set<String>> {
    val allAcquaintances = mutableMapOf<String, MutableSet<String>>()
    for ((name, listOfFriends) in friends) {
        allAcquaintances[name] = listOfFriends.toMutableSet()
        val tempUnmodifiedFriendsSet: MutableSet<String> = mutableSetOf()
        tempUnmodifiedFriendsSet.addAll(allAcquaintances[name] ?: setOf())
        for (friendsName in listOfFriends) {
            allAcquaintances[name]?.addAll(friends[friendsName] ?: setOf())
            if (friendsName !in allAcquaintances) allAcquaintances[friendsName] = mutableSetOf()
        }
        do {
            val differenceInSets: Set<String> =
                (allAcquaintances[name] ?: setOf<String>()) - (tempUnmodifiedFriendsSet)
            tempUnmodifiedFriendsSet.addAll(allAcquaintances[name] ?: setOf())
            for (friendsNameTwo in differenceInSets) {
                if (friends[friendsNameTwo] != null) allAcquaintances[name]?.addAll(friends[friendsNameTwo] ?: setOf())
            }
        } while (allAcquaintances[name] != tempUnmodifiedFriendsSet)
        allAcquaintances[name]?.remove(name)
    }
    return allAcquaintances
}

/**
 * Сложная
 *
 * Для заданного списка неотрицательных чисел и числа определить,
 * есть ли в списке пара чисел таких, что их сумма равна заданному числу.
 * Если да, верните их индексы в виде Pair<Int, Int>;
 * если нет, верните пару Pair(-1, -1).
 *
 * Индексы в результате должны следовать в порядке (меньший, больший).
 *
 * Постарайтесь сделать ваше решение как можно более эффективным,
 * используя то, что вы узнали в данном уроке.
 *
 * Например:
 *   findSumOfTwo(listOf(1, 2, 3), 4) -> Pair(0, 2)
 *   findSumOfTwo(listOf(1, 2, 3), 6) -> Pair(-1, -1)
 */
fun findSumOfTwo(list: List<Int>, number: Int): Pair<Int, Int> {
    val set: Set<Int> = list.toSet()
    val sortedList = list.sorted()
    for ((index, secondNumber) in sortedList.withIndex()) {
        val diff = number - secondNumber
        if (set.contains(diff)) {
            if (diff == secondNumber) {
                if ((((index - 1) >= 0) && sortedList[index - 1] == number) ||
                    (((index + 1) <= (sortedList.size - 1)) && sortedList[index + 1] == number)
                ) {
                    return Pair(list.indexOf(diff), list.lastIndexOf(diff))
                }
            } else {
                val secondNumberIndex = list.indexOf(secondNumber)
                val diffIndex = list.indexOf(diff)
                return if (secondNumberIndex > diffIndex) Pair(diffIndex, secondNumberIndex)
                else Pair(secondNumberIndex, diffIndex)
            }
        }
    }
    return Pair(-1, -1)
}

/**
 * Очень сложная
 *
 * Входными данными является ассоциативный массив
 * "название сокровища"-"пара (вес сокровища, цена сокровища)"
 * и вместимость вашего рюкзака.
 * Необходимо вернуть множество сокровищ с максимальной суммарной стоимостью,
 * которые вы можете унести в рюкзаке.
 *
 * Перед решением этой задачи лучше прочитать статью Википедии "Динамическое программирование".
 *
 * Например:
 *   bagPacking(
 *     mapOf("Кубок" to (500 to 2000), "Слиток" to (1000 to 5000)),
 *     850
 *   ) -> setOf("Кубок")
 *   bagPacking(
 *     mapOf("Кубок" to (500 to 2000), "Слиток" to (1000 to 5000)),
 *     450
 *   ) -> emptySet()
 */
fun bagPacking(treasures: Map<String, Pair<Int, Int>>, capacity: Int): Set<String> {
    val allPreviousBags: Array<Array<Int>> = Array(treasures.size + 1) { Array(capacity + 1) { 0 } }
    var nameIndex = 1
    for (j in 0..capacity) allPreviousBags[0][j] = 0
    for (j in 0..treasures.size) allPreviousBags[j][0] = 0

    for ((name, values) in treasures) {
        for (tempCapacity in 0..capacity) {
            if (tempCapacity < values.first) {
                allPreviousBags[nameIndex][tempCapacity] = allPreviousBags[nameIndex - 1][tempCapacity]
            } else {
                allPreviousBags[nameIndex][tempCapacity] = maxOf(
                    allPreviousBags[nameIndex - 1][tempCapacity],
                    (allPreviousBags[nameIndex - 1][tempCapacity - values.first] + values.second)
                )
            }
        }
        nameIndex++
    }
    val listOfTreasures = treasures.flatMap { listOf(it.key) }
    listOfTreasures.reversed()

    val setOfTakenTreasures = mutableSetOf<String>()
    var currentCapacity = capacity

    for (i in treasures.size downTo 1) {
        if (allPreviousBags[i][currentCapacity] != allPreviousBags[i - 1][currentCapacity]) {
            setOfTakenTreasures.add(listOfTreasures[i - 1])
            currentCapacity -= treasures[listOfTreasures[i - 1]]?.first ?: 0
        }
    }
    return setOfTakenTreasures
}