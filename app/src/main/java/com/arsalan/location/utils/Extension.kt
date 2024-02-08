package com.arsalan.location.utils

import java.text.DateFormat
import java.util.Date

object Extension  {
    fun Date.getDateAndTime(): String {
        return DateFormat.getDateTimeInstance().format(this)
    }
}