package com.samuelsihombing.kelompok8.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.samuelsihombing.kelompok8.MainActivity
import com.samuelsihombing.kelompok8.R
import com.samuelsihombing.kelompok8.app.ApiConfig
import com.samuelsihombing.kelompok8.helper.Helper
import com.samuelsihombing.kelompok8.helper.SharedPref
import com.samuelsihombing.kelompok8.room.MyDatabase
import com.samuelsihombing.kelompok8.model.Checkout
import com.samuelsihombing.kelompok8.model.ResponModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PengirimanActivity  : AppCompatActivity(){
    lateinit var myDb: MyDatabase
    var totalHarga = 0

    private lateinit var edt_nama : EditText
    private lateinit var edt_phone : EditText
    private lateinit var edt_catatan : EditText
    private lateinit var tv_total : TextView
    private lateinit var edt_total : EditText
    private lateinit var btn_pesan : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pengiriman)
        edt_nama = findViewById(R.id.edt_nama)
        edt_phone = findViewById(R.id.edt_phone)
        edt_catatan = findViewById(R.id.edt_catatan)
        tv_total = findViewById(R.id.tv_total)
        edt_total = findViewById(R.id.edt_total)
        btn_pesan = findViewById(R.id.btn_pesan)


        myDb = MyDatabase.getInstance(this)!!
//        totalHarga = Integer.valueOf(intent.getStringExtra("extra"))
//        tv_total.text = Helper().gantiRupiah(totalHarga)


        mainButton()
    }

    private fun mainButton() {


        btn_pesan.setOnClickListener {
            bayar()
        }
    }

    private fun bayar() {
        val user = SharedPref(this).getUser()!!

        val listProduk = myDb.daoKeranjang().getAll() as ArrayList
        var totalItem = 0
        var totalHarga = 0
        val produks = ArrayList<Checkout.Item>()
        for (p in listProduk) {
            if (p.selected) {
                totalItem += p.jumlah
                totalHarga += (p.jumlah * Integer.valueOf(p.harga))

                val produk = Checkout.Item()
                produk.id = "" + p.id
                produk.total_item = "" + p.jumlah
                produk.total_harga = "" + (p.jumlah * Integer.valueOf(p.harga))
                produk.catatan = "catatan baru"
                produks.add(produk)
            }
        }

        val chekout = Checkout()
      chekout.user_id = "" + user.id
        chekout.total_item = "" + totalItem
//        chekout.total_harga = "" + totalHarga
        chekout.name = edt_nama.text.toString()
        chekout.phone = edt_phone.text.toString()
        chekout.total_harga = edt_catatan.text.toString()
        chekout.total_transfer  = edt_total.text.toString()
//        chekout.total_transfer = "" + (totalHarga )
        chekout.produks = produks

        val json = Gson().toJson(chekout, Checkout::class.java)
        Log.d("Respon:", "jseon:" + json)
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("extra", json)
        startActivity(intent)

        ApiConfig.instanceRetrofit.chekout(chekout).enqueue(object : Callback<ResponModel> {
            override fun onFailure(call: Call<ResponModel>, t: Throwable) {
                error(t.message.toString())
            }

            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
                if (!response.isSuccessful) {
                    error(response.message())
                    return
                }
                val respon = response.body()!!
                if (respon.success == 1) {
                    val jsCheckout = Gson().toJson(chekout, Checkout::class.java)
                    val intent1 = Intent(this@PengirimanActivity, SuccessMakananActivity::class.java)
                    intent1.putExtra("checkout", jsCheckout)
                    startActivity(intent1)
                } else {
                    error(respon.message)
                    Toast.makeText(this@PengirimanActivity, "Error:" + respon.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}