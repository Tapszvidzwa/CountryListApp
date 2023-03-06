package com.example.countryapp.ui.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.countryapp.databinding.FragmentCountryListBinding
import com.example.countryapp.ui.CountryListAdapter
import com.example.countryapp.ui.viewmodels.CountryListViewModel
import com.example.countryapp.ui.viewmodels.ScreenState
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class CountryListFragment : Fragment() {
    private var _binding: FragmentCountryListBinding? = null
    private val binding
        get() = _binding
    private lateinit var adapter: CountryListAdapter
    private val viewModel: CountryListViewModel by viewModels { CountryListViewModel.Factory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCountryListBinding.inflate(inflater, container, false)
        val view = binding?.root
        binding?.countryList?.layoutManager = object : LinearLayoutManager(requireContext()) {}
        adapter = CountryListAdapter()
        binding?.countryList?.adapter = adapter

        viewModel.fetchCountryList()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->

                    when (uiState.state) {
                        ScreenState.LOADING -> {
                            binding?.progressBar?.visibility = uiState.showLoadingSpinner
                        }

                        ScreenState.SUCCESS -> {
                            binding?.progressBar?.visibility = uiState.showLoadingSpinner
                            adapter.submitList(uiState.countriesList)
                        }

                        ScreenState.ERROR -> {
                            binding?.progressBar?.visibility = uiState.showLoadingSpinner
                            adapter.submitList(uiState.countriesList)
                            binding?.let { showErrorSnackBar(it.root, uiState.errorMessage) }
                        }

                        else -> {
                            // no-op
                        }
                    }
                }
            }
        }

        return view
    }

    private fun showErrorSnackBar(rootView: View, msg: String) {
        val snackbar = Snackbar.make(
            rootView,
            msg,
            Snackbar.LENGTH_LONG
        )

        snackbar.setAction("Retry") {
            viewModel.retry()
        }

        snackbar.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(): Fragment {
            return CountryListFragment()
        }
    }
}