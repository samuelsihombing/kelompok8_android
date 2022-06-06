package com.example.tokoonline.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.samuelsihombing.kelompok8.helper.Helper


import com.google.gson.Gson
import com.samuelsihombing.kelompok8.R
import com.samuelsihombing.kelompok8.activity.DetailProdukActivity
import com.samuelsihombing.kelompok8.model.Produk
import com.samuelsihombing.kelompok8.util.Config
import com.squareup.picasso.Picasso
import kotlin.collections.ArrayList

class AdapterProduk(var activity: Activity, var data:ArrayList<Produk>):RecyclerView.Adapter<AdapterProduk.Holder>() {
    class Holder(view: View):RecyclerView.ViewHolder(view){
        val tvNama = view.findViewById<TextView>(R.id.tv_nama)
        val tvHarga = view.findViewById<TextView>(R.id.tv_harga)
        val imgProduk = view.findViewById<ImageView>(R.id.img_produk)
        val layout = view.findViewById<CardView>(R.id.layout_utama)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_produk, parent, false)
        return Holder(view)
    }


    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.tvNama.text = data[position].name
        holder.tvHarga.text = Helper().gantiRupiah(data[position].harga)
        val image = "http://192.168.100.132/web%20pam/toko/public/images/" + data[position].image
        Picasso.get()
            .load(image)
            .placeholder(R.drawable.hp_pavilion_15_cx0056wm)
            .error(R.drawable.hp_pavilion_15_cx0056wm)
            .into(holder.imgProduk)

        holder.layout.setOnClickListener {
            val intentvity = Intent(activity, DetailProdukActivity::class.java)

            val str = Gson().toJson(data[position], Produk::class.java)
            intentvity.putExtra("extra", str)

            activity.startActivity(intentvity)
        }
    }
}






















