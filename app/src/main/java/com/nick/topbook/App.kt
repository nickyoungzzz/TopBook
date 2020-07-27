package com.nick.topbook

import android.app.Application
import com.nick.easyhttp.config.EasyHttp

class App : Application() {
	override fun onCreate() {
		super.onCreate()
		EasyHttp.init {
			connectTimeOut(3000)
			readTimeOut(3000)
			writeTimeOut(3000)
			interceptor { chain ->
				chain.proceed(chain.request()).apply {
				}
			}
		}
	}
}