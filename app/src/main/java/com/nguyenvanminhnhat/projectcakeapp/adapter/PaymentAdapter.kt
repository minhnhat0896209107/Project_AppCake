package com.nguyenvanminhnhat.projectcakeapp.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nguyenvanminhnhat.projectcakeapp.R
import com.nguyenvanminhnhat.projectcakeapp.pojo.model.CartModel
import com.nguyenvanminhnhat.projectcakeapp.pojo.model.PaymentModel
import kotlinx.android.synthetic.main.item_cart.view.*
import java.text.DecimalFormat

class PaymentAdapter  : RecyclerView.Adapter<PaymentAdapter.IPaymentVH>() {

    private var items = mutableListOf<CartModel>()

    fun setData(items: List<CartModel>){
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    class IPaymentVH(view: View) : RecyclerView.ViewHolder(view){
        fun bind(data : CartModel){
            itemView.apply {
                Glide.with(ciImageCake)
                    .load(Uri.parse(data.imageCake))
                    .error(R.drawable.default_image)
                    .into(ciImageCake)
                tvNameCake.text = data.nameCake
                val dec = DecimalFormat("###,###.###")
                val number = dec.format(data.priceCake)
                tvPriceCake.text = number + " VNĐ"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IPaymentVH {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_payment, parent, false)
        return IPaymentVH(view)
    }

    override fun onBindViewHolder(holder: IPaymentVH, position: Int) {
        items.let { holder.bind(items[position]) }
    }

    override fun getItemCount(): Int {
        return this.items.size
    }
}