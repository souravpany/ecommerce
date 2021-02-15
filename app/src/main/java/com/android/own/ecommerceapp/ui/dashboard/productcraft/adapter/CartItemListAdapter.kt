package com.android.own.ecommerceapp.ui.dashboard.productcraft.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.Spinner
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.android.own.ecommerceapp.R
import com.android.own.ecommerceapp.data.model.Items


class CartItemListAdapter(
    c: Context, p: ArrayList<Items>, cartInterface: CartInterface
) :
    RecyclerView.Adapter<CartItemListAdapter.MyViewHolder>() {

    private var context: Context = c
    private var profiles: ArrayList<Items> = p
    private val cartInterface: CartInterface? = cartInterface

    var quantity = 1

    @NonNull
    override fun onCreateViewHolder(
        @NonNull parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(R.layout.cart_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, mPosition: Int) {
        holder.name.text = profiles[mPosition].name
        holder.tvCartPrice.text = profiles[mPosition].price.toString()



        holder.quantitySpinner.onItemSelectedListener = object :
            OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {
                quantity = position + 1

               /* profiles[mPosition].price = profiles[mPosition].price?.toInt()
                    ?.let { getInt(it.toString()).times(quantity).toString() }

                holder.tvCartPrice.text = profiles[mPosition].price.toString()
                cartInterface?.changeQuantity(profiles[mPosition].price?.toInt())*/

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }


    }


    override fun getItemCount(): Int {
        return profiles.size
    }

    inner class MyViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.findViewById(R.id.tvProductName)
        var tvCartPrice: TextView = itemView.findViewById(R.id.tvCartPrice)
        var quantitySpinner: Spinner = itemView.findViewById(R.id.quantitySpinner)
    }


    interface CartInterface {
        fun changeQuantity(quantity: Int?)
    }
}