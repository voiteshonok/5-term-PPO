package com.example.calculator

import android.os.Parcel
import android.os.Parcelable

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
