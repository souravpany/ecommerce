package com.android.own.ecommerceapp.ui.dashboard.product.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.android.own.ecommerceapp.R
import com.android.own.ecommerceapp.data.model.Items


class ProductListAdapter(
    c: Context, p: ArrayList<Items>

) :
    RecyclerView.Adapter<ProductListAdapter.MyViewHolder>() {

    private var context: Context = c
    private var profiles: ArrayList<Items> = p

    @NonNull
    override fun onCreateViewHolder(
        @NonNull parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_view_product, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.name.text = profiles[position].name
        holder.price.text = profiles[position].price.toString()
        holder.details.text = profiles[position].details
    }

    override fun getItemCount(): Int {
        return profiles.size
    }

    inner class MyViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.findViewById(R.id.tvName)
        var price: TextView = itemView.findViewById(R.id.tvPrice)
        var details: TextView = itemView.findViewById(R.id.tvDetails)

    }

}