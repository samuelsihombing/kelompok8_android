package com.samuelsihombing.kelompok8.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.samuelsihombing.kelompok8.helper.Helper
import com.samuelsihombing.kelompok8.model.Checkout
import com.samuelsihombing.kelompok8.room.MyDatabase
import com.google.gson.Gson
import com.samuelsihombing.kelompok8.MainActivity
import com.samuelsihombing.kelompok8.R

class SuccessMakananActivity : AppCompatActivity() {
    private lateinit var tv_nominal : TextView
    private lateinit var btn_cekStatus : Button
    var nominal = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success_makanan)

        tv_nominal = findViewById(R.id.tv_nominal)
        btn_cekStatus = findViewById(R.id.btn_cekStatus)

        setValues()
        mainButton()
    }

    fun mainButton() {

        btn_cekStatus.setOnClickListener {
            startActivity(Intent(this, RiwayatActivity::class.java))
        }
    }

    fun setValues() {
        val jsCheckout = intent.getStringExtra("checkout")

        val checkoutMakanan = Gson().fromJson(jsCheckout, Checkout::class.java)
        // hapus keranjang
        val myDb = MyDatabase.getInstance(this)!!
//        for (MakananMinuman in checkoutMakanan.produks){
//            myDb.daoKeranjang().deleteById(keranjang.id_makanan_minuman)
//        }
    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
        super.onBackPressed()
    }
}