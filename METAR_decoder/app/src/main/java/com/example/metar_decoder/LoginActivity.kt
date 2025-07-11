package com.example.metar_decoder

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

/**
 * Aktywność logowania użytkownika.
 *
 * Obsługuje logowanie przez Firebase Authentication. W przypadku udanego logowania przechodzi do ekranu głównego,
 * w przeciwnym razie pokazuje komunikat o błędzie. Umożliwia też przejście do ekranu rejestracji.
 */
class LoginActivity : AppCompatActivity() {
    /** Instancja FirebaseAuth do obsługi uwierzytelniania użytkowników. */
    private lateinit var auth: FirebaseAuth

    /**
     * Wywoływane przy utworzeniu aktywności. Inicjalizuje widok, pola oraz ustawia obsługę przycisków.
     *
     * @param savedInstanceState Stan zapisany aktywności, jeśli taki istnieje.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)
        val loginButton = findViewById<Button>(R.id.loginButton)
        val goToRegisterButton = findViewById<Button>(R.id.goToRegisterButton)

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Proszę wypełnić wszystkie pola", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    Toast.makeText(this, "Zalogowano!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Błąd logowania: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
                }
        }

        goToRegisterButton.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}
