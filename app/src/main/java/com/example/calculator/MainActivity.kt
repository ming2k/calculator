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
        // check `number operator number operator ... number` structure
        // feature: even -> number; odd -> operator; last -> number
        if (list.size == 0 ) {
            resultTextView.text = getString(R.string.error_info1)
            return
        }
        if (list.last() !is Number) {
            resultTextView.text = getString(R.string.error_info2)
        }
        for (i in list.indices) {
            if ( (i % 2 == 0 && list[i] !is Number) || (i % 2 == 1 && list[i] !is String)) {
                    resultTextView.text = getString(R.string.error_info3)
                    return
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
            when (list[1]) {
                "+" -> {
                    result = (list[0] as Number).toDouble() + (list[2] as Number).toDouble()
                    list[0] = result
                    list.removeAt(1)
                    list.removeAt(1)
                }
                "-" -> {
                    result = (list[0] as Number).toDouble() - (list[2] as Number).toDouble()
                    list[0] = result
                    list.removeAt(1)
                    list.removeAt(1)
                }
            }
        }

        currentInputSB.clear()

        // Format decimal :)
        // add thousand separator and omit decimal 0
        val dec = DecimalFormat("#,###.###")
        resultTextView.text = dec.format(result)

        /**
         * please note that:
         * Under normal conditions, the `isNumberStarted` is false,
         * It's good after clicking equal button,
         * because at last the `list` only have only one element,
         * if you click operator button, the element can continue to use,
         * if you click number button, the result will be covered by new one.
         * So it can be better to use.
         */

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
        val dec = DecimalFormat("#,###.###")
        for(i in list.indices) {
            if (i%2 == 0 && list[i] is Number){
                val formatNumber = dec.format(list[i] as Number)
                str.append("$formatNumber ")
            } else {
                str.append("${list[i]} ")
            }
        }
        processTextView.text = str.toString()
    }

    fun toBeDeveloped(view: View) {
        val author = getString(R.string.to_be_developed)
        resultTextView.text = author
        Log.v("myTag", "$list")
    }
}