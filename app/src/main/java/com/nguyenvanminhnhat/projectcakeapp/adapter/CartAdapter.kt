package com.nguyenvanminhnhat.projectcakeapp.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nguyenvanminhnhat.projectcakeapp.R
import com.nguyenvanminhnhat.projectcakeapp.pojo.model.CartModel
import kotlinx.android.synthetic.main.item_cart.view.*

class CartAdapter : RecyclerView.Adapter<CartAdapter.ICartVH>(){
    private var items = mutableListOf<CartModel>()

    fun setData(items: List<CartModel>){
        this.items.addAll(items)
        notifyDataSetChanged()
    }
    class ICartVH(view: View) : RecyclerView.ViewHolder(view){
        fun bind(data: CartModel){
            itemView.apply {
                Glide.with(ciImageCake)
                    .load(Uri.parse(data.imageCake))
                    .error(R.drawable.default_image)
                    .into(ciImageCake)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ICartVH {
       var view = LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
       return ICartVH(view)
    }

    override fun onBindViewHolder(holder: ICartVH, position: Int) {
        items.let { holder.bind(items[position]) }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}