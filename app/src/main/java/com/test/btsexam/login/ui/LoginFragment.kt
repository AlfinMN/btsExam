package com.test.btsexam.login.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.test.btsexam.R
import com.test.btsexam.config.ExamBtsApp
import com.test.btsexam.databinding.FragmentLoginBinding
import com.test.btsexam.home.HomeActivity
import com.test.btsexam.login.data.AuthModel
import com.test.btsexam.login.data.AuthRepo
import javax.inject.Inject

class LoginFragment : Fragment(), View.OnClickListener {
    lateinit var navController : NavController
    private var _binding: FragmentLoginBinding? = null
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
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        binding.apply {
            toRegister.setOnClickListener(this@LoginFragment)
            btnLogin.setOnClickListener(this@LoginFragment)
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.toRegister ->{
                view?.findNavController()?.navigate(R.id.action_global_to_registerFragment)
                navController = view?.let { Navigation.findNavController(it) }!!
            }
            binding.btnLogin -> {
                val user_msg_error: String = binding.authUsername.text.toString()

                if(user_msg_error.trim().isEmpty()) {
                    binding.authUsername.error = "Required"
                    Toast.makeText(this.requireContext(), "Username Required ", Toast.LENGTH_SHORT).show()
                }   else if (binding.authPassword.text.toString().trim().isEmpty()) {
                    Toast.makeText(this.requireContext(), "Password Required ", Toast.LENGTH_SHORT).show()
                } else {
                    val authModel = AuthModel(
                        username = binding.authUsername.text.toString(),
                        password = binding.authPassword.text.toString()
                    )

                    authRepo.resAuth.observe(this, Observer {
                        if (it.statusCode==2110){
                            startActivity(Intent(this.context,HomeActivity::class.java))
                        } else {
                            println("Login failed")
                        }
                    })
                    authRepo.login(authModel,requireContext())
                }
            }
        }
    }
}