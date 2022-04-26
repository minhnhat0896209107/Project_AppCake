package com.nguyenvanminhnhat.projectcakeapp.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nguyenvanminhnhat.projectcakeapp.R
import com.nguyenvanminhnhat.projectcakeapp.pojo.model.CartModel
import com.nguyenvanminhnhat.projectcakeapp.pojo.model.CategoryModel
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.item_list_category.view.*
import java.text.DecimalFormat

class ListCategoryAdapter(
    val onClickFavourite: (CategoryModel) -> Unit,
    val onClickAddCart : (CategoryModel) -> Unit)
    :RecyclerView.Adapter<ListCategoryAdapter.IListCategoryVH>(), Filterable {
    private var items = mutableListOf<CategoryModel>()
    private var itemsOld = mutableListOf<CategoryModel>()

    @SuppressLint("NotifyDataSetChanged")
    fun     setData(items: List<CategoryModel>) {
        this.items.clear()
        this.items.addAll(items)
        this.itemsOld.addAll(items)
        notifyDataSetChanged()
    }

    class IListCategoryVH(
        view: View,
        var onClickFavourite: (CategoryModel) -> Unit,
        var onClickAddCart : (CategoryModel) -> Unit)
        :RecyclerView.ViewHolder(view) {
        @SuppressLint("SetTextI18n")
        fun bind(data: CategoryModel) {
            var isCheck = false
            itemView.apply {
                Glide.with(ciCategory)
                    .load(Uri.parse("${data.imageCategory}"))
                    .error(R.mipmap.ic_launcher)
                    .into(ciCategory)


                tvNameCategory.text = data.nameCategory
                val dec = DecimalFormat("###,###.###")
                val number = dec.format(data.priceCategory)
                tvPrice.text = number + " VNĐ"
                ivFavourite.setOnClickListener {
                    onClickFavourite.invoke(data)
                    if (!isCheck) {
                        ivFavourite.setImageResource(
                            R.drawable.ic_favorite_fill_blue
                        )
                    }
                }
                tvAddCart.setOnClickListener {
                    onClickAddCart.invoke(data)
                    tvAddCart.setBackgroundColor(Color.GRAY)
                }
            }

        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListCategoryAdapter.IListCategoryVH {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_list_category, parent, false)
        return IListCategoryVH(view, onClickFavourite, onClickAddCart)
    }

    override fun onBindViewHolder(holder: IListCategoryVH, position: Int) {
        items.let { holder.bind(it[position]) }
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
                    val list = mutableListOf<CategoryModel>()
                    for (item: CategoryModel in itemsOld) {
                        if (item.nameCategory?.lowercase()?.contains(search.lowercase()) == true) {
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
                items = results?.values as MutableList<CategoryModel>
                notifyDataSetChanged()
            }
        }
    }
}