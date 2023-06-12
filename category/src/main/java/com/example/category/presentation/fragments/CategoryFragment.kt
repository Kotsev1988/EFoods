package com.example.category.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment.STYLE_NORMAL
import androidx.fragment.app.DialogFragment.STYLE_NO_FRAME
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModelProvider
import com.example.category.R
import com.example.category.databinding.FragmentCategoryBinding
import com.example.category.di.DaggerCategoryComponent
import com.example.category.presentation.adapters.MainAdapter
import com.example.category.presentation.adapters.dishes.DishesDelegate
import com.example.category.presentation.adapters.dishes.DishesDelegateAdapter
import com.example.category.presentation.adapters.menus.CategoriesDelegate
import com.example.category.presentation.adapters.menus.CategoriesDelegateAdapter
import com.example.category.presentation.view.ListDishes
import com.example.category.presentation.view.ListMenu
import com.example.category.presentation.viewmodel.CategoryViewModel
import com.example.category.presentation.viewmodel.appState.CategoryAppState
import com.example.core.domain.entity.Dishe
import com.example.core.domain.entity.MenuCategory
import com.example.core.domain.entity.Menus
import com.example.core.utils.InjectUtils
import javax.inject.Inject

const val KAY_PARENT_DIALOG = "key_parent_dialog"
const val KAY_PARENT = "key_parent"
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
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: CategoryViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[CategoryViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerCategoryComponent.factory()
            .create(InjectUtils.provideBaseComponent(requireActivity().applicationContext))
            .inject(this)
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        val id = arguments?.getString("metadataFileSyncFilter")
        val bundleBaseFrag = Bundle()
        bundleBaseFrag.putString("headerText", id)
        parentFragment?.setFragmentResult(KAY_PARENT, bundleBaseFrag)

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
            is CategoryAppState.OnSuccess ->{
                val menu = Menus(arrayListOf())
                menu.dishes.addAll(it.dishes)


              val  listDelegates = listOf(
                    CategoriesDelegate(it.menuCategory, it.listMenu),
                    DishesDelegate(menu, it.listDishes),
                )

                mainAdapter.submitList(listDelegates)
                binding.recyclerViewDishes.adapter = mainAdapter
                binding.frameLoadDishes.visibility = View.GONE
            }

            is CategoryAppState.SetDishes ->{
                val menu = Menus(arrayListOf())
                menu.dishes.addAll(it.dishes)

                val  listDelegates = listOf(

                    CategoriesDelegate(it.menuCategory, it.listMenu),
                    DishesDelegate(menu, it.listDishes),
                )

                mainAdapter.submitList(listDelegates)
            }

            is CategoryAppState.Error ->{
                Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
            }

            is CategoryAppState.ShowDialogFragment ->{
                showFragmentDialog(it.dish)
            }
        }
    }

    private lateinit var fragmentDialog: DetailFragment

    private fun showFragmentDialog(dish: Dishe) {
        fragmentDialog = DetailFragment.newInstance(dish)
        fragmentDialog.show(this.childFragmentManager, "tag")

    }



}