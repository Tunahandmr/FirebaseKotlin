package com.tunahan.firebasekotlin.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.tunahan.firebasekotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val v = binding.root
        setContentView(v)
        // Initialize Firebase Auth
        auth = Firebase.auth
        val currentUser = auth.currentUser

        if (currentUser != null){
            val i = Intent(this@MainActivity, FeedActivity::class.java)
            startActivity(i)
            finish()
        }


    }


    fun inClick(view: View){

        val email = binding.emailText.text.toString()
        val password = binding.passwordText.text.toString()

        if (email == "" || password == ""){
            Toast.makeText(this@MainActivity,"Enter email and password!!",Toast.LENGTH_LONG).show()
        }else{
            auth.signInWithEmailAndPassword(email,password).addOnSuccessListener {

                val i = Intent(this@MainActivity, FeedActivity::class.java)
                startActivity(i)
                finish()

            }.addOnFailureListener{
                Toast.makeText(this@MainActivity,it.localizedMessage,Toast.LENGTH_LONG).show()
            }

        }
    }

    fun upClick(view: View){

        val email = binding.emailText.text.toString()
        val password = binding.passwordText.text.toString()

        if (email == "" || password == ""){
            Toast.makeText(this@MainActivity,"Enter email and password!!",Toast.LENGTH_LONG).show()
        }else{
            auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener {

                val i = Intent(this@MainActivity, FeedActivity::class.java)
                startActivity(i)
                finish()

            }.addOnFailureListener{
                Toast.makeText(this@MainActivity,it.localizedMessage,Toast.LENGTH_LONG).show()
            }

        }
    }
}