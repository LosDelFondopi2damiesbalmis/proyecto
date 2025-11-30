package com.proyecto.PeluPos

import android.app.Application

class PeluPosApp : Application() {

    companion object {
        lateinit var instance: PeluPosApp
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}