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
        println(list)
    }
    @Test
    fun divideByZero() {
        val a = 1.0
        val b = 0.0
        val c = a/b
        println( c )
    }
}