package com.example.practica09_almacenamientoconsqlite.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.practica09_almacenamientoconsqlite.R
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {

    private lateinit var loginButton: Button
    private lateinit var exitButton: Button
    private lateinit var savePasswordCheckBox: CheckBox
    private lateinit var usernameInput: TextInputEditText
    private lateinit var passwordInput: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        loginButton = findViewById(R.id.loginButton)
        exitButton = findViewById(R.id.exitButton)
        savePasswordCheckBox = findViewById(R.id.savePasswordCheckBox)
        usernameInput = findViewById(R.id.username_input)
        passwordInput = findViewById(R.id.password_input)

        loadLoginData()

        exitButton.setOnClickListener {
            finish()
        }

        loginButton.setOnClickListener {
            if(inputIsValid()) {
                saveLoginData()
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            }
            else {
                Toast.makeText(
                    this,
                    "Por favor, complete todos los campos solicitados",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun loadLoginData() {
        var username = ""
        var password = ""
        with (getPreferences(Context.MODE_PRIVATE)) {
            if(getBoolean(getString(R.string.save_login_data), false)) {
                username = getString(getString(R.string.login_username), "") ?: ""
                password = getString(getString(R.string.login_password), "") ?: ""
                savePasswordCheckBox.isChecked = true
            }
        }
        usernameInput.setText(username)
        passwordInput.setText(password)
    }

    private fun inputIsValid(): Boolean {
        return !(usernameInput.text.isNullOrEmpty() || passwordInput.text.isNullOrEmpty())
    }

    private fun saveLoginData() {
        if(savePasswordCheckBox.isChecked) {
            // Save password and username to shared preferences
            with(getPreferences(Context.MODE_PRIVATE).edit()) {
                putBoolean(getString(R.string.save_login_data), true)
                putString(getString(R.string.login_username), usernameInput.text.toString())
                putString(getString(R.string.login_password), passwordInput.text.toString())
                apply()
            }
        }
        else {
            with(getPreferences(Context.MODE_PRIVATE).edit()) {
                putBoolean(getString(R.string.save_login_data), false)
                putString(getString(R.string.login_username), "")
                putString(getString(R.string.login_password), "")
                apply()
            }
        }
    }
}