package com.nguyenvanminhnhat.projectcakeapp.adapter

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nguyenvanminhnhat.projectcakeapp.R
import com.nguyenvanminhnhat.projectcakeapp.pojo.model.CategoryModel
import com.nguyenvanminhnhat.projectcakeapp.pojo.model.FavouriteModel
import com.nguyenvanminhnhat.projectcakeapp.pojo.model.PopularCakeModel
import kotlinx.android.synthetic.main.item_favourite.view.*

class FavouriteAdapter(var onLongClickFav : (String) -> Unit) : RecyclerView.Adapter<FavouriteAdapter.IFavouriteVH>() {
    private val items = mutableListOf<FavouriteModel>()

    fun setData(items: List<FavouriteModel>){
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }
    class IFavouriteVH(
        view: View,
        var onLongClickFav: (String) -> Unit
    ) : RecyclerView.ViewHolder(view){
        fun bind(data : FavouriteModel){
            itemView.apply {
                Glide.with(ivFav)
                    .load(Uri.parse(data.imgCake))
                    .error(R.mipmap.ic_launcher)
                    .into(ivFav)

                tvNameFav.text = data.nameCake.toString()
                Log.d("axy", "${data.imgCake}")

                setOnLongClickListener {
                    data.nameCake?.let { it1 -> onLongClickFav.invoke(it1) }
                    return@setOnLongClickListener true
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IFavouriteVH {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_favourite, parent, false)
        var layoutParam = view.layoutParams
        layoutParam.width = parent.width * 2 / 5
        view.layoutParams = layoutParam
        return IFavouriteVH(view, onLongClickFav)
    }

    override fun onBindViewHolder(holder: IFavouriteVH, position: Int) {
        items.let { holder.bind(it[position]) }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}

