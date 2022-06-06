package com.samuelsihombing.kelompok8.model

import com.samuelsihombing.kelompok8.model.Produk

class ResponModel {
    var success = 0
    lateinit var message:String
    var user = User()
    var produks:ArrayList<Produk> = ArrayList()
    var transaksis: ArrayList<Transaksi> = ArrayList()
    var transaksi = Transaksi()
}