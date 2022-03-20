package com.nguyenvanminhnhat.projectcakeapp.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nguyenvanminhnhat.projectcakeapp.R
import com.nguyenvanminhnhat.projectcakeapp.pojo.model.ImageModel
import com.nguyenvanminhnhat.projectcakeapp.pojo.model.PopularCakeModel
import kotlinx.android.synthetic.main.item_popular_cake.view.*

class PopularCakeAdapter(var onClickPopular : (PopularCakeModel) -> Unit)  : RecyclerView.Adapter<PopularCakeAdapter.IPopularCakeVH>() {

    private var items = mutableListOf<PopularCakeModel>()

    fun setPopularCake(items: List<PopularCakeModel>){
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    class IPopularCakeVH(view: View, var onClickPopular: (PopularCakeModel) -> Unit) : RecyclerView.ViewHolder(view){
        fun bind(data: PopularCakeModel){
            itemView.apply {
                Glide.with(ivPopular)
                    .load(Uri.parse(data.imageCake))
                    .error(R.mipmap.ic_launcher)
                    .into(ivPopular)
            }

            itemView.setOnClickListener {
                onClickPopular.invoke(data)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IPopularCakeVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_popular_cake,
            parent ,false)
        var layoutParam = view.layoutParams
        layoutParam.width = parent.width * 2 / 3
        view.layoutParams = layoutParam
        return IPopularCakeVH(view, onClickPopular)
    }

    override fun onBindViewHolder(holder: IPopularCakeVH, position: Int) {
        items.let { holder.bind(it[position]) }
    }

    override fun getItemCount(): Int {
        return items.size
    }


}