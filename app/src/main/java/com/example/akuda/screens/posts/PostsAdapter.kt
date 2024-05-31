package com.example.akuda.screens.posts

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.akuda.databinding.ItemPostBinding
import com.example.akuda.model.posts.Post
import java.util.Locale

class PostsAdapter : RecyclerView.Adapter<PostsAdapter.PostHolder>() {

    var posts = mutableListOf<Post>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            postsCopy.clear();
            postsCopy.addAll(value)
            notifyDataSetChanged()
        }


    private var postsCopy = mutableListOf<Post>()

    inner class PostHolder(val binding: ItemPostBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPostBinding.inflate(inflater, parent, false)

        return PostHolder(binding)
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        val post = posts[position]

        Log.d("RRRR", post.toString())

        with(holder.binding) {
            postTitle.text = post.title
            postAuthor.text = post.author
            postCity.text = post.city
            postRating.text = (post.rating.sum() / post.rating.size).toString()
        }

        Glide
            .with(holder.itemView)
            .load(post.image)
            .transform(RoundedCorners(20))
            .into(holder.binding.postImage)
    }

    override fun getItemCount(): Int = posts.size

    fun filterByCity(query: String) {
        val filteredGroups: MutableList<Post> = mutableListOf()

        if (query.isEmpty()) {
            filteredGroups.addAll(postsCopy)
        } else {
            val lowerCaseQuery = query.lowercase(Locale.getDefault())
            for (post in postsCopy) {
                if (post.city.lowercase(Locale.ROOT).contains(lowerCaseQuery)) {
                    filteredGroups.add(post)
                }
            }
        }
        posts.clear()
        posts.addAll(filteredGroups)
        notifyDataSetChanged()
    }
}