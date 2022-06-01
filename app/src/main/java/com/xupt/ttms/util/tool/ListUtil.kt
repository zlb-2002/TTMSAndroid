package com.xupt.ttms.util.tool

object ListUtil {

    fun <T> List<T>.myString():String {
        var string = ""
        for (t in this) {
            string += "$t,"
        }
        return string.substring(0,string.length-1)
    }

}