package com.garudpuran.kyachalrahahai.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.garudpuran.kyachalrahahai.Activities.SecondActivity
import com.garudpuran.kyachalrahahai.Activities.ThirdActivity
import com.garudpuran.kyachalrahahai.Models.Articles
import com.garudpuran.kyachalrahahai.R
import com.squareup.picasso.Picasso

class NewsRvAdapter(
    private val context: Context,
    private val dataset: List<Articles>
) : RecyclerView.Adapter<NewsRvAdapter.ItemViewHolder>() {


    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgView: ImageView = view.findViewById(R.id.news_pic)
        val titleTv:TextView = view.findViewById(R.id.news_title)
        val timeTv:TextView = view.findViewById(R.id.timeTv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.news_rcview_sample, parent, false)
        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        Picasso.get().load(item.urlToImage).into(holder.imgView)
        holder.titleTv.text = item.title
        holder.timeTv.text = item.publishedAt?.dropLast(10)

        val intent = Intent(context, ThirdActivity::class.java)
        intent.putExtra("title", item
            .title.toString())
        intent.putExtra("description",item.description.toString())
        intent.putExtra("urlToImage",item.urlToImage.toString())
        intent.putExtra("urlNews",item.url
            .toString())
        intent.putExtra("content",item
            .content.toString())
        intent.putExtra("publishedAt",item
            .publishedAt.toString())
        intent.putExtra("author",item.author.toString())

        holder.itemView.setOnClickListener {
            startActivity(context,intent,null)
        }
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}