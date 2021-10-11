package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import net.objecthunter.exp4j.ExpressionBuilder
import android.util.Log
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*val expression = ExpressionBuilder("4!").build()
        val result = expression.evaluate()
        val longResult = result.toLong()
        if(result == longResult.toDouble())
            tvResult.text = longResult.toString()
        else
            tvResult.text = result.toString()*/
    }

    fun onButtonClick(view: View?) {
        when(view?.id) {
            //Numbers
            R.id.tvOne -> appendOnExpression("1", true)
            R.id.tvTwo -> appendOnExpression("2", true)
            R.id.tvThree -> appendOnExpression("3", true)
            R.id.tvFour -> appendOnExpression("4", true)
            R.id.tvFive -> appendOnExpression("5", true)
            R.id.tvSix -> appendOnExpression("6", true)
            R.id.tvSeven -> appendOnExpression("7", true)
            R.id.tvEight -> appendOnExpression("8", true)
            R.id.tvNine -> appendOnExpression("9", true)
            R.id.tvZero -> appendOnExpression("0", true)
            R.id.tvDot -> appendOnExpression(".", true)

            //Operators
            R.id.tvPlus -> appendOnExpression("+", false)
            R.id.tvMinus -> appendOnExpression("-", false)
            R.id.tvMul -> appendOnExpression("*", false)
            R.id.tvDivide -> appendOnExpression("/", false)
            R.id.tvOpen -> appendOnExpression("(", false)
            R.id.tvClose -> appendOnExpression(")", false)

            //Land operators
            R.id.tvSin -> appendOnExpression("sin(", false)
            R.id.tvCos -> appendOnExpression("cos", false)
            R.id.tvTan -> appendOnExpression("tan(", false)
            R.id.tvLn -> appendOnExpression("log(", false)
            R.id.tvE -> appendOnExpression("e", false)
            R.id.tvPi -> appendOnExpression("pi", false)

            R.id.tvClear -> {
                tvExpression.text = ""
                tvResult.text = ""
            }

            R.id.tvBack -> {
                val string = tvExpression.text.toString()
                if(string.isNotEmpty()){
                    tvExpression.text = string.substring(0,string.length-1)
                }
                tvResult.text = ""
            }

            R.id.tvEquals -> {
                try {
                    val expression = ExpressionBuilder(tvExpression.text.toString()).build()
                    val result = expression.evaluate()
                    val longResult = result.toLong()
                    if(result == longResult.toDouble())
                        tvResult.text = longResult.toString()
                    else
                        tvResult.text = result.toString()

                }catch (e:Exception){
                    Log.d("Exception"," message : " + e.message )
                }
            }
        }
    }

    fun appendOnExpression(string: String, canClear: Boolean){
        if(tvResult.text.isNotEmpty()){
            tvExpression.text = ""
        }

        if(!canClear){
            tvExpression.append(tvResult.text)
        }
        tvExpression.append(string)
        tvResult.text = ""
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            putString("Expression", tvExpression.text.toString())
            putString("Result", tvResult.text.toString())
        }
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        tvExpression.text = savedInstanceState.getString("Expression")
        tvResult.text = savedInstanceState.getString("Result")
    }
}