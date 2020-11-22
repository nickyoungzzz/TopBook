package com.nick.topbook

import android.app.Application
import com.nick.easygo.config.EasyGo

class App : Application() {
	override fun onCreate() {
		super.onCreate()
		EasyGo.initialize {
			connectTimeOut(3000)
			readTimeOut(3000)
			writeTimeOut(3000)
			addInterceptor { chain ->
				chain.proceed(chain.request().apply {
					println(this.headerMap)
				}).apply {
					println(this.code)
				}
			}
		}
	}
}