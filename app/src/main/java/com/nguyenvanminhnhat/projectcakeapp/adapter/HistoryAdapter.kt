package com.nguyenvanminhnhat.projectcakeapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nguyenvanminhnhat.projectcakeapp.R
import com.nguyenvanminhnhat.projectcakeapp.pojo.model.CakeModel
import com.nguyenvanminhnhat.projectcakeapp.pojo.model.HistoryModel
import kotlinx.android.synthetic.main.item_cart.view.*
import kotlinx.android.synthetic.main.item_history.view.*
import java.text.DecimalFormat

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.IHistoryVH>() {
    private var items = mutableListOf<HistoryModel>()
    fun setData(items: List<HistoryModel>){
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }
    class IHistoryVH(view: View) : RecyclerView.ViewHolder(view){
        fun bind(data : HistoryModel){
            itemView.apply {
                tvTime.text = data.time
                val dec = DecimalFormat("###,###.###")
                val number = dec.format(data.totalPrice)
                tvTotalPriceCake.text = "$number VNĐ"

                tvQuantityHistory.text = "Số lượng bánh mua là: ${data.count}"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IHistoryVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
        return IHistoryVH(view)
    }

    override fun onBindViewHolder(holder: IHistoryVH, position: Int) {
        items.let { holder.bind(it[position]) }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}