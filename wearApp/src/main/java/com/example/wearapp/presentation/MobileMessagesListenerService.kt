package com.example.wearapp.presentation

import com.example.datasource.database.DayEntity
import com.example.repository.DayRepository
import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.WearableListenerService
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

@AndroidEntryPoint
class MobileMessagesListenerService : WearableListenerService(
) {

    @Inject
    lateinit var moshi: Moshi
    @Inject
    lateinit var dayRepository: DayRepository
    @OptIn(ExperimentalStdlibApi::class)
    override fun onMessageReceived(message: MessageEvent) {
        super.onMessageReceived(message)
        println("message reçu ${message.path}")
        if (message.path == "/currentday") {
            CoroutineScope(Dispatchers.IO).launch {
                moshi.adapter<DayEntity>().fromJson(String(message.data))?.also {day ->
                    dayRepository.insertANewDay(day)
                }

            }

        }
    }
}