package com.samuelsihombing.kelompok8.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.samuelsihombing.kelompok8.app.ApiConfig
import com.samuelsihombing.kelompok8.helper.SharedPref
import com.samuelsihombing.kelompok8.model.ResponModel
import com.samuelsihombing.kelompok8.MainActivity
import com.samuelsihombing.kelompok8.R
import kotlinx.android.synthetic.main.activity_masuk.*
import kotlinx.android.synthetic.main.activity_masuk.btn_register
import kotlinx.android.synthetic.main.activity_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RegisterActivity : AppCompatActivity() {

    lateinit var s: SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        s = SharedPref(this)

        btn_register.setOnClickListener {
            register()
        }

        btn_google.setOnClickListener {
            dataDummy()
        }
    }

    fun dataDummy(){
        edt_nama.setText("Virna")
        edt_email.setText("virna@gmail.com")
        edt_phone.setText("020184012")
        edt_password.setText("virna123")
    }

    fun register(){
        if(edt_nama.text.isEmpty()){
            edt_nama.error = "Mohon isi nama anda"
            edt_nama.requestFocus()
            return
        }else if(edt_email.text.isEmpty()) {
            edt_email.error = "Mohon isi email anda"
            edt_email.requestFocus()
            return
        }else if(edt_phone.text.isEmpty()) {
            edt_phone.error = "Mohon isi nomor telepon anda"
            edt_phone.requestFocus()
            return
        }else if(edt_password.text.isEmpty()) {
            edt_password.error = "Mohon isi password anda anda"
            edt_password.requestFocus()
            return
        }

        pb.visibility = View.VISIBLE
        ApiConfig.instanceRetrofit.register(edt_nama.text.toString(), edt_email.text.toString(), edt_phone.text.toString(), edt_password.text.toString()).enqueue(object :
            Callback<ResponModel> {
            override fun onFailure(call: Call<ResponModel>, t: Throwable) {
                pb.visibility = View.GONE
                Toast.makeText(this@RegisterActivity, "Error: " + t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
                pb.visibility = View.GONE
                val respon = response.body() !!
                if (response.body()!!.success == 1){
//                    berhasil
                    s.setStatusLogin(true)
                    s.setUser(respon.user)
                    val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                    Toast.makeText(this@RegisterActivity, "Selamat datang " + respon.user.name, Toast.LENGTH_SHORT).show()
                }else{
//                    gagal
                    Toast.makeText(this@RegisterActivity, "Error: " + respon.message, Toast.LENGTH_SHORT).show()
                }
            }
        })

    }
}



















