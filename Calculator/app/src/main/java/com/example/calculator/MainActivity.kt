package com.example.calculator

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import net.objecthunter.exp4j.ExpressionBuilder
import android.util.Log
import android.view.View
import com.example.calculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var expression: Expression = Expression()
    private lateinit var bind: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)

        expression.addSubDeq (fun (){
            tvExpression.text = expression.getString()
        })

        expression.addSubResult (fun (){
            tvResult.text = expression.getResult()
        })

        val expression = ExpressionBuilder("π").build()
        val result = expression.evaluate()
        val longResult = result.toLong()
        if(result == longResult.toDouble())
            tvResult.text = longResult.toString()
        else
            tvResult.text = result.toString()
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
            R.id.tvCos -> appendOnExpression("cos(", false)
            R.id.tvTan -> appendOnExpression("tan(", false)
            R.id.tvLn -> appendOnExpression("log(", false)
            R.id.tvEX -> appendOnExpression("e^(", false)
            R.id.tvE -> appendOnExpression("e", false)
            R.id.tvPi -> appendOnExpression("pi", false)
            R.id.tvSqrt -> appendOnExpression("sqrt(", false)
            R.id.tvPow -> appendOnExpression("^(", false)
            R.id.tvSqr -> appendOnExpression("^(2)", false)
            R.id.tv2X -> appendOnExpression("2^(", false)

            R.id.tvClear -> {
                expression.clear()
            }

            R.id.tvBack -> {
                expression.pop()
            }

            R.id.tvEquals -> {
                expression.solve()
                changeOrientation()
            }
        }
    }

    private fun appendOnExpression(string: String, canClear: Boolean){
        if(tvResult.text.isNotEmpty()){
            expression.clear()
            if(!canClear){
                expression.append(tvResult.text as String)
            }
            expression.resultStringClear()
        }

        expression.append(string)
    }

    /*override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            //putString("Expression", tvExpression.text.toString())
            putString("Result", tvResult.text.toString())
            putParcelable("expression", expression)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        expression = savedInstanceState.getParcelable<Expression>("expression")!!
        //tvExpression.text = expression.getString()//TODO
        tvResult.text = savedInstanceState.getString("Result")
    }*/

    private fun changeOrientation() {
        val orientation = this.resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        } else {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        }
        setContentView(bind.root)
    }
}