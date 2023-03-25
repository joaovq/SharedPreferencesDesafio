package br.com.joaovitorqueiroz.sharedpreferences.prefs

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import br.com.joaovitorqueiroz.sharedpreferences.model.User

class SharedPreferencesManager(private val context: Context) {

    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(
            "MyPrefs",
            Context.MODE_PRIVATE,
        )
    }

    private val mainKeyAlias by lazy {
        val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
        MasterKeys.getOrCreate(keyGenParameterSpec)
    }

    private val encryptedSharedPrefs by lazy {
        val sharedPrefsFile = "EncryptPref"

        EncryptedSharedPreferences.create(
            sharedPrefsFile,
            mainKeyAlias,
            context.applicationContext,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM,
        )
    }

    fun saveUser(user: User) {
        encryptedSharedPrefs.edit {
            putString(KEY_USERNAME, user.username)
            putString(KEY_PASSWORD, user.password)
        }
        sharedPreferences.edit {
            putString(KEY_EMAIL, user.email)
            putInt(KEY_AGE, user.age)
            putString(KEY_CPF, user.cpf)
        }
    }

    fun getUser(username: String, password: String): User? {
        val savedUsername = encryptedSharedPrefs.getString(KEY_USERNAME, "")
        val savedPassword = encryptedSharedPrefs.getString(KEY_PASSWORD, "")
        Log.e("usernmae", savedUsername.toString())
        Log.e("password", savedPassword.toString())

        return if (savedUsername == username && savedPassword == password) {
            val email = sharedPreferences.getString(KEY_EMAIL, "")
            val age = sharedPreferences.getInt(KEY_AGE, 0)
            val cpf = sharedPreferences.getString(KEY_CPF, "")

            User(savedUsername, savedPassword, email!!, age, cpf!!)
        } else {
            null
        }
    }

    companion object {
        private const val KEY_USERNAME = "username"
        private const val KEY_PASSWORD = "password"
        private const val KEY_EMAIL = "email"
        private const val KEY_AGE = "age"
        private const val KEY_CPF = "cpf"
    }
}
