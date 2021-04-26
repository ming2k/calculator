package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val currentInputSB = StringBuilder()
    // Note this your number must less than 2^32 because of Int
    private val numbersList = mutableListOf<Int>()
    private val operatorsList = mutableListOf<String>()
    // Clear the string builder if the number input is finished,
    // otherwise update the number.
    private var isNumberStarted = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun numberButtonClick(view: View) {
        // convert Text to TextView
        val tv = view as TextView
        currentInputSB.append(tv.text)

        if (isNumberStarted) {
            numbersList.add(tv.text.toString().toInt())
            isNumberStarted = false
        } else {
            numbersList[numbersList.size - 1] = currentInputSB.toString().toInt()
            //numbersList.removeLast()
            //numbersList.add(currentInputSB.toString().toInt())
        }
        //Log.v("myTag", "$numbersList")
        showProcess()
    }
    fun operatorButtonClick(view: View) {
        val tv = view as TextView
        operatorsList.add(tv.text.toString())
        isNumberStarted = true
        currentInputSB.clear()
        //Log.v("myTag", "$operatorsList")
        showProcess()
    }

    fun equalButtonClick(view: View) {
        Log.v("button-test","equal")
    }

    fun backButtonClick(view: View) {
        // determine to withdraw the number or the operator
        if (numbersList.size == 0){
            return
        }
        if(numbersList.size > operatorsList.size) {
            // withdraw the last number of numbersList
            numbersList.removeLast()
            isNumberStarted = true
            currentInputSB.clear()
        } else {
            operatorsList.removeLast()
            isNumberStarted = false
            currentInputSB.append(numbersList.last())
        }
        showProcess()
    }

    fun clearButtonClick(view: View) {
        // clear the process text view  content
        processTextView.text = ""
        // clear the string builder
        currentInputSB.clear()
        numbersList.clear()
        operatorsList.clear()
        isNumberStarted = true
        Log.v("myTag", "$numbersList")
        Log.v("myTag", "$operatorsList")
    }

    // Concatenate numbers and operators, and display them in `processTextView`
    private fun showProcess() {
        var str = StringBuilder()
        for((i, number) in numbersList.withIndex()) {
            str.append(number)
            // determine whether the operator exists
            if (operatorsList.size > i) {
                str.append(" ${operatorsList[i]} ")
            }
        }
        processTextView.text = str.toString()
    }

    // Complete logic operation
    fun calculate1() {
        if (numbersList.size == 0 ) {
            return
        }
        var i = 0
        var param1 = numbersList[0]
        var param2 = 0
        var result = 0.0f
        while (true) {
            var operator = operatorsList[i]
            if (operator == "×" || operator == "÷") {
                param2 = numbersList[i+1]
                when(operator) {
                    "×" -> { result = (param1 * param2) as Float }
                    "÷" -> { result = (param1 / param2) as Float }
                }
            }
        }
    }

    fun debugLog(view: View) {
        Log.v("mytag", "$numbersList")
        Log.v("mytag", "$operatorsList")
        Log.v("mytag", "$isNumberStarted")
    }
}