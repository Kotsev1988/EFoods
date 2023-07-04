package com.example.mycard.presentation.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.mycard.databinding.FragmentMyCardBinding
import com.example.mycard.di.ArticlesComponentMyCardViewModel
import com.example.mycard.presentation.adapter.MyCardAdapter
import com.example.mycard.presentation.viewModel.MyCardViewModel
import com.example.mycard.presentation.viewModel.appState.AppStateMyCard
import javax.inject.Inject

class MyCardFragment : Fragment() {

    private var _binding: FragmentMyCardBinding? = null
    private val binding get() = _binding!!


    private var myAdapter: MyCardAdapter? = null


    @Inject
    internal lateinit var viewModelFactory: dagger.Lazy<MyCardViewModel.Factory>

    private val viewModel: MyCardViewModel by viewModels {
        viewModelFactory.get()
    }


    override fun onAttach(context: Context) {
        ViewModelProvider(this).get<ArticlesComponentMyCardViewModel>()
            .newDetailsMyCardComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
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

                myAdapter = MyCardAdapter(it.myCardList)

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

}