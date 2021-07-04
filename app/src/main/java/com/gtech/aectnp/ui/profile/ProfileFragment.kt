package com.gtech.aectnp.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.gtech.aectnp.R
import com.gtech.aectnp.databinding.FragmentProfileBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
var _binding :FragmentProfileBinding?=null
    val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
val status = arrayOf("Pass","Fail")
val adapter = ArrayAdapter<String>(requireContext(),android.R.layout.simple_list_item_1
,status)
        binding.sem1Status.setAdapter(adapter)
        binding.sem2Status.setAdapter(adapter)
        binding.sem3Status.setAdapter(adapter)
        binding.sem4Status.setAdapter(adapter)
        binding.sem5Status.setAdapter(adapter)
        binding.sem6Status.setAdapter(adapter)
        binding.sem7Status.setAdapter(adapter)
        binding.sem8Status.setAdapter(adapter)
binding.update.setOnClickListener {
    Toast.makeText(requireContext(), "Details Updated", Toast.LENGTH_SHORT).show()
}
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}