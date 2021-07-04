package com.gtech.aectnp.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.gtech.aectnp.R
import com.gtech.aectnp.databinding.FragmentProfileBinding
import com.gtech.aectnp.ui.auth.GetUserModel

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
    val currentuser = FirebaseAuth.getInstance().currentUser?.uid.toString()
    private var param2: String? = null
    var _binding: FragmentProfileBinding? = null
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
        val status = arrayOf("Pass", "Fail")
        val adapter = ArrayAdapter<String>(
            requireContext(), android.R.layout.simple_list_item_1, status
        )

        FirebaseDatabase.getInstance().reference.child("users").child(currentuser)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val model = snapshot.getValue(GetUserModel::class.java)
                    if (model?.name != null) binding.name.setText(model?.name)
                    model?.number?.let { binding.number.setText(it) }
                    model?.currentsem?.let { binding.currentsem.setText(it) }
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
        getprofile(currentuser)
        binding.sem1Status.setAdapter(adapter)
        binding.sem2Status.setAdapter(adapter)
        binding.sem3Status.setAdapter(adapter)
        binding.sem4Status.setAdapter(adapter)
        binding.sem5Status.setAdapter(adapter)
        binding.sem6Status.setAdapter(adapter)
        binding.sem7Status.setAdapter(adapter)
        binding.sem8Status.setAdapter(adapter)
        val semlist = arrayOf(
            "Sem I",
            "Sem II",
            "Sem III",
            "Sem IV",
            "Sem V",
            "Sem VI",
            "Sem VII",
            "Sem VIII"
        )
        val semadapter =
            ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, semlist)
        binding.currentsem.setAdapter(semadapter)

        binding.update.setOnClickListener {
            val map = HashMap<String, Any>()
            if (binding.marks10.text.toString().isNotBlank()) map["marks10"] =
                binding.marks10.text.toString()
            if (binding.marksdiploma.text.toString().isNotBlank()) map["marksdiploma"] =
                binding.marksdiploma.text.toString()
            if (binding.marks12.text.toString().isNotBlank()) map["marks12"] =
                binding.marks12.text.toString()
            if (binding.percentage10.text.toString().isNotBlank()) map["percentage10"] =
                binding.percentage10.text.toString()
            if (binding.percentagediploma.text.toString().isNotBlank()) map["percentagediploma"] =
                binding.percentagediploma.text.toString()
            if (binding.percentage12.text.toString().isNotBlank()) map["percentage12"] =
                binding.percentage12.text.toString()
            if (binding.persem1.text.toString().isNotBlank()) map["persem1"] =
                binding.persem1.text.toString()
            if (binding.persem2.text.toString().isNotBlank()) map["persem2"] =
                binding.persem2.text.toString()
            if (binding.persem3.text.toString().isNotBlank()) map["persem3"] =
                binding.persem3.text.toString()
            if (binding.persem4.text.toString().isNotBlank()) map["persem4"] =
                binding.persem4.text.toString()
            if (binding.persem5.text.toString().isNotBlank()) map["persem5"] =
                binding.persem5.text.toString()
            if (binding.persem6.text.toString().isNotBlank()) map["persem6"] =
                binding.persem6.text.toString()
            if (binding.persem7.text.toString().isNotBlank()) map["persem7"] =
                binding.persem7.text.toString()
            if (binding.persem8.text.toString().isNotBlank()) map["persem8"] =
                binding.persem8.text.toString()

            if (binding.sem1Status.text.toString().isNotBlank()) map["sem1Status"] =
                binding.sem1Status.text.toString()
            if (binding.sem2Status.text.toString().isNotBlank()) map["sem2Status"] =
                binding.sem2Status.text.toString()
            if (binding.sem3Status.text.toString().isNotBlank()) map["sem3Status"] =
                binding.sem3Status.text.toString()
            if (binding.sem4Status.text.toString().isNotBlank()) map["sem4Status"] =
                binding.sem4Status.text.toString()
            if (binding.sem5Status.text.toString().isNotBlank()) map["sem5Status"] =
                binding.sem5Status.text.toString()
            if (binding.sem6Status.text.toString().isNotBlank()) map["sem6Status"] =
                binding.sem6Status.text.toString()
            if (binding.sem7Status.text.toString().isNotBlank()) map["sem7Status"] =
                binding.sem7Status.text.toString()
            if (binding.sem8Status.text.toString().isNotBlank()) map["sem8Status"] =
                binding.sem8Status.text.toString()

            FirebaseDatabase.getInstance().reference.child("users").child(currentuser)
                .updateChildren(map).addOnCompleteListener { it ->
                    if (it.isSuccessful) Toast.makeText(
                        requireContext(),
                        "Details Updated",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }
    }

    private fun getprofile(currentuser: String) {
FirebaseDatabase.getInstance().reference.child("users/${currentuser}").addValueEventListener(object :ValueEventListener{
    override fun onDataChange(snapshot: DataSnapshot) {
val m = snapshot.getValue(GetProfile::class.java)
  m?.marks10?.let {  binding.marks10.setText(it) }
  m?.marksdiploma?.let {  binding.marksdiploma.setText(it) }
  m?.marks12?.let {  binding.marks12.setText(it) }
  m?.percentage10?.let {  binding.percentage10.setText(it) }
  m?.percentagediploma?.let {  binding.percentagediploma.setText(it) }
  m?.percentage12?.let {  binding.percentage12.setText(it) }
  m?.persem1?.let {  binding.persem1.setText(it) }
  m?.persem2?.let {  binding.persem2.setText(it) }
  m?.persem3?.let {  binding.persem3.setText(it) }
  m?.persem4?.let {  binding.persem4.setText(it) }
  m?.persem5?.let {  binding.persem5.setText(it) }
  m?.persem6?.let {  binding.persem6.setText(it) }
  m?.persem7?.let {  binding.persem7.setText(it) }
  m?.persem8?.let {  binding.persem8.setText(it) }
  m?.sem1Status?.let {  binding.sem1Status.setText(it) }
  m?.sem2Status?.let {  binding.sem2Status.setText(it) }
  m?.sem3Status?.let {  binding.sem3Status.setText(it) }
  m?.sem4Status?.let {  binding.sem4Status.setText(it) }
  m?.sem5Status?.let {  binding.sem5Status.setText(it) }
  m?.sem6Status?.let {  binding.sem6Status.setText(it) }
  m?.sem7Status?.let {  binding.sem7Status.setText(it) }
  m?.sem8Status?.let {  binding.sem8Status.setText(it) }
    }

    override fun onCancelled(error: DatabaseError) {
    }
})
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