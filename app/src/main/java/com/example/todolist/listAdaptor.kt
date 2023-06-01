package com.example.todolist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class listAdaptor : RecyclerView.Adapter<listAdaptor.ViewHolder>(){

    private var onClickItem:((ItemModel)->Unit)?=null
    private var onClickDeleteItem:((ItemModel)->Unit)?=null
    private var itmList:ArrayList<ItemModel> =ArrayList()


    fun addItems(items:ArrayList<ItemModel>){
        this.itmList=items
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=
       ViewHolder(

            LayoutInflater.from(parent.context).inflate(R.layout.card_item,parent,false))
    override fun getItemCount(): Int {
        return itmList.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var itm=itmList[position]
        holder.bindView(itm)
        holder.itemView.setOnClickListener { onClickItem?.invoke(itm) }
        holder.btnDelete.setOnClickListener{onClickDeleteItem?.invoke(itm)}

    }
    class ViewHolder(var view:View):RecyclerView.ViewHolder(view){
        private var Task=view.findViewById<TextView>(R.id.task_tv)
        public var btnDelete:Button=view.findViewById(R.id.btnDelete)
        fun bindView(itm:ItemModel){
            Task.text=itm.Task
        }

    }

    fun setOnClickItem(callback: (ItemModel)->Unit){
        this.onClickItem=callback
    }

    fun setOnClickDeleteItem(callback: (ItemModel)->Unit){
        this.onClickDeleteItem=callback
    }


}