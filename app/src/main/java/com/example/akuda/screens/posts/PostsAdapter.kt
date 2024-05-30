package com.example.akuda.screens.posts

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.akuda.databinding.ItemPostBinding
import com.example.akuda.model.posts.Post

class PostsAdapter : RecyclerView.Adapter<PostsAdapter.PostHolder>() {

    var posts = emptyList<Post>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class PostHolder(val binding: ItemPostBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPostBinding.inflate(inflater, parent, false)

        return PostHolder(binding)
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        val binging = holder.binding
        val post = posts[position]

        binging.apply {
            postTitle.text = post.title
            postAuthor.text = post.author
            postCity.text = post.city
            postRating.text = (post.rating.sum() / post.rating.size).toString()
        }

        Glide
            .with(holder.itemView)
            .load(post.image)
            .transform(RoundedCorners(20))
            .into(binging.postImage)
    }

    override fun getItemCount(): Int = posts.size



}