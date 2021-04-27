package com.example.calculator

import org.junit.Test

import org.junit.Assert.*
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun listOfObject() {
        val list = mutableListOf<Any>()
        list.add("hello")
        list.add(100)
        list.add(1.11f)
        //list[1] = list[2]
        println(list[1] as Int)
        //println(list[1] as Int + (list[2] as Float).toInt())
    }
    @Test
    fun toIntOfList() {
        val list = mutableListOf<Number>()
        list.add(111)
        list[0].toInt()
    }
}