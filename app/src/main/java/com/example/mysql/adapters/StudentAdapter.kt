package com.example.mysql.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.mysql.databinding.ItemGroupBinding
import com.example.mysql.models.Students

class StudentAdapter(var list: List<Students>, val rvClick: RvClick): RecyclerView.Adapter<StudentAdapter.VH>() {

    inner class VH(private var itemRV: ItemGroupBinding):RecyclerView.ViewHolder(itemRV.root){
        fun onBind(students: Students){
            itemRV.groupId.text = students.number
            itemRV.groupName.text = students.name
            itemRV.root.setOnClickListener {
                rvClick.itemClick(students)
            }
            itemRV.menuItem.setOnClickListener {
                rvClick.menuClick(itemRV.menuItem, students)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemGroupBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
    interface RvClick{
        fun itemClick(students: Students)
        fun menuClick(imageView: ImageView,students: Students)
    }
}