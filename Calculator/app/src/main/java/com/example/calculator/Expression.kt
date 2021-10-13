package com.example.calculator

import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import net.objecthunter.exp4j.ExpressionBuilder

class Expression(){
    private var deq :ArrayDeque<String> = ArrayDeque<String>()
    private var resultString :String = ""
    private var deqSubs :ArrayList<()->Unit> = ArrayList<()->Unit>()
    private var resultSubs :ArrayList<()->Unit> = ArrayList<()->Unit>()

    //private var hasLeftBrace :List<String> = listOf("sin(", "cos(", "tan(", "(", "log(", "sqrt(", "^(", "2^(", "e^(")
    private var notFirst :List<String> = listOf("^(2)", "^(", "*", "/")
    private var ops :List<String> = listOf("-", "+", "*", "/")

    private fun notifyDeq(){
        for (sub in deqSubs){
            sub()
        }
    }

    private fun notifyResult(){
        for (sub in resultSubs){
            sub()
        }
    }

    fun addSubDeq(sub: () -> Unit){
        deqSubs.add(sub)
    }

    fun addSubResult(sub: () -> Unit){
        resultSubs.add(sub)
    }

    fun append(string: String){
        fun isFirst(): Boolean{
            return (deq.size == 0 || deq.last().endsWith("("))
        }

        if ((notFirst.contains(string) && isFirst()) ||
                ((getBrCount() == 0 || deq.last().endsWith("(") || ops.contains(deq.last())) && string == ")")){
            return
        }

        if (deq.isNotEmpty() && ops.contains(string) && ops.contains(deq.last())){
            pop()
        }

        deq.add(string)
        notifyDeq()
    }

    fun pop(){
        if (deq.isNotEmpty()){
            deq.removeLast()
        }
        notifyDeq()
    }

    fun clear(){
        deq.clear()
        notifyDeq()
    }

    fun getString(): String{
        return deq.joinToString("")
    }

    fun resultStringClear(){
        resultString = ""
        notifyResult()
    }

    fun getResult(): String{
        return resultString
    }

    private fun getBrCount() :Int{
        var brCount = 0
        val text = getString()
        for (i in text){
            if (i == '('){
                brCount += 1
            }
            else if (i == ')'){
                brCount -= 1
            }
        }

        return brCount
    }

    fun solve() :ResultStatus{
        val brCount = getBrCount()
        var text = getString()
        for (i in 1..brCount){
            text += ")"
        }

        try {
            val expressionBuilder = ExpressionBuilder(if (text.isNullOrEmpty()) "0" else text).build()
            val result = expressionBuilder.evaluate()
            val longResult = result.toLong()

            resultString = if(result == longResult.toDouble())
                longResult.toString()
            else
                result.toString()

            notifyResult()
            return ResultStatus.OK
        }catch (e:Exception){
            Log.d("Exception"," message : " + e.message )

            return ResultStatus.ERROR
        }
    }
}
