package com.example.todolist
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {


    private lateinit var btn:Button
    private lateinit var Task:EditText
    private lateinit var helper: SQLiteHelper
    private var itm:ItemModel?=null
    private var adaptor:listAdaptor?=null
    private lateinit var recyclerView : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initial()
        initRecyclerview()
        helper= SQLiteHelper(this)
        btn.setOnClickListener(){
            additem()
        }

        adaptor?.setOnClickItem {
            Toast.makeText(this, it.Task, Toast.LENGTH_SHORT).show()
        }

        adaptor?.setOnClickDeleteItem {
            deleteTask(it.id)
        }

    }

    private fun initial(){
        btn=findViewById(R.id.btnadd)
        Task=findViewById(R.id.tvtask)
        recyclerView=findViewById(R.id.rv)

    }

    private fun initRecyclerview(){
        recyclerView.layoutManager= LinearLayoutManager(this)
        adaptor=listAdaptor()
        recyclerView.adapter=adaptor
    }


    private fun additem() {
        val tsk = Task.text.toString()
        if (tsk.isNullOrEmpty())
            Toast.makeText(this, "please first enter a task", Toast.LENGTH_LONG).show()
        else {
            val itm = ItemModel(Task=tsk)
            val status =helper.insertItem(itm)
            if (status > -1) {
                Toast.makeText(this, "Item was Added", Toast.LENGTH_LONG).show()
                clear()
                getItem()
            }else
                Toast.makeText(this, "Item was NOT Added !!!", Toast.LENGTH_LONG).show()
        }
    }

    private fun clear(){
        Task.setText(" ")
    }

    private fun getItem() {
        val itmList=helper.getAllItems()
        android.util.Log.e("error is here","${itmList.size}")
        adaptor?.addItems(itmList)
    }

    private fun deleteTask(id:Int){
        val builder= AlertDialog.Builder(this)
        builder.setMessage("Are you sure you are done with this task?")
        builder.setCancelable(true)
        builder.setPositiveButton("yes"){ dialog,_ ->
            helper.deleteItem(id)
            getItem()
            dialog.dismiss()
        }
        builder.setNegativeButton("no"){dialog,_ -> dialog.dismiss()}
        val alert=builder.create()
        alert.show()
    }

}