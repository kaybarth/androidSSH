package com.example.tutorial

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.tutorial.databinding.FragmentFirstBinding
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }*/

        binding.buttonFirst.setOnClickListener {
            val sshManager = Ssh()

            val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())

            val host: String? = sharedPreferences.getString("edit_text_preference", "")
            val username: String? = sharedPreferences.getString("edit_text_preference", "")
            val password: String? = sharedPreferences.getString("edit_text_preference", "")
            val command: String? = sharedPreferences.getString("edit_text_preference", "")

            val output = sshManager.executeCommand(host, username, password, command)
            println("Resultado del comando:\n$output")
        }

        binding.main2config.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_Config)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}