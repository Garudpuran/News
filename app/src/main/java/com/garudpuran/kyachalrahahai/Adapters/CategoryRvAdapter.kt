package com.garudpuran.kyachalrahahai.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.garudpuran.kyachalrahahai.Models.CategoryRvModel
import com.garudpuran.kyachalrahahai.R

class CategoryRvAdapter (private val context: Context,
private val dataset: List<CategoryRvModel>, val cInt:CategoryClickInterface
) : RecyclerView.Adapter<CategoryRvAdapter.ItemViewHolder>() {

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTv: TextView = view.findViewById(R.id.categoryTitleTv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryRvAdapter.ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.news_category_rcview_sample, parent, false)
        return CategoryRvAdapter.ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: CategoryRvAdapter.ItemViewHolder, position: Int) {
        val item = dataset[position]
        holder.titleTv.text = item.category.toString()
        holder.itemView.setOnClickListener { cInt.onCategoryClick(position)
        }
    }

    override fun getItemCount(): Int {
        return  dataset.size
    }

    interface CategoryClickInterface{
        fun onCategoryClick(position:Int) {

        }
    }
}