package com.example.mycard.presentation.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModelProvider
import com.example.core.utils.InjectUtils
import com.example.mycard.databinding.FragmentMyCardBinding
import com.example.mycard.di.DaggerMyCardComponent
import com.example.mycard.presentation.adapter.MyCardAdapter
import com.example.mycard.presentation.viewModel.MyCardViewModel
import com.example.mycard.presentation.viewModel.appState.AppStateMyCard
import javax.inject.Inject

const val KAY_PARENT = "key_parent"

class MyCardFragment : Fragment() {

    private var _binding: FragmentMyCardBinding? = null
    private val binding get() = _binding!!


    private var myAdapter: MyCardAdapter? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: MyCardViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[MyCardViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerMyCardComponent
            .builder()
            .baseComponent(InjectUtils.provideBaseComponent(requireActivity().applicationContext))
            .build()
            .inject(this)
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val bundleBaseFrag = Bundle()
        bundleBaseFrag.putString("headerText", "location")
        parentFragment?.setFragmentResult(KAY_PARENT, bundleBaseFrag)
        _binding = FragmentMyCardBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.listItem.observe(viewLifecycleOwner) {
            renderData(it)
        }

        viewModel.init()
    }

    @SuppressLint("SetTextI18n")
    private fun renderData(it: AppStateMyCard) {

        when (it) {
            is AppStateMyCard.OnSuccess -> {

                myAdapter = MyCardAdapter(it.myCardList).apply {
                    DaggerMyCardComponent.builder().baseComponent(context?.let {
                        InjectUtils.provideBaseComponent(it.applicationContext)
                    }).build().inject(this)
                }
                binding.myShops.adapter = myAdapter
            }

            is AppStateMyCard.OnUpdate -> {
                myAdapter?.setData(it.myCardList)
            }

            is AppStateMyCard.Error -> {
                Toast.makeText(requireActivity(), it.error, Toast.LENGTH_SHORT).show()
            }

            is AppStateMyCard.TotalPrice -> {
                binding.checkout.text = "Оплатить " + it.totalPrice + " Р"
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            MyCardFragment()
    }
}