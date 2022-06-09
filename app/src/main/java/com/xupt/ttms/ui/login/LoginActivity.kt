package com.xupt.ttms.ui.login

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import com.xupt.ttms.MainActivity
import com.xupt.ttms.databinding.ActivityLoginBinding
import com.xupt.ttms.util.retrofit.RetrofitManager

import com.xupt.ttms.util.tool.TimeCountUtil
import com.xupt.ttms.util.tool.ToastUtil

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = binding.username
        val password = binding.password
        val login = binding.login
        val loading = binding.loading
        val toolbar = binding.loginToolbar
        val code = binding.code

        RetrofitManager.context = this

        loginViewModel = ViewModelProvider(this, LoginViewModelFactory())[LoginViewModel::class.java]

        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            login.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                password.error = getString(loginState.passwordError)
            }
        })

        setSupportActionBar(toolbar)

        loginViewModel.loginResult.observe(this@LoginActivity, Observer {
            it ?: return@Observer

            loading.visibility = View.GONE

            if (it) {
                setResult(Activity.RESULT_OK)
                savePhone()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                ToastUtil.getToast(this@LoginActivity, "登陆失败")
            }
        })

        username.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    username.text.toString(),
                    password.text.toString()
                )
            }

            filters = arrayOf(InputFilter.LengthFilter(11))
        }

        password.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    username.text.toString(),
                    password.text.toString()
                )
            }

            filters = arrayOf(InputFilter.LengthFilter(6))

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        loginViewModel.login(
                            username.text.toString(),
                            password.text.toString()
                        )
                }
                false
            }

            code?.setOnClickListener {
                Log.d("TAG", "onCreate: ")
                loginViewModel.getCode(username.text.toString())

            }

            loginViewModel.codeResult.observe(this@LoginActivity){
                if (it) {
                    val buttonTime = TimeCountUtil(
                        60000L,
                        1000L,
                        this@LoginActivity,
                        code
                    ).start()
                }
            }

            login.setOnClickListener {
                loading.visibility = View.VISIBLE
                loginViewModel.login(username.text.toString(), password.text.toString())
            }
        }
    }

    private fun savePhone() {
        getSharedPreferences("user", MODE_PRIVATE).edit().putString("phone", binding.username.text.toString()).apply()
    }

}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}