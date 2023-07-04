package com.example.category.presentation.fragments

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.BulletSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.bumptech.glide.Glide
import com.example.category.R
import com.example.category.databinding.FragmentDetailBinding
import com.example.category.di.ArticlesComponentViewModel
import com.example.category.presentation.viewmodel.DetailViewModel
import com.example.category.presentation.viewmodel.appState.DetailAppState
import com.example.domain.entity.Dishe
import javax.inject.Inject

class DetailFragment : DialogFragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!


    @Inject
    internal lateinit var viewModelFactory: dagger.Lazy<DetailViewModel.Factory>

    private val viewModel: DetailViewModel by viewModels {
        viewModelFactory.get()
    }


    override fun onAttach(context: Context) {
        ViewModelProvider(this).get<ArticlesComponentViewModel>()
            .newDetailsComponent.inject(this)
        super.onAttach(context)
    }


    override fun getTheme(): Int  = R.style.RoundedDialogStyle

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentDetailBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

      val  dish = arguments?.let{
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
              it.getParcelable(KAY_PARENT_DIALOG, Dishe::class.java)
          }else{
              it.getParcelable(KAY_PARENT_DIALOG)
          }

      }

        viewModel.listItem.observe(viewLifecycleOwner){
            renderData(it)
        }

        if (dish != null) {
            viewModel.init(dish)
        }


        binding.addToMyCard.setOnClickListener {
            viewModel.addToCard()
        }

        binding.close.setOnClickListener {
            this.dismiss()
        }

    }

    private fun renderData(it: DetailAppState) {
        when(it){
            is DetailAppState.OnSuccess ->{

                binding.dishName.text= it.dishe.name
                binding.dishPrice.text = it.dishe.price.toString() +" Р "

                val string  = SpannableString(it.dishe.weight.toString() + " г")
                string.setSpan(BulletSpan(10), 0, string.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

                binding.dishWeight.text = string
                binding.dishDescription.text = it.dishe.description

                Glide.with(binding.dishImage.context)
                    .load(it.dishe.image_url)
                    .centerInside()
                    .into(binding.dishImage)
            }

            is DetailAppState.IsContain ->{

                if (it.isContain) {

                    binding.addToMyCard.visibility = View.GONE
                    binding.existInMyCard.visibility = View.VISIBLE
                }else{
                    binding.addToMyCard.visibility = View.VISIBLE
                    binding.existInMyCard.visibility = View.GONE
                }

            }

            is DetailAppState.AddToCard ->{

                binding.addToMyCard.visibility = View.GONE
                binding.existInMyCard.visibility = View.VISIBLE
                Toast.makeText(requireActivity(), "Successfully added", Toast.LENGTH_SHORT).show()

            }

            is DetailAppState.Error ->{
                Toast.makeText(requireActivity(), it.error, Toast.LENGTH_SHORT).show()
            }
        }

    }

    companion object {

        @JvmStatic
        fun newInstance(dishe: Dishe) =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KAY_PARENT_DIALOG, dishe)
                }
            }
    }
}