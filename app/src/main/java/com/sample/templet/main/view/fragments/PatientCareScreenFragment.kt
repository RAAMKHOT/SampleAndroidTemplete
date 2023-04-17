package com.sample.templet.main.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sample.templet.databinding.FragmentPatientCareScreenBinding
import com.sample.templet.utils.Constants.ARG_PARAM


class PatientCareScreenFragment : Fragment() {
    private var param: Bundle? = null
    private var _binding: FragmentPatientCareScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { param = it.getBundle(ARG_PARAM) }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // inflate the layout and bind to the _binding
        _binding = FragmentPatientCareScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(bundle: Bundle?) =
            PatientCareScreenFragment().apply {
                arguments = Bundle().apply { putBundle(ARG_PARAM, bundle) }
            }
    }
}