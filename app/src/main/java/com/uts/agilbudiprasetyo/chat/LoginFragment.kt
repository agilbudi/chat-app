package com.uts.agilbudiprasetyo.chat

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.firebase.ui.auth.ui.email.CheckEmailFragment.TAG
import com.google.firebase.auth.FirebaseAuth
import com.uts.agilbudiprasetyo.chat.databinding.FragmentLoginBinding
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {
    val firebase = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentLoginBinding>(
            inflater, R.layout.fragment_login, container, false)
        binding.btnRegister.setOnClickListener {
            view: View? ->
            view?.findNavController()?.navigate(R.id.action_loginFragment_to_registerFragment)
        }
        binding.btnLogin.setOnClickListener {
            view: View? ->
            view?.let { here -> loginToFirebase(here) }
        }
        return binding.root
    }

    private fun loginToFirebase(view: View) {
        val email = et_login_email.text.toString().trim()
        val passord = et_login_password.text.toString()

        if (email == "" && passord == "") {
            Toast.makeText(context, "Anda tidak perlu masuk! jika semua kosong...", Toast.LENGTH_SHORT).show()
        } else if (email == "" || email == " ") {
            Toast.makeText(context, "Butuh Email?", Toast.LENGTH_SHORT).show()
        } else if (passord == "" || passord.length < 6) {
            if (passord.length in 1..5) {
                Toast.makeText(context, "Password kurang dari 6", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(context, "Anda sudah Pikun? Password masih kosong!?", Toast.LENGTH_SHORT).show()
            }
        } else {
            firebase.signInWithEmailAndPassword(email, passord)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        Log.d(TAG, "Sign: success")
                        Toast.makeText(context, "Login Berhasil!", Toast.LENGTH_SHORT).show()
                        val intent = Intent(context, DaftarTemanActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK.or(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        startActivity(intent)
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Login Gagal.", Toast.LENGTH_SHORT).show()
                }
        }
    }

}