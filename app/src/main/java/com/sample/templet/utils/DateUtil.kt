package com.sample.templet.utils

import android.annotation.SuppressLint
import android.util.Log
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateUtil {
    const val EEE_dd_MMM_hh_mm_aaa = "EEE, dd MMM hh:mm aaa"
    const val YYYY_DD_MM_HHMMSS = "yyyy-dd-MM HH:mm:ss"
    const val YYYY_MM_DD_HHMMSS = "yyyy-MM-dd HH:mm:ss"
    const val hh_mm__a = "hh:mm a"
    const val h_mm__a = "h:mm a"
    const val yyyy_MM_dd = "yyyy-MM-dd"
    const val yyyy_dd_MM = "yyyy-dd-MM"
    const val dd_MMMM_yyyy = "dd-MMMM-yyyy"
    const val dd__MMMM__yyyy = "dd MMMM yyyy"
    const val dd_MMMM_yyyy_zzzz = "dd MMMM yyyy zzzz"
    const val DATE_FORMAT_7 = "EEE, MMM d, ''yy"
    const val DATE_FORMAT_8 = "yyyy-MM-dd HH:mm:ss"
    const val DATE_FORMAT_9 = "h:mm a dd MMMM yyyy"
    const val DATE_FORMAT_10 = "K:mm a, z"
    const val DATE_FORMAT_11 = "hh 'o''clock' a, zzzz"
    const val DATE_FORMAT_12 = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    const val DATE_FORMAT_13 = "E, dd MMM yyyy HH:mm:ss z"
    const val DATE_FORMAT_14 = "yyyy.MM.dd G 'at' HH:mm:ss z"
    const val DATE_FORMAT_15 = "yyyyy.MMMMM.dd GGG hh:mm aaa"
    const val DATE_FORMAT_16 = "EEE, d MMM yyyy HH:mm:ss Z"
    const val DATE_FORMAT_17 = "yyyy-MM-dd'T'HH:mm:ss.SSSZ"
    const val DATE_FORMAT_18 = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX"
    const val EEEE_d_MMM_yyyy = "EEEE d, MMM yyyy"

    @Throws(ParseException::class)
    fun formatDateFromDateString(inputDateFormat: String?, outputDateFormat: String?, inputDate: String?): String? {
        val mParsedDate: Date
        val mOutputDateString: String
        val mInputDateFormat = SimpleDateFormat(inputDateFormat, Locale.getDefault())
        val mOutputDateFormat = SimpleDateFormat(outputDateFormat, Locale.getDefault())
        mParsedDate = mInputDateFormat.parse(inputDate!!)!!
        mOutputDateString = mOutputDateFormat.format(mParsedDate)
        return mOutputDateString
    }

    @Throws(ParseException::class)
    fun formatDateFromDateCalendar(outputDateFormat: String?, inputDate: Calendar?): String? {
        val mOutputDateFormat = SimpleDateFormat(outputDateFormat, Locale.getDefault())
        val mOutputDateString = mOutputDateFormat.format(inputDate!!.time)
        return mOutputDateString
    }

    @SuppressLint("SimpleDateFormat")
    fun getShowCalenderDateBasedOnTimezone(myCalendar: Calendar?, mTimeZone: String?, timeFormat: String? = Util.YYYY_MM_DD_HHMMSS): String {
        val format = SimpleDateFormat(timeFormat)
        format.timeZone = mGetTimezoneDnd(mTimeZone)!!
        if (myCalendar != null) {
            return format.format(myCalendar.time)
        } else {
            return "null"
        }
    }

    fun mGetTimezoneDnd(mTimezone: String?): TimeZone? {
        val timeZone: TimeZone? = if ((mTimezone != null) && mTimezone.isNotEmpty()) {
            TimeZone.getTimeZone(mTimezone)
        } else {
            TimeZone.getDefault()
        }
        return timeZone
    }

    fun getCalenderFromDateString(inputDate: String, inputDateFormat: String): Calendar {
        val cal = Calendar.getInstance()
        val sdf = SimpleDateFormat(inputDateFormat, Locale.ENGLISH)
        cal.time = sdf.parse(inputDate)!!
        return cal
    }

    @SuppressLint("SimpleDateFormat")
    fun convertToUTC(eventDateTime: String, inputDateFormat: String? = YYYY_MM_DD_HHMMSS): String? {
        try {
            val SDF = SimpleDateFormat(inputDateFormat)

            SDF.timeZone = mGetTimezoneDnd("IST")!!

            val fromDateValue = SDF.parse(eventDateTime)

            SDF.timeZone = TimeZone.getTimeZone("UTC")

            val fromDateConvert = SDF.format(fromDateValue)

            Log.e("TIME : ", "UTC ==> $fromDateConvert")

            return fromDateConvert
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun convertUTCToTargetTimeZone(inputDate: String?, inputDateFormat: String? = YYYY_MM_DD_HHMMSS, timeZoneDesc: String? ="IST"): String {
        var result = ""
        try {
            val SDF = SimpleDateFormat(inputDateFormat)
            SDF.timeZone = TimeZone.getTimeZone("UTC")
            val mSDF = SimpleDateFormat(inputDateFormat)
            mSDF.timeZone = TimeZone.getTimeZone(timeZoneDesc)
            val fromDateValue = SDF.parse(inputDate!!)
            result = mSDF.format(fromDateValue!!)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return result
    }
}