package com.hozanbaydu.campustime.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.hozanbaydu.campustime.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth     //FirebaseAuth sınıfından auth objesi tanımladık.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportActionBar?.hide()



// Initialize Firebase Auth
        auth = Firebase.auth

        val currentUsers=auth.currentUser  //initialize ettik.

        if (currentUsers!=null){
            val intent=Intent(this@MainActivity, FeedActivity::class.java)   //Bir kere girilmişse tekrar şifre sormamak için.
            startActivity(intent)
            finish()
        }

    }

    fun signInClicked (view: View) {
        val email=binding.emailText.text.toString()
        val password=binding.passwordText.text.toString()

        if (email.equals("") || password.equals("")){

            Toast.makeText(this,"Enter your email and password!!",Toast.LENGTH_LONG).show()

        }else{
            auth.signInWithEmailAndPassword(email,password).addOnSuccessListener {
                val intent=Intent(this@MainActivity, FeedActivity::class.java)
                startActivity(intent)
                finish()

            }.addOnFailureListener {
                Toast.makeText(this@MainActivity,it.localizedMessage,Toast.LENGTH_LONG).show() //localizedMessage Firebasenin kendi hata mesajlarını gostermek için.
            }
        }


    }

    fun signUpClicked (view: View){
        val email=binding.emailText.text.toString()
        val password=binding.passwordText.text.toString()

        if (email.equals("") || password.equals("")){

            Toast.makeText(this,"Enter your email and password!!",Toast.LENGTH_LONG).show()

        }else{
            auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener {
                //addOnSuccessListener işlem başarılı olursa demek.
                val intent=Intent(this@MainActivity, FeedActivity::class.java)
                startActivity(intent)
                finish()

            }.addOnFailureListener {
                Toast.makeText(this@MainActivity,it.localizedMessage,Toast.LENGTH_LONG).show()
            }
        }


    }


}