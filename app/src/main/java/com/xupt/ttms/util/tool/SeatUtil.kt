package com.xupt.ttms.util.tool

import com.xupt.ttms.data.bean.studioBean.Ticket

object SeatUtil {

    fun listToMap(list: List<Ticket>): MutableMap<Int, Ticket> {
        val map = mutableMapOf<Int, Ticket>()
        for (ticket in list) {
            map[ticket.ticketId] = ticket
        }
        return map.toMutableMap()
    }

    fun appendToList (map:MutableMap<Int, Ticket>, id:Int, list:MutableList<Ticket>):MutableList<Ticket> {
        map[id]?.let { list.add(it) }
        return list
    }

    fun removeToList (map:MutableMap<Int, Ticket>, id:Int, list:MutableList<Ticket>):MutableList<Ticket> {
        map[id]?.let { list.remove(it) }
        return list
    }

}