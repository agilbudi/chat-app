package com.uts.agilbudiprasetyo.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item.view.*

class ChatAdapter(val userList: List<DataUser>, val onClickItem: (DataUser)-> Unit):
                    RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item, parent, false)
        return itemUser(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as itemUser).bind(userList[position], onClickItem)
    }

    class itemUser(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(user: DataUser, onClickItem: (DataUser) -> Unit){
            itemView.tv_item_nama.text = user.nama
            itemView.tv_item_asal.text = user.asal
            Picasso.get().load(user.foto).placeholder(R.drawable.ic_foto).into(itemView.iv_item_foto)
            itemView.setOnClickListener { onClickItem(user) }
        }
    }

    override fun getItemCount() = userList.size

}