package com.uts.agilbudiprasetyo.chat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_detail_user.*
import kotlinx.android.synthetic.main.item.*

class DetailUserActivity : AppCompatActivity() {
    companion object{
        val EXTRA_NAME = ""
        val EXTRA_EMAIL= ""
        val EXTRA_ASAL= ""
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_user)

        var intentActive = getIntent()
        
        if(intentActive.hasExtra(Intent.EXTRA_TEXT)){
            val nama = intentActive.getStringExtra(Intent.EXTRA_TEXT)
            val email = intentActive.getStringExtra(Intent.EXTRA_EMAIL)
            tv_detail_nama.text = nama
            tv_detail_email.text = email
        }
    }
}