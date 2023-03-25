package br.com.joaovitorqueiroz.sharedpreferences.view

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.joaovitorqueiroz.sharedpreferences.databinding.ActivityHomeBinding
import br.com.joaovitorqueiroz.sharedpreferences.model.User
import br.com.joaovitorqueiroz.sharedpreferences.prefs.SharedPreferencesManager
import java.util.*

class HomeActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
    }
    private lateinit var sharedPreferencesManager: SharedPreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        sharedPreferencesManager = SharedPreferencesManager(this)

        val parcelableExtra = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(USER_EXTRA, User::class.java)
        } else {
            intent.getParcelableExtra(USER_EXTRA)
        }

        parcelableExtra?.let { user ->
            val user1 =
                sharedPreferencesManager.getUser(username = user.username!!, user.password!!)
            binding.tvPassword.text = user1?.password
            binding.tvEmail.text = user1?.email
            binding.tvAge.text = user1?.age.toString()
            binding.tvUsername.text = user1?.username.toString()
            binding.tvCpf.text = user1?.cpf.toString()
        }
        binding.btnSalvar.setOnClickListener {
            val text = binding.etNotes.text.toString()
            if (text.isNotEmpty()) {
                saveNoteInFile(text)
            }
        }
    }

    private fun saveNoteInFile(text: String) {
        val openFileOutput = openFileOutput("${UUID.randomUUID()}", MODE_APPEND)
        openFileOutput.use { stream ->
            stream.write(text.toByteArray())
        }
    }
}
