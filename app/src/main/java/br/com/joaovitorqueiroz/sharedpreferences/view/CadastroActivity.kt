package br.com.joaovitorqueiroz.sharedpreferences.view

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import br.com.joaovitorqueiroz.sharedpreferences.SharedPreferencesApplication
import br.com.joaovitorqueiroz.sharedpreferences.databinding.ActivityCadastroBinding
import br.com.joaovitorqueiroz.sharedpreferences.model.User
import br.com.joaovitorqueiroz.sharedpreferences.prefs.SharedPreferencesManager
import br.com.joaovitorqueiroz.sharedpreferences.validate
import com.google.android.material.snackbar.Snackbar

class CadastroActivity : AppCompatActivity() {

    private lateinit var sharedPreferencesManager: SharedPreferencesManager
    private val binding by lazy {
        ActivityCadastroBinding.inflate(layoutInflater)
    }
    private lateinit var etCpf: EditText
    private lateinit var etUsername: EditText
    private lateinit var etEmail: EditText
    private lateinit var etAge: EditText
    private lateinit var etPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        sharedPreferencesManager =
            (application as SharedPreferencesApplication).sharedPreferencesManager
        initViews()
        setListeners()
    }

    private fun initViews() {
        etCpf = binding.etCpf
        etUsername = binding.etUsername
        etEmail = binding.etEmail
        etAge = binding.etAge
        etPassword = binding.etPassword
    }

    private fun setListeners() {
        binding.btnSalvar.setOnClickListener {
            if (isValidForms()) {
                val user = getUserInstance()
                sharedPreferencesManager.saveUser(user)
                Snackbar
                    .make(binding.root, "Cadastro feito com sucesso!!", Snackbar.LENGTH_SHORT)
                    .show()
                finish()
            } else {
                val snackbar = Snackbar.make(
                    it,
                    "Todos os campos devem ser preenchidos",
                    Snackbar.LENGTH_SHORT
                )
                snackbar.setAction("Ok") {
                    snackbar.dismiss()
                }
                snackbar.show()
            }
        }
    }

    private fun isValidForms(): Boolean =
        etCpf.text.toString().validate() && etUsername.text.toString().validate() &&
            etPassword.text.toString().validate() &&
            etEmail.text.toString().validate() &&
            etAge.text.toString().validate()

    private fun getUserInstance(): User {
        val username = etUsername.text.toString()
        val password = etPassword.text.toString()
        val email = etEmail.text.toString()
        val age = etAge.text.toString().toInt()
        val cpf = etCpf.text.toString()
        return User(username, password, email, age, cpf)
    }
}
