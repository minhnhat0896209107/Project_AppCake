package com.nguyenvanminhnhat.projectcakeapp.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nguyenvanminhnhat.projectcakeapp.R
import com.nguyenvanminhnhat.projectcakeapp.pojo.model.BlogModel
import kotlinx.android.synthetic.main.item_blog.view.*

class BlogAdapter : RecyclerView.Adapter<BlogAdapter.IBlogVH>(){

    private var items = mutableListOf<BlogModel>()

    fun setData(items: List<BlogModel>){
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    class IBlogVH(view: View) : RecyclerView.ViewHolder(view){
        fun bind(data : BlogModel){
            itemView.apply {
                tvUserNameBlog.text = data.nameUser.toString()
                tvTitleBlog.text = data.titleBlog.toString()
                tvDescriptionBlog.text = data.descriptionBlog
                Glide.with(ivBlog)
                    .load(Uri.parse("${data.imgBlog}"))
                    .error(R.drawable.default_image)
                    .into(ivBlog)
                tvLike.text = "${data.countLike} lượt yêu thích"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IBlogVH {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_blog, parent, false)
        return IBlogVH(view)
    }

    override fun onBindViewHolder(holder: IBlogVH, position: Int) {
        items.let { holder.bind(it[position]) }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}