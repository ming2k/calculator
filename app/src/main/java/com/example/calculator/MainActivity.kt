package com.example.calculator

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val currentInputSB = StringBuilder()
    // Create the list to store numbers and operators.
    private val list = mutableListOf<Any>()
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

        if (isNumberStarted) {
            currentInputSB.clear()
            currentInputSB.append(tv.text)
            list.add(tv.text.toString().toInt())
            isNumberStarted = false
        } else {
            currentInputSB.append(tv.text)
            //numbersList.removeLast()
            //numbersList.add(currentInputSB.toString().toInt())
            list[list.size - 1] = currentInputSB.toString().toInt()
        }
        showProcess()
    }

    fun operatorButtonClick(view: View) {
        val tv = view as TextView
        currentInputSB.clear()
        list.add(tv.text.toString())
        isNumberStarted = true
        showProcess()
    }

    fun equalButtonClick(view: View) {
        // Grammar check
        val errorInfo = "ERROR"
        for (i in list.indices) {
            if (i % 2 == 0) {
                if (list[i] !is Number) {
                    resultTextView.text = errorInfo
                    return
                }
            } else {
                if (list[i] !is String) {
                    resultTextView.text = errorInfo
                    return
                }
            }
        }

        val newList = list
        var result = 0.0f

        // Handling multiplication and division
        // Store multiplication and division symbol indices in list
        val mulAndDivIndicesList = mutableListOf<Int>()
        for ( (i,e) in list.withIndex() ) {
            if ( e == "×" || e == "÷") {
                mulAndDivIndicesList.add(i)
            }
        }

        for ( (i, index) in mulAndDivIndicesList.withIndex() ) {
            val relativeIndex = index - i * 2
            when (newList[relativeIndex]) {
                "×" -> {
                    result = (newList[relativeIndex-1] as Number).toFloat() * (newList[relativeIndex+1] as Number).toFloat()
                    newList[relativeIndex-1] = result
                    newList.removeAt(relativeIndex)
                    newList.removeAt(relativeIndex)
                }
                "÷" -> {
                    result = (newList[relativeIndex-1] as Number).toFloat() / (newList[relativeIndex+1] as Number).toFloat()
                    newList[relativeIndex-1] = result
                    newList.removeAt(relativeIndex)
                    newList.removeAt(relativeIndex)
                }
            }
        }

        // Handling addition and subtraction
        val addAndSubIndicesList = mutableListOf<Int>()
        for ( (i,e) in list.withIndex() ) {
            if ( e == "+" || e == "-") {
                addAndSubIndicesList.add(i)
            }
        }

        for ( (i, index) in addAndSubIndicesList.withIndex() ) {
            val relativeIndex = index - i * 2
            when (newList[relativeIndex]) {
                "+" -> {
                    result = (newList[relativeIndex-1] as Number).toFloat() + (newList[relativeIndex+1] as Number).toFloat()
                    newList[relativeIndex-1] = result
                    newList.removeAt(relativeIndex)
                    newList.removeAt(relativeIndex)
                }
                "-" -> {
                    result = (newList[relativeIndex-1] as Number).toFloat() - (newList[relativeIndex+1] as Number).toFloat()
                    newList[relativeIndex-1] = result
                    newList.removeAt(relativeIndex)
                    newList.removeAt(relativeIndex)
                }
            }
        }

        resultTextView.text = result.toString()
    }

    fun backButtonClick(view: View) {
        // determine to withdraw the number or the operator
        currentInputSB.append(list.last())
        if (list.size == 0){
            return
        }
        list.removeLast()
        if (list.last() is Number) {
            currentInputSB.append(list.last())
            isNumberStarted = false
        }
        showProcess()
    }

    fun clearButtonClick(view: View) {
        // clear the process text view  content
        processTextView.text = ""
        // clear the list
        currentInputSB.clear()
        list.clear()
        isNumberStarted = true
    }

    // Concatenate numbers and operators, and display them in `processTextView`
    private fun showProcess() {
        val str = StringBuilder()
        for(e in list) {
            str.append("$e ")
        }
        processTextView.text = str.toString()
    }

    fun debugLog(view: View) {
        Log.v("myTag", "$list")
        Log.v("myTag", "$isNumberStarted")
    }
}