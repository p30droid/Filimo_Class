package com.navin.filimo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.navin.filimo.R
import com.navin.filimo.models.Category
import com.squareup.picasso.Picasso

class CategoryAdapter(mContext: Context, data: MutableList<Category>) :
    RecyclerView.Adapter<CategoryAdapter.CategoryVH>() {
    val context: Context = mContext
    val categoryList = data


    class CategoryVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val img_category = itemView.findViewById<AppCompatImageView>(R.id.img_category)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryVH {

        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.category_row, null)
        return CategoryVH(view)
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: CategoryVH, position: Int) {

        Picasso.get().load(categoryList.get(position).categoryImage).into(holder.img_category)

    }
}