package com.example.mysql.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mysql.databinding.ItemGroupBinding
import com.example.mysql.models.Group

class GroupAdapter(var list: List<Group>,val rvClick: RvClick): RecyclerView.Adapter<GroupAdapter.VH>() {

    inner class VH(private var itemRv: ItemGroupBinding):RecyclerView.ViewHolder(itemRv.root){
        fun onBind(group: Group){
            itemRv.menuItem.visibility = View.GONE
            itemRv.groupId.text = group.id.toString()
            itemRv.groupName.text = group.name
            itemRv.root.setOnClickListener {
                rvClick.itemClick(group)
            }
            itemRv.root.setOnLongClickListener {
                rvClick.longClick(group)
                true
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
        fun itemClick(group: Group)
        fun longClick(group: Group)
    }
}