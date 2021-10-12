package com.example.calculator

import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import net.objecthunter.exp4j.ExpressionBuilder

class Expression() :Parcelable{
    private var deq :ArrayDeque<String> = ArrayDeque<String>()
    private var resultString :String = ""
    private var deqSubs :ArrayList<()->Unit> = ArrayList<()->Unit>()
    private var resultSubs :ArrayList<()->Unit> = ArrayList<()->Unit>()

    constructor(parcel: Parcel) : this() {
        deq = parcel.readString()?.split(" ") as ArrayDeque<String>
    }

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

    fun solve(){
        var brCount = 0
        var text = getString()
        for (i in text){
            if (i == '('){
                brCount += 1
            }
            else if (i == ')'){
                brCount -= 1
            }
        }
        for (i in 1..brCount){
            deq.add(")")
        }

        try {
            val expressionBuilder = ExpressionBuilder(getString()).build()
            val result = expressionBuilder.evaluate()
            val longResult = result.toLong()

            resultString = if(result == longResult.toDouble())
                longResult.toString()
            else
                result.toString()

            notifyResult()

        }catch (e:Exception){
            Log.d("Exception"," message : " + e.message )
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        //parcel.writeArray(arrayOf(deq))
        parcel.writeString(deq.joinToString(" "))//TODO remake storing, mb-binding
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Expression> {
        override fun createFromParcel(parcel: Parcel): Expression {
            return Expression(parcel)
        }

        override fun newArray(size: Int): Array<Expression?> {
            return arrayOfNulls(size)
        }
    }
}
