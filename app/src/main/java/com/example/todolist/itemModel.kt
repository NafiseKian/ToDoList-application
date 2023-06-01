package com.example.todolist

import java.util.*

class ItemModel( var id:Int=getAutoId(), var Task:String="" )
{
    companion object {
    fun getAutoId(): Int {
        val random = Random()
        return random.nextInt(100)
    }
}
}