package com.hozanbaydu.campustime.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hozanbaydu.campustime.R
import com.hozanbaydu.campustime.adapter.FeedRecyclerAdapter
import com.hozanbaydu.campustime.databinding.ActivityFeedBinding
import com.hozanbaydu.campustime.model.Post

class FeedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFeedBinding
    private lateinit var auth:FirebaseAuth
    private lateinit var db:FirebaseFirestore
    private lateinit var postArrayList:ArrayList<Post>
    private lateinit var feedAdapter:FeedRecyclerAdapter
    lateinit var sharedPreferences: SharedPreferences



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        auth=Firebase.auth
        db=Firebase.firestore
        postArrayList=ArrayList<Post>()





    }
    private fun getData(uni:String){


        db.collection(uni).orderBy("date",Query.Direction.DESCENDING).addSnapshotListener { value, error ->
            if (error!=null){
                Toast.makeText(this,error.localizedMessage,Toast.LENGTH_LONG).show()

            }else{
                if (value!=null){
                    if(!value.isEmpty){

                        val documents=value.documents

                        postArrayList.clear()

                        for (document in documents){

                            val comment=document.get("comment") as String
                            val userEmail=document.get("userEmail") as String
                            val dowloadUrl=document.get("dowlandUrl") as String
                            val post=Post(userEmail,comment,dowloadUrl)
                            postArrayList.add(post)
                        }
                        feedAdapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuInflater=menuInflater
        menuInflater.inflate(R.menu.feed_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId== R.id.add_post){
            val intent=Intent(this, UploadActivity::class.java)
            startActivity(intent)
        }else if (item.itemId== R.id.signout){
           auth.signOut()
            val intent=Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }else if (item.itemId==R.id.odtu){


            getData("odtu")
            binding.recyclerView.layoutManager=LinearLayoutManager(this)
            feedAdapter= FeedRecyclerAdapter(postArrayList)
            binding.recyclerView.adapter=feedAdapter
            getSupportActionBar()?.setTitle("Orta Doğu Teknik Üniversitesi")
            getSupportActionBar()?.setBackgroundDrawable( ColorDrawable(Color.parseColor("#1c6071")))

        }
        else if (item.itemId==R.id.ytu){

            getData("ytu")
            binding.recyclerView.layoutManager=LinearLayoutManager(this)
            feedAdapter= FeedRecyclerAdapter(postArrayList)
            binding.recyclerView.adapter=feedAdapter
            getSupportActionBar()?.setTitle("Yıldız Teknik Üniversitesi")
            getSupportActionBar()?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#06023b")))

        }
        else if (item.itemId==R.id.itu){


            getData("itu")
            binding.recyclerView.layoutManager=LinearLayoutManager(this)
            feedAdapter= FeedRecyclerAdapter(postArrayList)
            binding.recyclerView.adapter=feedAdapter
            getSupportActionBar()?.setTitle("İstanbul Teknik Üniversitesi")
            getSupportActionBar()?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#b8860b")))




        }
        return super.onOptionsItemSelected(item)
    }
}