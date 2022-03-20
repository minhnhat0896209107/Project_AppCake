package com.nguyenvanminhnhat.projectcakeapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.nguyenvanminhnhat.projectcakeapp.R
import com.nguyenvanminhnhat.projectcakeapp.pojo.model.CategoryModel
import com.nguyenvanminhnhat.projectcakeapp.pojo.model.UserModel
import kotlinx.android.synthetic.main.item_review.view.*
import kotlinx.android.synthetic.main.item_search_user.view.*

class UserAdapter (isChatCheck: Boolean) : RecyclerView.Adapter<UserAdapter.IUserVH>(), Filterable{
    private val isChatCheck: Boolean = isChatCheck
    private var items = mutableListOf<UserModel>()
    private var itemsOld = mutableListOf<UserModel>()

    fun setData(items : List<UserModel>){
        this.items.clear()
        this.items.addAll(items)
        this.itemsOld.addAll(items)
        notifyDataSetChanged()
    }

    class IUserVH(view: View) : RecyclerView.ViewHolder(view){
        fun bind(data : List<UserModel>, position: Int){
            itemView.apply {
                val user : UserModel = data[position]
                tvUserName.text = user.username
                when (position) {
                    0 -> {
                        ciProfile.setImageResource(R.drawable.rv01)
                    }
                    1 -> {
                        ciProfile.setImageResource(R.drawable.rv02)
                    }
                    2 -> {
                        ciProfile.setImageResource(R.drawable.rv03)
                    }
                    3 -> {
                        ciProfile.setImageResource(R.drawable.rv04)
                    }
                    else -> {
                        ciProfile.setImageResource(R.drawable.rv05)
                    }

                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IUserVH {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_search_user, parent, false)
        return IUserVH(view)
    }

    override fun onBindViewHolder(holder: IUserVH, position: Int) {
        items.let { holder.bind(items, position) }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val search: String = constraint.toString()
                items = if (search.isEmpty()) {
                    itemsOld
                } else {
                    val list = mutableListOf<UserModel>()
                    for (item: UserModel in itemsOld) {
                        if (item.username?.lowercase()?.contains(search.lowercase()) == true) {
                            list.add(item)
                        }
                    }
                    list
                }
                val filterResult = FilterResults()
                filterResult.values = items
                return filterResult
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                items = results?.values as MutableList<UserModel>
                notifyDataSetChanged()
            }
        }
    }
}