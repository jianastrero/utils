package dev.jianastrero

import dev.jianastrero.utils.log.LogLevel
import dev.jianastrero.utils.log.LogUtil
import dev.jianastrero.utils.log.log
import dev.jianastrero.utils.log.logDeep
import dev.jianastrero.utils.table.TableTokens
import dev.jianastrero.utils.table.table

data class MyClass(
    val name: String,
    val age: Int,
    val height: Double,
    val isStudent: Boolean,
)

internal fun main() {
    LogUtil.minLogLevel = LogLevel.DEBUG

    val myString = "Hello, World!"
    val myInt = 42
    val myDouble = 3.14
    val myBoolean = true
    val array = arrayOf(1, 2, 3, 4, 5)
    val myList = listOf(1, 2, 3, 4, 5)
    val myMap = mapOf("key1" to "value1", "key2" to "value2")
    val myThrowable = Throwable("This is a throwable")
    val myClass = MyClass(myString, myInt, myDouble, myBoolean)

    myString.log()
    myInt.log()
    myDouble.log()
    myBoolean.log()
    array.log()
    myList.log()
    myMap.log()
    myThrowable.log()
    myClass.log()

    myString.logDeep("myString")
    myInt.logDeep("myInt")
    myDouble.logDeep("myDouble")
    myBoolean.logDeep("myBoolean")
    array.logDeep("array")
    myList.logDeep("myList")
    myMap.logDeep("myMap")
    myThrowable.logDeep("myThrowable")
    myClass.logDeep("myClass")
}
