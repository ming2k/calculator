package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    private val currentInputSB = StringBuilder()
    // Create the list to store numbers and operators.
    private val list = mutableListOf<Any>()
    // `isNumberStarted` is true create a number element of list,
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
            // Can also be written like this:
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
        // preset error info
        val errorInfo = "ERROR"
        // check `number operator number operator ... number` structure
        // feature: even -> number; odd -> operator; last -> number
        if (list.size == 0) {
            resultTextView.text = errorInfo
            return
        }
        if (list.last() !is Number) {
            resultTextView.text = errorInfo
            return
        }
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


        var result = 0.0

        // Handling multiplication and division
        // Store multiplication and division symbol indices in list
        val mulAndDivIndicesList = mutableListOf<Int>()
        for ( (i,e) in this.list.withIndex() ) {
            if ( e == "×" || e == "÷") {
                mulAndDivIndicesList.add(i)
            }
        }

        for ( (i, index) in mulAndDivIndicesList.withIndex() ) {
            // Turn 3 elements to a element after every operation,
            // so indices need to minus 2.
            val relativeIndex = index - i * 2
            when (list[relativeIndex]) {
                "×" -> {
                    result = (list[relativeIndex-1] as Number).toDouble() * (list[relativeIndex+1] as Number).toDouble()
                    // Please pay attention to index changes.
                    list[relativeIndex-1] = result
                    list.removeAt(relativeIndex)
                    list.removeAt(relativeIndex)
                }
                "÷" -> {
                    result = (list[relativeIndex-1] as Number).toDouble() / (list[relativeIndex+1] as Number).toDouble()
                    list[relativeIndex-1] = result
                    list.removeAt(relativeIndex)
                    list.removeAt(relativeIndex)
                }
            }
        }

        // Handling addition and subtraction
        for ( i in 1 until list.size step 2 ) {
            when (list[i]) {
                "+" -> {
                    result = (list[i-1] as Number).toDouble() + (list[i+1] as Number).toDouble()
                    list[i-1] = result
                    list.removeAt(i)
                    list.removeAt(i)
                }
                "-" -> {
                    result = (list[i-1] as Number).toDouble() - (list[i+1] as Number).toDouble()
                    list[i-1] = result
                    list.removeAt(i)
                    list.removeAt(i)
                }
            }
        }

        // Format decimal :)
        val dec = DecimalFormat("#,###.#")
        resultTextView.text = dec.format(result)
    }

    fun backButtonClick(view: View) {
        // determine to withdraw the number or the operator
        if (list.size == 0){
            return
        }
        list.removeLast()
        if (list.size > 0 && list.last() is Number) {
            currentInputSB.append(list.last())
            isNumberStarted = false
        } else {
            isNumberStarted = true
        }
        showProcess()
    }

    fun clearButtonClick(view: View) {
        // restore the process and result text view content
        processTextView.text = ""
        resultTextView.text = "0"
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

    fun toBeDeveloped(view: View) {
        val author = "Author: Liming, to be developed"
        resultTextView.text = author
    }
}