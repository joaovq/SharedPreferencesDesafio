package br.com.joaovitorqueiroz.sharedpreferences

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.joaovitorqueiroz.sharedpreferences.databinding.ActivityCadastroBinding
import br.com.joaovitorqueiroz.sharedpreferences.prefs.SharedPreferencesManager
import br.com.joaovitorqueiroz.sharedpreferences.prefs.User

class CadastroActivity : AppCompatActivity() {

    private lateinit var sharedPreferencesManager: SharedPreferencesManager
    private val binding by lazy {
        ActivityCadastroBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        sharedPreferencesManager = SharedPreferencesManager(this)

        binding.btnSalvar.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()
            val email = binding.etEmail.text.toString()
            val age = binding.etAge.text.toString().toInt()
            val cpf = binding.etCpf.text.toString()

            val user = User(username, password, email, age, cpf)
            sharedPreferencesManager.saveUser(user)

            finish()
        }
    }
}
