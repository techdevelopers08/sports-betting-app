package com.app.sportbetting.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.sportbetting.databinding.ItemMyCompsBinding
import com.app.sportbetting.models.getComp.Body

class MyCompAdapter(context: Context,var onClick:OnClickComp) : RecyclerView.Adapter<MyCompAdapter.MyCompViewHolder>() {

     var list =  emptyList<Body>()
    inner class MyCompViewHolder(val binding : ItemMyCompsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyCompViewHolder {
        val binding = ItemMyCompsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyCompViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyCompViewHolder, position: Int) {
        holder.binding.textView20.text = list[position].sport_name
        holder.binding.textView21.text = list[position].comp_name
        holder.binding.joinCompParent.setOnClickListener {
            onClick.onClickComp(
                list[position].sports_id
            )
        }
    }

    override fun getItemCount():Int{
        return list.size
    }
    fun setData(body: List<Body>) {
        this.list = body
    }

    interface OnClickComp{
        fun onClickComp(sportsId: Int)
    }

}