package com.example.time_tracking_app

import android.content.Context
import com.example.datasource.database.DayEntity
import com.google.android.gms.wearable.Node
import com.google.android.gms.wearable.Wearable
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class SendDayToWearUsecase @Inject constructor(
    @ApplicationContext val context : Context,
    private val moshi : Moshi
) {
    val path = "/currentday"

    @OptIn(ExperimentalStdlibApi::class)
    suspend fun sendDay(day:DayEntity) {
        val nodes = suspendCoroutine<List<Node>> {continuation ->
            Wearable.getNodeClient(context).connectedNodes.addOnSuccessListener {
                continuation.resume(it)
            }
        }
        nodes.forEach{node ->
            Wearable.getMessageClient(context).sendMessage(node.id,path,moshi.adapter<DayEntity>().toJson(day).toByteArray())
        }


    }
}