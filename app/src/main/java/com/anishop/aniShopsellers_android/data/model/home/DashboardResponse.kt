package com.anishop.aniShopsellers_android.data.model.home

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Month
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters

data class DashboardResponse(
    val status: String,
    val allTypes: OrderSummary,
    val graphData: List<GraphEntry>,
    //val message: String?
)

data class OrderSummary(
    val totalOrders: Int,
    val newOrders: Int,
    val pendingOrders: Int,
    val dispatchedOrders: Int,
    val inComplete: Int,
    val completed: Int,
    val cancelledOrders: Int
)

data class GraphEntry(
    val date: String,
    val totalQuantity: Int,
    val status: String
)

fun processOrders(graphData: List<GraphEntry>): Triple<Map<String, Int>, Map<String, Int>, Map<String, Int>> {
    val formatter = DateTimeFormatter.ISO_DATE_TIME
    val currentDate = LocalDate.now()

    // Get the start and end of the current week (Monday to Sunday)
    val startOfWeek = currentDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
    val endOfWeek = startOfWeek.plusDays(6)

    // Get the first and last day of the current month
    val startOfMonth = currentDate.withDayOfMonth(1)
    val endOfMonth = currentDate.withDayOfMonth(currentDate.lengthOfMonth())

    // Initialize maps with default values (0) for missing days/months
    val thisWeekOrders = mutableMapOf<String, Int>().apply {
        DayOfWeek.values().forEach { put(it.toString(), 0) } // Monday-Sunday
    }
    val thisMonthOrders = mutableMapOf<String, Int>().apply {
        (1..currentDate.lengthOfMonth()).forEach { put(it.toString(), 0) } // 1st - last day of month
    }
    val thisYearOrders = mutableMapOf<String, Int>().apply {
        Month.values().forEach { put(it.toString(), 0) } // January-December
    }

    for (entry in graphData) {
        val orderDate = LocalDateTime.parse(entry.date, formatter).toLocalDate()
        val totalQuantity = entry.totalQuantity

        // Check if the order falls in this week
        if (!orderDate.isBefore(startOfWeek) && !orderDate.isAfter(endOfWeek)) {
            val dayOfWeek = orderDate.dayOfWeek.toString()  // "MONDAY", "TUESDAY", etc.
            thisWeekOrders[dayOfWeek] = thisWeekOrders.getOrDefault(dayOfWeek, 0) + totalQuantity
        }

        // Check if the order falls in this month
        if (!orderDate.isBefore(startOfMonth) && !orderDate.isAfter(endOfMonth)) {
            val dayOfMonth = orderDate.dayOfMonth.toString() // "1", "2", etc.
            thisMonthOrders[dayOfMonth] = thisMonthOrders.getOrDefault(dayOfMonth, 0) + totalQuantity
        }

        // Check if the order falls in this year
        if (orderDate.year == currentDate.year) {
            val monthName = orderDate.month.toString() // "JANUARY", "FEBRUARY", etc.
            thisYearOrders[monthName] = thisYearOrders.getOrDefault(monthName, 0) + totalQuantity
        }
    }

    return Triple(thisWeekOrders, thisMonthOrders, thisYearOrders)
}