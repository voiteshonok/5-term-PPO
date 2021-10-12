package com.example.calculator

import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import net.objecthunter.exp4j.ExpressionBuilder

class Expression() :Parcelable{
    private var deq :ArrayDeque<String> = ArrayDeque<String>()

    constructor(parcel: Parcel) : this() {
        deq = parcel.readString()?.split(" ") as ArrayDeque<String>
    }

    fun append(string: String){
        deq.add(string)
    }

    fun pop(){
        if (deq.isNotEmpty()){
            deq.removeLast()
        }
    }

    fun clear(){
        deq.clear()
    }

    fun getString(): String{
        return deq.joinToString("")
    }

    fun getStringResult(): String {
        try {
            val expressionBuilder = ExpressionBuilder(getString()).build()
            val result = expressionBuilder.evaluate()
            val longResult = result.toLong()

            return if(result == longResult.toDouble())
                longResult.toString()
            else
                result.toString()

        }catch (e:Exception){
            Log.d("Exception"," message : " + e.message )
        }
        return ""
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        //parcel.writeArray(arrayOf(deq))
        parcel.writeString(deq.joinToString(" "))//TODO
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
