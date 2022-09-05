package com.tunahan.firebasekotlin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.tunahan.firebasekotlin.databinding.RecyclerRowBinding
import com.tunahan.firebasekotlin.model.Post

class FirebaseAdapter(val postList: ArrayList<Post>): RecyclerView.Adapter<FirebaseAdapter.FireHolder>() {

    class FireHolder(val recyclerRowBinding: RecyclerRowBinding): RecyclerView.ViewHolder(recyclerRowBinding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FireHolder {
        val recyclerRowBinding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FireHolder(recyclerRowBinding)
    }

    override fun onBindViewHolder(holder: FireHolder, position: Int) {
        holder.recyclerRowBinding.recyclerEmailText.text = postList.get(position).email
        holder.recyclerRowBinding.recyclerCommentText.text = postList.get(position).comment
        //get image -> picasso library
        Picasso.get().load(postList.get(position).downloadUrl).into(holder.recyclerRowBinding.recyclerImageView)
    }

    override fun getItemCount(): Int {

        return postList.size
    }
}