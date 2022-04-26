package com.nguyenvanminhnhat.projectcakeapp.adapter

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nguyenvanminhnhat.projectcakeapp.R
import com.nguyenvanminhnhat.projectcakeapp.pojo.model.CartModel
import com.nguyenvanminhnhat.projectcakeapp.pojo.model.CategoryModel
import kotlinx.android.synthetic.main.item_cart.view.*
import kotlinx.android.synthetic.main.item_list_category.view.*
import java.text.DecimalFormat


class CartAdapter (var onClickQuantity : (Long) -> Unit):
    RecyclerView.Adapter<CartAdapter.ICartVH>(){
    private var items = mutableListOf<CartModel>()
    var totalPrice : Long = 0
    fun setData(items: List<CartModel>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    class ICartVH(
        view: View,
        var onClickQuantity : (Long) -> Unit
    ) : RecyclerView.ViewHolder(view) {
        var quantity : Long = 0
        fun bind(data: CartModel) {
            var count : Long = 1
            itemView.apply {
                Glide.with(ciImageCake)
                    .load(Uri.parse(data.imageCake))
                    .error(R.drawable.default_image)
                    .into(ciImageCake)
                tvNameCake.text = data.nameCake
                val dec = DecimalFormat("###,###.###")
                val number = dec.format(data.priceCake)
                tvPriceCake.text = number + " VNĐ"

                ivPlus.setOnClickListener {
                    count++
                    tvQuantity.text = "$count"
                    quantity = count
                    Log.d("count", "$quantity")
                }
                ivMinus.setOnClickListener {
                    if(count < 1)
                        count = 0
                    else
                        count--
                    tvQuantity.text = "$count"
                    quantity = count
                    Log.d("count", "$quantity")
                }
                onClickQuantity.invoke(quantity)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ICartVH {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        return ICartVH(view, onClickQuantity)
    }

    override fun onBindViewHolder(holder: ICartVH, position: Int) {
        items.let { holder.bind(items[position])
            Log.d("priceCake", "${items[position].priceCake}")
            totalPrice += (items[position].priceCake).toLong()
            Log.d("totalPrice", "$totalPrice - ${holder.bind(items[position])}")
            onClickQuantity.invoke(totalPrice)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
    interface ICount {
        fun onCount(count: Long)
    }
}