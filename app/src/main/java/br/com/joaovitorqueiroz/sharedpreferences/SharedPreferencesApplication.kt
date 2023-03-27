package br.com.joaovitorqueiroz.sharedpreferences

import android.app.Application
import br.com.joaovitorqueiroz.sharedpreferences.prefs.SharedPreferencesManager

class SharedPreferencesApplication : Application() {
    lateinit var sharedPreferencesManager: SharedPreferencesManager

    override fun onCreate() {
        super.onCreate()
        sharedPreferencesManager = createPreferences()
    }

    private fun createPreferences() = SharedPreferencesManager(applicationContext)
}