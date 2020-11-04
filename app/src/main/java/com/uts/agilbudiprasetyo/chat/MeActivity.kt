package com.uts.agilbudiprasetyo.chat

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_me.*

class MeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_me)

        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/project-dummy-by-hide.appspot.com/o/chat%2Fres%2Fimages%2F073e224f-a7db-46b8-ad86-6b13ce9cafbe?alt=media&token=65337a7c-7cd0-4bb9-8301-b15183eb5e23")
            .into(iv_me_foto)
        btn_me_salam.setOnClickListener {
            Toast.makeText(this, "Salam hangat dari saya, Terima Kasih...", Toast.LENGTH_LONG).show()
        }
        tv_me_email.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.setType("text/html")
            intent.putExtra(Intent.EXTRA_EMAIL, "agilbudiprasetyo@gmail.com")
            intent.putExtra(Intent.EXTRA_CC, "agilbudiprasetyo@gmail.com")
            intent.putExtra(Intent.EXTRA_SUBJECT, "Silaturahmi")
            intent.putExtra(Intent.EXTRA_TEXT, "Assalamualaikum,")
            startActivity(Intent.createChooser(intent, "Send Email"))
        }
    }

    companion object{
        fun launchIntent(context : Context){
            val intent = Intent(context, MeActivity::class.java)
            context.startActivity(intent)
        }
    }
}