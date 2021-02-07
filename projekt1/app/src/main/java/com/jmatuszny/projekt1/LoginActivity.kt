package com.jmatuszny.projekt1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val auth = FirebaseAuth.getInstance()

        loginRegisterButton.setOnClickListener {

            auth.signInWithEmailAndPassword(loginEditText.text.toString(), passwordEditText.text.toString())
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(this, "Zalogowano.", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, MainActivity::class.java).also {
                                it.putExtra("user", auth.currentUser?.email)
                            })
                        } else {
                            val e = it.exception
                            Toast.makeText(this, e?.message, Toast.LENGTH_LONG).show()
                        }
                    }
        }

        loginRegisterButton.setOnLongClickListener {

            auth.createUserWithEmailAndPassword(loginEditText.text.toString(), passwordEditText.text.toString())
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(this, "Rejestracja przebiegła prawidłowo.", Toast.LENGTH_SHORT).show()
                        } else {
                            val e = it.exception
                            Toast.makeText(this, e?.message, Toast.LENGTH_LONG).show()
                        }
                    }

            true
        }
    }
}