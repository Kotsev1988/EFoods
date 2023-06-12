package com.example.main.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import com.example.core.utils.InjectUtils
import com.example.main.R
import com.example.main.databinding.FragmentMainBinding
import com.example.main.di.DaggerMainComponent
import com.example.main.presentation.adapter.MainAdapter
import com.example.main.presentation.viewModel.MainViewModel
import com.example.main.presentation.viewModel.appState.AppState
import javax.inject.Inject

const val KAY_PARENT = "key_parent"
class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var  mainAdapter: MainAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerMainComponent.factory()
            .create(InjectUtils.provideBaseComponent(requireActivity().applicationContext))
            .inject(this)
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        val bundleBaseFrag = Bundle()
        bundleBaseFrag.putString("headerText", "location")
        parentFragment?.setFragmentResult(KAY_PARENT, bundleBaseFrag)
        _binding = FragmentMainBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.clickEvent.observe(viewLifecycleOwner){
            val name = it.goToMenuDetailsIfNotHandled()
            val bundle = Bundle()

            if (name != null) {
                bundle.putString("PRODUCT_ID", name)
                val request = NavDeepLinkRequest.Builder
                    .fromUri("android-app://eFood.app/categoryFragment/${name}".toUri())
                    .build()

                val bundleBaseFrag = Bundle()
                bundleBaseFrag.putString("headerText", name)
                findNavController().navigate(request)

            }
        }

        viewModel.listItem.observe(viewLifecycleOwner){
            renderData(it)
        }
        viewModel.init()
        binding.frameLoad.visibility = View.VISIBLE
    }

    private fun renderData(it: AppState) {

        when(it){
            is AppState.OnSuccess ->{
                mainAdapter = MainAdapter(it.productsListPresenter).apply {
                    DaggerMainComponent.factory().create(InjectUtils.provideBaseComponent(requireActivity().applicationContext)).inject(this)
                }
                binding.recyclerView.adapter = mainAdapter
                binding.frameLoad.visibility = View.GONE
            }
            is AppState.Error ->{
                Toast.makeText(requireActivity(), it.error, Toast.LENGTH_SHORT).show()
            }
        }
    }

}