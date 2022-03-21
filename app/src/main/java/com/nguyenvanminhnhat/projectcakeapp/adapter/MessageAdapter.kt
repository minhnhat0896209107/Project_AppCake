package com.nguyenvanminhnhat.projectcakeapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.nguyenvanminhnhat.projectcakeapp.R
import com.nguyenvanminhnhat.projectcakeapp.pojo.model.MessageModel
import com.nguyenvanminhnhat.projectcakeapp.view.chat.message.MessageViewModel
import kotlinx.android.synthetic.main.item_right.view.*

class MessageAdapter(var onLongDelete: (String) -> Unit) : RecyclerView.Adapter<MessageAdapter.IMessageVH>() {
    private var items = mutableListOf<MessageModel>()
    private val MESSAGE_TYPE_LEFT = 0
    private val MESSAGE_TYPE_RIGHT = 1
    var firebaseUser: FirebaseUser? = null

    fun setData(items : List<MessageModel>){
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }
    class IMessageVH(
        view: View,
        var onLongDelete: (String) -> Unit
    )  : RecyclerView.ViewHolder(view){
        fun bind(data : MessageModel){
            itemView.apply {
                tvMessage.text = data.message
                setOnLongClickListener {
                    data.message?.let { it1 -> onLongDelete.invoke(it1) }
                    return@setOnLongClickListener true
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IMessageVH {
        return if (viewType == MESSAGE_TYPE_RIGHT) {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_right, parent, false)
            IMessageVH(view, onLongDelete)
        } else {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_left, parent, false)
            IMessageVH(view, onLongDelete)
        }
    }

    override fun onBindViewHolder(holder: IMessageVH, position: Int) {
        items.let { holder.bind(it[position]) }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        firebaseUser = FirebaseAuth.getInstance().currentUser
        if (items[position].senderId == firebaseUser!!.uid) {
            return MESSAGE_TYPE_RIGHT
        } else {
            return MESSAGE_TYPE_LEFT
        }
    }

}