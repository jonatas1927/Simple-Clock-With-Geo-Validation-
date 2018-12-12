package com.simplemobiletools.clock.receivers

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Handler
import com.google.android.gms.location.LocationServices
import com.simplemobiletools.clock.activities.ReminderActivity
import com.simplemobiletools.clock.extensions.*
import com.simplemobiletools.clock.helpers.ALARM_ID
import com.simplemobiletools.clock.services.GeoData
import com.simplemobiletools.commons.extensions.toast

class AlarmReceiver : BroadcastReceiver() {
    @SuppressLint("MissingPermission")
    override fun onReceive(context: Context, intent: Intent) {
        val id = intent.getIntExtra(ALARM_ID, -1)
        val alarm = context.dbHelper.getAlarmWithId(id) ?: return

        var locationGlob: Location = Location("dummyprovider");
        LocationServices.getFusedLocationProviderClient(context).lastLocation
                .addOnSuccessListener { location: Location ->
                    locationGlob = location


                    if (alarm.latitude != 0.0 && GeoData.VerificaDistancia(
                                    alarm.latitude,
                                    locationGlob.latitude,
                                    alarm.longitude,
                                    locationGlob.longitude,
                                    0.0,//para a altitude n fazer diferença agora
                                    0.0,
                                    alarm.distancia + locationGlob!!.accuracy
                            )) {
                        if (context.isScreenOn()) {
                            context.showAlarmNotification(alarm)
                            Handler().postDelayed({
                                context.hideNotification(id)
                            }, context.config.alarmMaxReminderSecs * 1000L)
                        } else {
                            Intent(context, ReminderActivity::class.java).apply {
                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                putExtra(ALARM_ID, id)
                                context.startActivity(this)
                            }
                        }
                    } else {
                        context.toast("eita caramba, você esta onde deveria, que bom :)", 50000000)
                        print("MEU DEUS DO CÉUUU PQ NÃO FUNCIONA??????????????" +
                                alarm.latitude +
                                locationGlob!!.latitude +
                                alarm.longitude +
                                locationGlob!!.longitude +
                                0.0 +//para a altitude n fazer diferença agora
                                0.0 +
                                alarm.distancia + locationGlob!!.accuracy)
                    }
                }
    }
}
