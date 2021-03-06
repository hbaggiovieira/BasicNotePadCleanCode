package com.example.basicnotepad.core.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import com.example.basicnotepad.BuildConfig
import java.math.BigDecimal
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

private val MONETARY_DEFAULT_LOCALE = Locale("pt", "BR")
private val DATE_DEFAULT_LOCALE = Locale.US
private val BRAZILIAN_DATE_FORMATS = arrayListOf("dd/MM/yyyy")
private val AMERICAN_DATE_FORMATS = arrayListOf(
    "yyyy-MM-dd'T'HH:mm:ss.SSS",
    "yyyy-MM-dd'T'HH:mm:ss.SSSXXX",
    "yyyy-MM-dd'T'HH:mm:ss",
    "yyyy-MM-dd'T'HH:mm",
    "yyyy-MM-dd",
    "yyyy/MM/dd'T'HH:mm",
    "yyyy/MM/dd"
)

fun String?.asBigDecimal(): BigDecimal {
    var text = onlyNumbers()
    val isNegative = text.startsWith("-")

    text = text.replace(Regex("[^\\d]"), "")

    var value = when (text.length) {
        0 -> "0.00"
        1 -> "0.0$text"
        2 -> "0.$text"
        else -> text.substring(0, text.length - 2) + "." + text.substring(
            text.length - 2,
            text.length
        )
    }

    if (isNegative) {
        value = "-$value"
    }

    return BigDecimal(value)
}

fun String?.onlyNumbers(): String {
    return this.toString().replace("[^\\d]".toRegex(), "")
}

// -- START -- Date functions

fun String.convertFromBrazilianFormatToDate() = BRAZILIAN_DATE_FORMATS.convertFromStringToDate(this)

fun String.convertFromAmericanFormatToDate(): Date =
    AMERICAN_DATE_FORMATS.convertFromStringToDate(this)

private fun ArrayList<String>.convertFromStringToDate(date: String): Date {
    this.forEach { format ->
        try {
            val simpleDateFormat = SimpleDateFormat(format, DATE_DEFAULT_LOCALE)
            simpleDateFormat.timeZone = TimeZone.getTimeZone("UTC")
            return simpleDateFormat.parse(date)!!
        } catch (e: Exception) {
        }
    }
    throw FormatNotFound("Formato de Data não encontrado")
}

class FormatNotFound(message: String) : Exception()

/** UTC change 21:00:00 GMT-03:00 to 00:00:00 **/
fun Date.convertDateToFormattedString(pattern: String = "dd/MM/yyyy"): String {
    val simpleDateFormat = SimpleDateFormat(pattern, DATE_DEFAULT_LOCALE)
    simpleDateFormat.timeZone = TimeZone.getTimeZone("UTC")
    return simpleDateFormat.format(this)
}

fun String.isBrazilianDateFormatValid(): Boolean {
    return try {
        val convertedDate = this.convertFromBrazilianFormatToDate().fixTimezone()
        val todayDate = Date(System.currentTimeMillis())
        convertedDate.before(todayDate)
    } catch (e: java.lang.Exception) {
        false
    }
}

fun Date.fixTimezone(): Date = Date(this.time + 3600 * 3000)

fun Date.revertTimezone(): Date = Date(this.time - 3600 * 3000)

fun Date.isDateLongerThan(date: Date) = this.time > date.time

fun getDateNow(): Date = Date()

fun getLocalDateTimeLong(): String {
    val date = Calendar.getInstance().time
    val dateTimeFormat = SimpleDateFormat("dd 'de' MMMM 'de' yyyy',' HH:mm ", Locale.getDefault())
    return dateTimeFormat.format(date)
}

fun getLocalDateTime(): String {
    val date = Calendar.getInstance().time
    val dateTimeFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    return dateTimeFormat.format(date)
}

fun getLocalDate(): String {
    val date = Calendar.getInstance().time
    val dateTimeFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return dateTimeFormat.format(date)
}

fun String.formattedAsDateFromApi(): String {
    var formattedData: String? = null
    if (length >= 9) {
        formattedData = "${substring(8, 10)}/${substring(5, 7)}/${substring(0, 4)}"
    }
    return formattedData.toString()
}

fun String.formattedAsDate(): String {
    return if (length >= 8) {
        substring(0, 2) + "/" + substring(2, 4) + "/" + substring(4, length)
    } else {
        this
    }
}

// -- END -- Date functions
fun copyBoard(context: Context, string: String) {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("label", string)
    clipboard.setPrimaryClip(clip)
}

fun String.formattedAsAccountNumber(): String {
    var value = onlyNumbers()

    value = when (value.length) {
        0 -> ""
        1 -> value
        else -> value.substring(0, value.length - 1) +
                "-" +
                value.substring(value.length - 1, value.length)
    }

    return value
}

fun String.formattedAsCpf(): String {
    return if (length == 11) {
        substring(0, 3) +
                "." + substring(3, 6) +
                "." + substring(6, 9) +
                "-" + substring(9, length)
    } else {
        this
    }
}

fun String.formattedAsCnpj(): String {
    return if (length == 14) {
        substring(0, 2) +
                "." + substring(2, 5) +
                "." + substring(5, 8) +
                "/" + substring(8, 12) +
                "-" + substring(12, length)
    } else {
        this
    }
}

fun String.formattedAsDocumentNumber(): String {
    return if (length == 11) {
        formattedAsCpf()
    } else {
        formattedAsCnpj()
    }
}

fun String.isValidCPF(): Boolean {
    val weight = intArrayOf(11, 10, 9, 8, 7, 6, 5, 4, 3, 2)

    return if (!isNullOrBlank()) {
        val document = toString().replace("[^\\d]".toRegex(), "")
        if (document != "00000000000") {
            try {
                val firstValidator = getDocumentValidator(document.substring(0, 9), weight)
                val secondValidator =
                    getDocumentValidator(document.substring(0, 9) + firstValidator, weight)

                document == document.substring(
                    0,
                    9
                ) + firstValidator.toString() + secondValidator.toString()
            } catch (exception: Exception) {
                if (BuildConfig.DEBUG) {
                    exception.printStackTrace()
                }
                false
            }
        } else {
            false
        }
    } else {
        false
    }
}

fun String.isValidCNPJ(): Boolean {
    val weight = intArrayOf(6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2)

    return if (!isNullOrEmpty()) {
        val document = replace("[^\\d]".toRegex(), "")

        try {
            val firstValidator = getDocumentValidator(document.substring(0, 12), weight)
            val secondValidator =
                getDocumentValidator(document.substring(0, 12) + firstValidator, weight)

            document == document.substring(
                0,
                12
            ) + firstValidator.toString() + secondValidator.toString()
        } catch (exception: Exception) {
            false
        }
    } else {
        false
    }
}

private fun getDocumentValidator(text: String, weight: IntArray): Int {
    var sum = 0
    var validator: Int

    for (index in text.length - 1 downTo 0) {
        validator = Integer.parseInt(text.substring(index, index + 1))
        sum += validator * weight[weight.size - text.length + index]
    }

    sum = 11 - sum % 11
    return if (sum > 9) 0 else sum
}

fun Double.toMoneyFormat(): String {
    val value =
        NumberFormat.getCurrencyInstance(MONETARY_DEFAULT_LOCALE).format(this).split('$')[1].trim()
    return "R$ $value"
}

fun BigDecimal.toDecimalFormat(): String =
    NumberFormat.getCurrencyInstance(MONETARY_DEFAULT_LOCALE).format(this).split('$')[1].trim()

fun BigDecimal.toMoneyFormat(): String {
    val value =
        NumberFormat.getCurrencyInstance(MONETARY_DEFAULT_LOCALE).format(this).split('$')[1].trim()
    return "R$ $value"
}

fun CharSequence.validateBirthDate(): Boolean {
    val birthDateText: String = this.toString()
    val mBirthDate: Calendar = Calendar.getInstance()
    val dateToday: Calendar = Calendar.getInstance()
    val date = SimpleDateFormat("dd/MM/yyyy", Locale("pt-Br"))
    date.isLenient = false

    return try {
        mBirthDate.time = date.parse(birthDateText)!!
        mBirthDate.time < dateToday.time
    } catch (e: Exception) {
        false
    }
}
