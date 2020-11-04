package com.uts.agilbudiprasetyo.chat

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.uts.agilbudiprasetyo.chat.databinding.FragmentRegisterBinding
import kotlinx.android.synthetic.main.fragment_register.*
import java.util.*

class RegisterFragment : Fragment() {
    val firebase = FirebaseAuth.getInstance()

    val PICK_ALBUM = 109
    var FOTO_URI: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentRegisterBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_register, container, false)
        binding.btnBatal.setOnClickListener {
            view: View? ->
            view?.findNavController()?.navigate(R.id.action_registerFragment_to_loginFragment)
        }
        binding.btnDaftar.setOnClickListener {
                view: View? ->
                view?.let { here -> registerToFirebase(here) }
        }
        binding.ivRegisterFoto.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            intent.type = "image/*"
            startActivityForResult(intent, PICK_ALBUM)
        }
        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_ALBUM){
            if (resultCode == Activity.RESULT_OK && data!!.data != null){
                FOTO_URI = data.data
                iv_register_foto.setImageURI(FOTO_URI)
            }
        }
    }
    private fun registerToFirebase(view: View) { // add data register
        val nama = et_register_nama.text.toString()
        val asal = et_register_asal.text.toString()
        val email = et_register_email.text.toString().trim()
        val passord = et_register_password.text.toString()
        val uid = FirebaseAuth.getInstance().uid
        val db = FirebaseDatabase.getInstance().getReference("chat/$uid")
        //validasi
        if (nama == "" && asal == "" && email == "" && passord == "") {
            Toast.makeText(context, "Tidak ada yang perlu didaftarkan!", Toast.LENGTH_SHORT).show()
        } else if (nama == "" || nama == " ") {
            Toast.makeText(context, "Namamu Siapa?", Toast.LENGTH_SHORT).show()
        } else if (asal == "" || asal == " ") {
            Toast.makeText(context, "Masukan Asalmu!", Toast.LENGTH_SHORT).show()
        } else if (email == "" || email == " ") {
            Toast.makeText(context, "Email itu Penting! untuk Login...", Toast.LENGTH_SHORT).show()
        } else if (passord == "" || passord.length < 6) {
            if (passord.length in 1..5) {
                Toast.makeText(context, "Miskin Password :P", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(context, "Yakin gk Pake Password !?", Toast.LENGTH_SHORT).show()
            }
        } else {
            firebase.createUserWithEmailAndPassword(email, passord)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(context, "Tunggu Sebentar...", Toast.LENGTH_SHORT).show()
                        var namaFoto: String = "FotoDefault"
                        if (FOTO_URI == null) { // tanpa foto
                            db.setValue(DataUser(asal, email, namaFoto, nama))
                                .addOnSuccessListener {
                                    Toast.makeText(context, "Berhasil Membuat User!", Toast.LENGTH_SHORT).show()
                                    view.findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                                }
                                .addOnFailureListener {
                                    Toast.makeText(context, "Gagal Membuat User!", Toast.LENGTH_SHORT).show()
                                }
                        } else { // dengan foto
                            namaFoto = UUID.randomUUID().toString()
                            val uploadFirebase =
                                FirebaseStorage.getInstance().getReference("chat/res/images/$namaFoto")
                            uploadFirebase.putFile(FOTO_URI!!)
                                .addOnSuccessListener {
                                    uploadFirebase.downloadUrl.addOnSuccessListener {
                                        db.setValue(DataUser(asal, email, it.toString(), nama))
                                            .addOnSuccessListener {
                                                Toast.makeText(context, "Berhasil Membuat User!", Toast.LENGTH_SHORT).show()
                                                view.findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                                            }
                                            .addOnFailureListener {
                                                Toast.makeText(context, "Gagal Membuat User!", Toast.LENGTH_SHORT).show()
                                            }
                                    }
                                }
                                .addOnFailureListener {
                                    Toast.makeText(context, "foto gagal diupload...", Toast.LENGTH_SHORT).show()
                                }
                        }
                    } else {
                        Toast.makeText(context, "Koneksimu !!!", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(context, it.localizedMessage, Toast.LENGTH_SHORT).show()
                }
        }
    }

}