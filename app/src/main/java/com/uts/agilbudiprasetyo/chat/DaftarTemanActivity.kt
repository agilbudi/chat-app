package com.uts.agilbudiprasetyo.chat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_daftar_teman.*

class DaftarTemanActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daftar_teman)

        checkSign()
        val listUser = listUser()
        rv_daftar_teman.layoutManager = LinearLayoutManager(this)
        rv_daftar_teman.setHasFixedSize(true)
        rv_daftar_teman.adapter = ChatAdapter(listUser, {userItem: DataUser -> userItemClicked(userItem)})

    }

    private fun userItemClicked(userItem: DataUser) {
        val intent = Intent(this, DetailUserActivity::class.java)
        intent.putExtra(Intent.EXTRA_EMAIL, userItem.email)
        intent.putExtra(Intent.EXTRA_TEXT, userItem.nama)
        startActivity(intent)

    }

    private fun listUser(): List<DataUser>{

        val nama = resources.getStringArray(R.array.nama)
        val email = resources.getStringArray(R.array.email)
        val asal = resources.getStringArray(R.array.asal)
        val listItem = ArrayList<DataUser>()
        var x = nama.size.toString().toInt()-1
        while (x >= 0) {
            listItem.add(DataUser( asal[x], email[x], "foto", nama[x]))
            x--
        }
        return listItem
    }

    private fun checkSign() {
        if (FirebaseAuth.getInstance().uid.isNullOrEmpty()){
            MainActivity.launchIntentClear(this)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu_top, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_aboutFragment->{
                MeActivity.launchIntent(this)
            }
            R.id.menu_logout->{
                FirebaseAuth.getInstance().signOut()
                MainActivity.launchIntentClear(this)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}