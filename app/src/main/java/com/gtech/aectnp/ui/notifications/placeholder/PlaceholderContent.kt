package com.gtech.aectnp.ui.notifications.placeholder
import android.text.format.DateFormat
import com.google.firebase.database.IgnoreExtraProperties
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
 class NotificationModel() {
  var id: String?=null
  var title: String?=null
  var branch: String?=null
  var message: String?=null
  var date: Long?=null
  var group:String?=null


  fun getdateasformatted(date:Long):String{

   var formatter = SimpleDateFormat("dd-mm-yyyy")
   var cal = Calendar.getInstance(Locale.ENGLISH)
   cal.timeInMillis = date
   var date: String = DateFormat.format("hh:mm:aa dd-MM-yyyy ", cal).toString()
   return  date
  }


}
