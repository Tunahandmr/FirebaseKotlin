package com.tunahan.firebasekotlin.view

import android.content.Intent
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
import com.tunahan.firebasekotlin.R
import com.tunahan.firebasekotlin.adapter.FirebaseAdapter
import com.tunahan.firebasekotlin.databinding.ActivityFeedBinding
import com.tunahan.firebasekotlin.model.Post

class FeedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFeedBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var postArrayList: ArrayList<Post>
    private lateinit var firebaseAdapter: FirebaseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedBinding.inflate(layoutInflater)
        val v = binding.root
        setContentView(v)

        postArrayList = ArrayList<Post>()
        auth = Firebase.auth
        db = Firebase.firestore
        getData()

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        firebaseAdapter = FirebaseAdapter(postArrayList)
        binding.recyclerView.adapter = firebaseAdapter

    }

    private fun getData(){
        db.collection("Posts")
                //only ahmet's posts are visible
            //.whereEqualTo("email","ahmet@gmail.com")
                //DESCENDING or ASCENDING
            .orderBy("date",Query.Direction.DESCENDING)
            .addSnapshotListener { value, error ->

            if (error != null){
                Toast.makeText(this@FeedActivity,error.localizedMessage,Toast.LENGTH_LONG).show()
            }else{
                if (value != null){
                    if (!value.isEmpty){
                        val documents = value.documents

                        // the arraylist is cleared
                        postArrayList.clear()

                        for(document in documents){
                            val comment = document.get("comment") as String
                            val email = document.get("email") as String
                            val downloadUrl = document.get("downloadUrl") as String

                            val post = Post(comment, email, downloadUrl)
                            postArrayList.add(post)


                        }

                        firebaseAdapter.notifyDataSetChanged()

                    }
                }
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.fire_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.add_post){

            val i = Intent(this@FeedActivity, UploadActivity::class.java)
            startActivity(i)
        }else if(item.itemId == R.id.sign_out){
            auth.signOut()
            val i = Intent(this@FeedActivity, MainActivity::class.java)
            startActivity(i)
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}