package com.example.category.presentation.fragments

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
import com.example.category.databinding.FragmentCategoryBinding
import com.example.category.di.ArticlesComponentViewModel
import com.example.category.presentation.adapters.MainAdapter
import com.example.category.presentation.adapters.dishes.DishesDelegate
import com.example.category.presentation.adapters.dishes.DishesDelegateAdapter
import com.example.category.presentation.adapters.menus.CategoriesDelegate
import com.example.category.presentation.adapters.menus.CategoriesDelegateAdapter
import com.example.category.presentation.viewmodel.CategoryViewModel
import com.example.category.presentation.viewmodel.appState.CategoryAppState
import com.example.domain.entity.Menus
import javax.inject.Inject

const val KAY_PARENT_DIALOG = "key_parent_dialog"

class CategoryFragment : Fragment() {
    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!

    private val mainAdapter by lazy {
        MainAdapter.Builder()
            .add(CategoriesDelegateAdapter())
            .add(DishesDelegateAdapter())
            .build()
    }

    @Inject
    internal lateinit var viewModelFactory: dagger.Lazy<CategoryViewModel.Factory>

    private val viewModel: CategoryViewModel by viewModels {
        viewModelFactory.get()
    }

    override fun onAttach(context: Context) {
        ViewModelProvider(this).get<ArticlesComponentViewModel>()
            .newDetailsComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentCategoryBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.init()

        viewModel.listItem.observe(viewLifecycleOwner) {
            renderData(it)
        }

        binding.frameLoadDishes.visibility = View.VISIBLE

    }

    private fun renderData(it: CategoryAppState) {

        when (it) {
            is CategoryAppState.OnSuccess -> {
                val menu = Menus(arrayListOf())
                menu.dishes.addAll(it.dishes)


                val listDelegates = listOf(
                    CategoriesDelegate(it.menuCategory, it.listMenu),
                    DishesDelegate(menu, it.listDishes),
                )

                mainAdapter.submitList(listDelegates)
                binding.recyclerViewDishes.adapter = mainAdapter
                binding.frameLoadDishes.visibility = View.GONE
            }

            is CategoryAppState.SetDishes -> {
                val menu = Menus(arrayListOf())
                menu.dishes.addAll(it.dishes)

                val listDelegates = listOf(

                    CategoriesDelegate(it.menuCategory, it.listMenu),
                    DishesDelegate(menu, it.listDishes),
                )

                mainAdapter.submitList(listDelegates)
            }

            is CategoryAppState.Error -> {
                Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
            }

            is CategoryAppState.ShowDialogFragment -> {
                showFragmentDialog(it.dish)
            }

            is CategoryAppState.UnAvailable -> {
                Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
            }

            is CategoryAppState.Losing -> {
                Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
            }

            is CategoryAppState.Lost -> {
                Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
                viewModel.loadDishesFromCache()
            }
        }
    }

    private lateinit var fragmentDialog: DetailFragment

    private fun showFragmentDialog(dish: com.example.domain.entity.Dishe) {
        fragmentDialog = DetailFragment.newInstance(dish)
        fragmentDialog.show(this.childFragmentManager, "tag")

    }
}