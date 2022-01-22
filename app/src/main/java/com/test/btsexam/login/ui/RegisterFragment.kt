package com.test.btsexam.login.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.test.btsexam.R
import com.test.btsexam.config.ExamBtsApp
import com.test.btsexam.databinding.FragmentLoginBinding
import com.test.btsexam.databinding.FragmentRegisterBinding
import com.test.btsexam.login.data.AuthRepo
import com.test.btsexam.login.data.RegisterModel
import javax.inject.Inject


class RegisterFragment : Fragment(), View.OnClickListener {

    lateinit var navController : NavController
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var authRepo: AuthRepo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as ExamBtsApp).applicationComponent.inject(this)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        binding.apply {
            btnRegister.setOnClickListener(this@RegisterFragment)
            binding.email.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(p0: Editable?) {
                    if (android.util.Patterns.EMAIL_ADDRESS.matcher(binding.email.text.toString()).matches())
                        binding.btnRegister.isEnabled = true
                    else{
                        binding.btnRegister.isEnabled = false
                        binding.email.setError("Email not valid!")
                    }
                }

            })
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.btnRegister -> {
                val user_msg_error: String = binding.authUsername.text.toString()

                if(user_msg_error.trim().isEmpty()) {
                    binding.authUsername.error = "Required"
                    Toast.makeText(this.requireContext(), "User Name Required ", Toast.LENGTH_SHORT).show()
                }
                else if (binding.email.text.toString().trim().isEmpty()) {
                    binding.email.error = "Required"
                    Toast.makeText(this.requireContext(), "Email Required ", Toast.LENGTH_SHORT).show()
                }
                else if (binding.authPassword.text.toString().trim().isEmpty()) {
                    Toast.makeText(this.requireContext(), "Password Required ", Toast.LENGTH_SHORT).show()
                }
                else if (binding.authConfirmPass.text.toString().trim().isEmpty()) {
                    Toast.makeText(this.requireContext(), "Password Required ", Toast.LENGTH_SHORT).show()
                }
                else if (binding.authPassword.text.toString() != binding.authConfirmPass.text.toString()){
                    Toast.makeText(this.context,"Password does not match", Toast.LENGTH_SHORT).show()
                } else{
                   val registerModel = RegisterModel(
                       username = binding.authUsername.text.toString(),
                       email = binding.email.text.toString(),
                       password = binding.authPassword.text.toString()
                   )

                    authRepo.resApi.observe(this, Observer {
                        if (it.statusCode == 2000){
                            Toast.makeText(this.context,
                                    "your account has been created", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this.context,AuthActivity::class.java))
                        } else {
                            println("Error")
                        }
                    })
                    authRepo.register(registerModel, requireContext())

                }
            }
        }
    }

}