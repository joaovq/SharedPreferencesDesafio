package br.com.joaovitorqueiroz.sharedpreferences.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.joaovitorqueiroz.sharedpreferences.SharedPreferencesApplication
import br.com.joaovitorqueiroz.sharedpreferences.closeKeyboard
import br.com.joaovitorqueiroz.sharedpreferences.databinding.ActivityMainBinding
import br.com.joaovitorqueiroz.sharedpreferences.prefs.SharedPreferencesManager
import com.google.android.material.snackbar.Snackbar

const val USER_EXTRA = "User"

class LoginActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var sharedPreferencesManager: SharedPreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        sharedPreferencesManager =
            (application as SharedPreferencesApplication).sharedPreferencesManager

        setListeners()
    }

    private fun setListeners() {
        binding.btnLogin.setOnClickListener {
            authUser()
        }

        binding.tvCreateAccount.setOnClickListener {
            startActivity(Intent(applicationContext, CadastroActivity::class.java))
        }
    }

    private fun authUser() {
        if (binding.etUsername.text.toString().isNotEmpty() &&
            binding.etSenha.text.toString().isNotEmpty()
        ) {
            val username = binding.etUsername.text.toString()
            val password = binding.etSenha.text.toString()
            val user1 =
                sharedPreferencesManager.getUser(username = username, password)
            user1?.let { safeUser ->
                startActivity(
                    Intent(applicationContext, HomeActivity::class.java).putExtra(
                        USER_EXTRA,
                        safeUser,
                    ),
                )
                finish()
            }
            this.closeKeyboard()
            return
        }
        Snackbar
            .make(
                binding.root,
                "Os campos não podem estar vazios",
                Snackbar.LENGTH_SHORT,
            ).show()
        this.closeKeyboard()
    }
}
