package com.karimali.baseapp.common.extensions

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Point
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.ocpsoft.prettytime.PrettyTime
import java.lang.reflect.GenericDeclaration
import java.lang.reflect.Type
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random
import android.util.DisplayMetrics
import android.util.Size
import android.view.WindowManager
import kotlin.collections.ArrayList

import android.content.ClipData
import android.content.ClipboardManager

import android.content.Context.CLIPBOARD_SERVICE
import com.karimali.baseapp.common.models.ResultState
import java.time.LocalDate
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter


infix fun<T> Boolean.then(first:T) : T? = if(this) first else null

/**
 * Convert Integers To Enums If Exists
 */
inline fun <reified T:Enum<T>>Int.enumerate() : Enum<T> {
    return enumValues<T>()[this]
}

/**
 * Convert String To Enums If Exists
 */
inline fun <reified T:Enum<T>>String.enumerate() : Enum<T> {
    return enumValues<T>().first { obj -> obj.name.contains(this) }
}

infix fun Int.dp(context: Context) : Int = (this * context.resources.displayMetrics.density + 0.5f).toInt()

infix fun Float.dp(context: Context) : Int = (this * context.resources.displayMetrics.density + 0.5f).toInt()

infix fun Context.toDp(value: Int) = ( value / resources.displayMetrics.density).toInt()

fun String.safeColorParse(defaultColor:String): Int {
    return try {
        Color.parseColor(this)
    }catch (e:java.lang.Exception){
        Color.parseColor(defaultColor)
    }
}
fun Double.convertToPrice() : String{
    return  BigDecimal(this).setScale(2, RoundingMode.HALF_EVEN).toString()
}
fun Double.convertToPriceWithCurrency(currency: String) : String{
    return  "${BigDecimal(this).setScale(2, RoundingMode.HALF_EVEN).toString()} $currency"
}

@RequiresApi(Build.VERSION_CODES.O)
fun String.convertToAgo(): String {
    val prettyTime = PrettyTime(Locale.getDefault())
    return prettyTime.format(Date(dateFormat(this, Locale.ENGLISH)))
}

@RequiresApi(Build.VERSION_CODES.O)
fun Date.convertToAgo(): String {
    val prettyTime = PrettyTime(Locale.getDefault())
    return prettyTime.format(this)
}

@RequiresApi(Build.VERSION_CODES.O)
fun String.convertTo12HoursFormat(): String {
    return try {
        val mDate = this.replace(" ","T") + ".000000Z"
        val parsedDate =  Date(dateFormat(mDate))
        Log.i("Date","$parsedDate")
        val dateFormat: DateFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
            dateFormat.format(parsedDate)
    }catch (e:Exception){
        Log.i("Date","Date Err ${e.message}")
        ""
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun String.toDate(locale: Locale? = null): Date? {
    return try {
        //Log.i("Expire"," Date Here ${Date(Utils.dateFormat(mDate))}")
        Date(dateFormat(this,locale = Locale.ENGLISH))
    }catch (e:Exception){
        Log.i("Expire","Fail $e")
        null
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun dateFormat(date:String, locale: Locale? = null, format : String ? = "EEE, d MMM yyyy HH:mm:ss") : String {
    return try {
        val parsedDate = OffsetDateTime.parse(date)
        val formatter = DateTimeFormatter.ofPattern(format, locale ?: Locale.getDefault())
        formatter.format(parsedDate)
    }catch (e:Exception){
        ""
    }
}
@RequiresApi(Build.VERSION_CODES.O)
fun LocalDate.toDate(): Date = Date.from(this.atStartOfDay(ZoneId.systemDefault()).toInstant())


@RequiresApi(Build.VERSION_CODES.O)
fun String.convertToDate(locale: Locale? = null, format: String? = null): Date? {
    return try {
        val date = SimpleDateFormat(format ?: "yyyy-MM-dd hh:mm:ss", locale ?: Locale.getDefault()).parse(this)
        val cal = Calendar.getInstance()
        cal.time = date
        cal.time
    }catch (e:Exception){
        Log.i("Expire","Fail $e")
        null
    }
}

fun String.dMMMyyyyToDate(locale: Locale? = null ) : Date? {
    fun getMonthIndex(month:String) : Int {
        val date = SimpleDateFormat("MMM", Locale.ENGLISH).parse(month)
        val cal = Calendar.getInstance()
        date?.let {
            cal.time = date
            return cal[Calendar.MONTH] + 1
        }
        return 0
    }
    return try {
        val day = this.split(" ").first()
        val month = this.split(" ")[1]
        val year = this.split(" ").last()

        Log.i("ClassDate","Month $month (${getMonthIndex(month)}) Year $year Day $day")
        val format1 = SimpleDateFormat("yyyy-MM-dd hh:mm:ss",locale ?: Locale.getDefault())
        val date = format1.parse("$year-${getMonthIndex(month)}-$day 00:00:00")
        Log.i("ClassDate","Date $date")
        date

    }catch (e:Exception){
        Log.i("ClassDate","Fail $e")
        null
    }
}

fun String?.dMMMyyyyToDate() : Date? {
    fun getMonthIndex(month:String) : Int {
        val date = SimpleDateFormat("MMM", Locale.ENGLISH).parse(month)
        val cal = Calendar.getInstance()
        date?.let {
            cal.time = date
            return cal[Calendar.MONTH] + 1
        }
        return 0
    }
    return try {
        this?.let {
            val day = this.split(" ").first()
            val month = this.split(" ")[1]
            val year = this.split(" ").last()

            Log.i("ClassDate","Month $month (${getMonthIndex(month)}) Year $year Day $day")
            val format1 = SimpleDateFormat("yyyy-MM-dd hh:mm:ss",Locale.getDefault())
            val date = format1.parse("$year-${getMonthIndex(month)}-$day 00:00:00")
            Log.i("ClassDate","Date $date")
            date
        }
        return Calendar.getInstance().time
    }catch (e:Exception){
        Log.i("ClassDate","Fail $e")
        null
    }
}

fun String.ddMMMMyyyyToDate() : Date? {
    fun getMonthIndex(month:String) : Int {
        val date = SimpleDateFormat("MMMM", Locale.ENGLISH).parse(month)
        val cal = Calendar.getInstance()
        date?.let {
            cal.time = date
            return cal[Calendar.MONTH] + 1
        }
        return 0
    }
    return try {
        val day = this.split(" ").first()
        val month = this.split(" ")[1]
        val year = this.split(" ").last()

        Log.i("ClassDate","Month $month (${getMonthIndex(month)}) Year $year Day $day")
        val format1 = SimpleDateFormat("yyyy-MM-dd hh:mm:ss",Locale.getDefault())
        val date = format1.parse("$year-${getMonthIndex(month)}-$day 00:00:00")
        Log.i("ClassDate","Date $date")
        date

    }catch (e:Exception){
        Log.i("ClassDate","Fail $e")
        null
    }
}

fun String.timeStampToDate(locale: Locale? = null) :Date? {
    //Fri Nov 25 00:00:00 GMT+02:00 2022
    val cal = Calendar.getInstance()
    try {
        val date = SimpleDateFormat("E MMM dd hh:mm:ss Z yyyy",locale ?: Locale.getDefault()).parse(this)
        date?.let {
            cal.time = date
        }
    }catch (e:Exception){
        return null
    }
    return cal.time
}
fun String.dayTimeToDate(locale: Locale? = null ) : Date? {
    return try {
        val date = SimpleDateFormat("hh:mm a", Locale.ENGLISH).parse(this)
        val cal = Calendar.getInstance()
        date?.let {
            cal.time = date
        }
        cal.time
    }catch (e:Exception){
        Log.i("ClassDate","Fail $e")
        null
    }
}

fun String.dayTimeToDate() : Date? {
    return try {
        val cal = Calendar.getInstance()
        val todayCal = Calendar.getInstance()
        val date = SimpleDateFormat("hh:mm a", Locale.ENGLISH).parse(this)
        date?.let {
            cal.time = date
        }
        cal.set(todayCal.get(Calendar.YEAR),todayCal.get(Calendar.MONTH),todayCal.get(Calendar.DATE))
        cal.time
    }catch (e:Exception){
        Log.i("ClassDate","Fail $e")
        null
    }
}

fun String.ddMMMMyyyyToDate(locale: Locale? = null ) : Date? {

    fun getMonthIndex(month:String) : Int {
        val date = SimpleDateFormat("MMMM", Locale.ENGLISH).parse(month)
        val cal = Calendar.getInstance()
        date?.let {
            cal.time = date
            return cal[Calendar.MONTH] + 1
        }
        return 0
    }

    return try {

        val day = this.split(" ").first()
        val month = this.split(" ")[1]
        val year = this.split(" ").last()

        Log.i("ClassDate","Month $month (${getMonthIndex(month)}) Year $year Day $day")
        val format1 = SimpleDateFormat("1",locale ?: Locale.getDefault())
        val date = format1.parse("$year-${getMonthIndex(month)}-$day 00:00:00")
        Log.i("ClassDate","Date $date")
        date

    }catch (e:Exception){
        Log.i("ClassDate","Fail $e")
        null
    }

}

fun String.toDate(format: String,locale: Locale?): Date? {
    return try {
        val format1 = SimpleDateFormat("yyyyy-MMM-dd hh:mm:ss",locale)
        val date = format1.parse("2011-01-18 00:00:00.0")
        Log.i("ClassDate","Date $date")
        date
    }catch (e:Exception){
        Log.i("Expire","Fail $e")
        null
    }
}

fun String.dateToTime(locale: Locale?): String? {
    return try {
        val format1 = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss",locale)
        val date = format1.parse(this)
        val format2 = SimpleDateFormat("HH:MM",locale)
//        Log.i("ClassDate","Date $date")
        format2.format(date)
    }catch (e:Exception){
        Log.i("Expire","Fail $e")
        null
    }
}

fun Fragment.getScreenSize() : Point
{
    val display =  this.requireActivity().window.windowManager.defaultDisplay
    val size = Point()
    display.getSize(size)
    return size
}

fun Long.toDateString(format : String? = "yyyy/MM/dd",locale: Locale? = null) : String {
    val formatter : SimpleDateFormat = SimpleDateFormat(format,locale ?: Locale.getDefault())
    return formatter.format(Date(this))
}

fun Date.toDateString(format : String? = "yyyy/MM/dd",locale: Locale? = null) : String {
    val formatter : SimpleDateFormat = SimpleDateFormat(format,locale ?: Locale.getDefault())
    return formatter.format(this)
}

fun Date.yMMd(locale: Locale? = null) : String {
    val formatter : SimpleDateFormat = SimpleDateFormat("dd MMM yyyy",locale ?: Locale.getDefault())
    return formatter.format(this)
}

fun Date.dayOfMath(locale: Locale? = null) : String {
    val formatter : SimpleDateFormat = SimpleDateFormat("dd/MM", locale ?: Locale.getDefault())
    return formatter.format(this)
}

fun Date.dayNumber(locale: Locale? = null) : Int {
    val formatter : SimpleDateFormat = SimpleDateFormat("dd", locale ?: Locale.getDefault())
    return formatter.format(this).toInt()
}




fun Date.dayOfWeek(locale: Locale? = null,fullDayName : Boolean ?= false) : String {
    val formatter : SimpleDateFormat = SimpleDateFormat(if(fullDayName == true) "EEEE" else "E", locale ?: Locale.getDefault())
    return formatter.format(this)
}

fun Date.ddMmmYyyy(locale: Locale? = null) : String {
    val formatter : SimpleDateFormat = SimpleDateFormat("dd MMM,yyyy", locale ?: Locale.getDefault())
    return formatter.format(this)
}
fun Date.dMyyyy(locale: Locale? = null) : String {
    val formatter : SimpleDateFormat = SimpleDateFormat("dd,M.yyyy", locale ?: Locale.getDefault())
    return formatter.format(this)
}
fun Date.toFullTimeStamp(locale: Locale? = null) : String {
    val formatter : SimpleDateFormat = SimpleDateFormat("E MMM dd hh:mm:ss Z yyyy", locale ?: Locale.getDefault())
    return formatter.format(this)
}

fun Date.ddMMMyyyy(locale: Locale? = null) : String {
    val formatter : SimpleDateFormat = SimpleDateFormat("E , dd MMM yyyy", locale ?: Locale.getDefault())
    return formatter.format(this)
}
fun Date.ddMMMyyyy() : String {
    val formatter : SimpleDateFormat = SimpleDateFormat("E , dd MMM yyyy", Locale.getDefault())
    return formatter.format(this)
}

fun Date.dayTime(locale: Locale? = null) : String {
    val formatter : SimpleDateFormat = SimpleDateFormat("hh:mm a", locale ?: Locale.getDefault())
    return formatter.format(this)
}

fun Date.dayTime() : String {
    val formatter : SimpleDateFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
    return formatter.format(this)
}



fun Date.duration(locale: Locale? = null) : Long {
    val formatter : SimpleDateFormat = SimpleDateFormat("hh:mm:ss",locale ?: Locale.getDefault())
    return formatter.calendar.timeInMillis
}

fun Long.toDate(format : String? = "yyyy/MM/dd",locale: Locale? = null) : Date {
    val formatter : SimpleDateFormat = SimpleDateFormat(format, locale ?: Locale.getDefault())
    return Date(this)
}

fun Int.toBoolean() : Boolean = this == 1

fun Boolean.toInt() : Int = if(this) 1 else 0

fun String.removeSpacesOrArabic() : String{
    var replaced = this.replace("[ا-ي\\s+]".toRegex(), "")
    if(replaced.isEmpty()){
        replaced = "untitled-${Random.nextInt(99)}"
    }
    return replaced
}

fun <T>Exception.formatCommonError() : ResultState<T> {
    return when{
        this.message!!.contains("host",true) -> ResultState.NetworkException(message)
        this.message!!.contains("network",true) -> ResultState.NetworkException(message)
        this.message!!.contains("socket",true) -> ResultState.NetworkException(message)
        else -> ResultState.EmptyData("")
    }
}

fun String.isEmail() : Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.isPhone() : Boolean {
    return android.util.Patterns.PHONE.matcher(this).matches()
}

fun String.isAllZeros() : Boolean{
    return this.all { s -> s == '0' }
}

fun <T : GenericDeclaration?>List<T>.indexOfFirstSafe(predicate: (T) -> Boolean) : Int {
    return try {
        indexOfFirst(predicate)
    }catch (e:Exception){
        -1
    }
}

inline fun <reified T> String.convertToListObject(): List<T>? {
    val listType: Type = object : TypeToken<List<T?>?>() {}.type
    return Gson().fromJson<List<T>>(this, listType)
}

fun String.copyText(context: Context){
    val clipboard: ClipboardManager? = context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager?
    val clip = ClipData.newPlainText("label", this)
    clipboard?.setPrimaryClip(clip)
}


fun Locale.isLanguage(languageCode : String) : Boolean {
    return language == languageCode
}

fun <T>List<T>.toArrayList() : ArrayList<T> {
    val arr : ArrayList<T> = kotlin.collections.ArrayList()
    arr.addAll(this)
    return arr
}
object ScreenSizeCompat {

    private val api: Api = Api()

    fun getScreenSize(context: Context): Triple<Size?,Int?,Int?> = api.getScreenSize(context)

    @Suppress("DEPRECATION")
    private open class Api {
        open fun getScreenSize(context: Context): Triple<Size?,Int?,Int?> {
            val display = context.getSystemService(WindowManager::class.java).defaultDisplay
            val metrics = if (display != null) {
                DisplayMetrics().also { display.getRealMetrics(it) }
            } else {
                Resources.getSystem().displayMetrics
            }
            return Triple(Size(metrics.widthPixels, metrics.heightPixels),metrics.densityDpi,0)
        }
    }

}