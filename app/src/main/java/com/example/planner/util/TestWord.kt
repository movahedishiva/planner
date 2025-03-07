package com.example.planner.util

fun palindromee(word: String):Boolean{

    var start: Int =0
    var end: Int = word.length-1
    while (start<end){

        if(word.get(start).equals(word.get(end)))
        {
            ++start
            --end
            continue
        }else {
            return false
        }
    }

    return true
}


fun palindromee2(word: String):Boolean{

    val reverse: String=word.reversed()
    if(word.equals(reverse))return true

    return false
}