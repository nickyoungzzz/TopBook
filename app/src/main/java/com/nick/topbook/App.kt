package com.nick.topbook

import android.app.Application
import com.nick.easygo.config.EasyGo

class App : Application() {
	override fun onCreate() {
		super.onCreate()
		EasyGo.initialize()
	}
}