package com.nguyenvanminhnhat.projectcakeapp.adapter

import android.annotation.SuppressLint
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nguyenvanminhnhat.projectcakeapp.R
import com.nguyenvanminhnhat.projectcakeapp.pojo.model.CategoryModel
import kotlinx.android.synthetic.main.item_category.view.*

class CategoryAdapter : RecyclerView.Adapter<CategoryAdapter.ICategoryVH>(){
    private val items = mutableListOf<CategoryModel>()

    fun setDataCategory(items : List<CategoryModel>){
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }
    class ICategoryVH(view: View) : RecyclerView.ViewHolder(view){
        fun bind(data: CategoryModel){
            itemView.apply {
                Glide.with(ivCategory)
                    .load(Uri.parse(data.imageCategory))
                    .error(R.mipmap.ic_launcher)
                    .into(ivCategory)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ICategoryVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return ICategoryVH(view)
    }

    override fun onBindViewHolder(holder: ICategoryVH, position: Int) {
        items.let { holder.bind(it[position]) }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}