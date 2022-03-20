package com.nguyenvanminhnhat.projectcakeapp.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nguyenvanminhnhat.projectcakeapp.R
import com.nguyenvanminhnhat.projectcakeapp.pojo.model.Review
import kotlinx.android.synthetic.main.item_review.view.*

class ReviewAdapter : RecyclerView.Adapter<ReviewAdapter.IReviewVH>() {

    private var items = mutableListOf<String>()

    fun setData(items: Review?){
        this.items = items?.rv as MutableList<String>
        notifyDataSetChanged()
    }

    class IReviewVH(view: View) : RecyclerView.ViewHolder(view){
        fun bind(data: String, count: Int){
            itemView.apply {
                when(count){
                    0 -> {
                        ciReview.setImageResource(R.drawable.rv01)
                        tvTitleRv.text = "Nathan Nguyen"


                    }
                    1 -> {
                        ciReview.setImageResource(R.drawable.rv02)
                        tvTitleRv.text = "Joseph"


                    }
                    2 -> {
                        ciReview.setImageResource(R.drawable.rv03)
                        tvTitleRv.text = "Samuel"

                    }
                    3 -> {
                        ciReview.setImageResource(R.drawable.rv04)
                        tvTitleRv.text = "Isaiah"

                    }
                    else -> {
                        ciReview.setImageResource(R.drawable.rv05)
                        tvTitleRv.text = "Daniel"

                    }

                }
                tvDescripRv.text = data

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IReviewVH {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_review, parent, false)
        return IReviewVH(view)
    }


    override fun onBindViewHolder(holder: IReviewVH, position: Int) {
        items.let { holder.bind(it[position], position) }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}