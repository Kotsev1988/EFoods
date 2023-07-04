package com.example.efoods.presentation.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.efoods.R
import com.example.efoods.databinding.FragmentBaseBinding
import com.example.efoods.presentation.viewModel.BaseViewModel
import com.example.efoods.presentation.viewModel.appState.AppStateLocation
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.text.SimpleDateFormat
import java.util.Date

const val REQUEST_CODE = 30

class BaseFragment : Fragment() {

    private var _binding: FragmentBaseBinding? = null
    private val binding get() = _binding!!

    var city = ""

    private val viewModel: BaseViewModel by lazy {
        ViewModelProvider(this)[BaseViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentBaseBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomNavigationView =
            view.findViewById<BottomNavigationView>(R.id.mainBottomNavigationView)

        val navController = (childFragmentManager.findFragmentById(R.id.mainContainerView)
                as NavHostFragment).navController

        bottomNavigationView.setupWithNavController(navController)

        viewModel.liveDataLocation.observe(viewLifecycleOwner) {
            renderData(it)
        }

        //checkPermission()
//        childFragmentManager.setFragmentResultListener(KAY_PARENT, this) { _, result ->
//
//            var resultFromBundle = result.getString("headerText")
//
//            if (resultFromBundle == "location") {
//                if (city == "") {
//
//                    binding.kitchenNameHeader.visibility = View.GONE
//                    binding.myTime.visibility = View.VISIBLE
//                    binding.myLocation.visibility = View.VISIBLE
//
//                    val sdf = SimpleDateFormat("dd MMMM, yyyy")
//                    val currentDateAndTime = sdf.format(Date())
//
//                    binding.myLocation.text = city
//                    binding.myTime.text = currentDateAndTime
//                    binding.backButton.setImageResource(R.drawable.ic_location)
//                    binding.backButton.setOnClickListener(null)
//                } else {
//                    binding.myTime.visibility = View.VISIBLE
//                    binding.myLocation.visibility = View.VISIBLE
//                    binding.kitchenNameHeader.visibility = View.GONE
//
//                    val sdf = SimpleDateFormat("dd MMMM, yyyy")
//                    val currentDateAndTime = sdf.format(Date())
//
//                    binding.myLocation.text = city
//                    binding.myTime.text = currentDateAndTime
//                    binding.backButton.setImageResource(R.drawable.ic_location)
//                    binding.backButton.setOnClickListener(null)
//                }
//            } else {
//
//                binding.kitchenNameHeader.visibility = View.VISIBLE
//                binding.myLocation.visibility = View.GONE
//                binding.myTime.visibility = View.GONE
//                binding.backButton.setImageResource(R.drawable.ic_back)
//
//                binding.backButton.setOnClickListener(clickListener)
//
//                binding.kitchenNameHeader.text = result.getString("headerText")
//            }
//        }
    }

    private fun renderData(it: AppStateLocation) {

        when (it) {
            is AppStateLocation.Success -> {
                city = it.cities
                binding.myLocation.text = it.cities
                val sdf = SimpleDateFormat("dd MMMM, yyyy")

                val currentDateAndTime = sdf.format(Date())
                binding.myTime.text = currentDateAndTime
            }

            is AppStateLocation.EmptyData -> {
                context?.let { context ->
                    showDialog(
                        context.getString(R.string.dialog_title_gps_turnoff),
                        context.getString(R.string.dialog_message_last_location_unknown)
                    )
                }
            }

            is AppStateLocation.ShowRationalDialog -> {
                showRationaleDialog()
            }

            is AppStateLocation.Error -> {

            }
        }

    }

    private fun checkPermission() {
        requireActivity().let {
            when {
                ContextCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED -> {
                    viewModel.getLocation()
                }

                shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                    showRationaleDialog()
                }

                else -> {
                    requestPermission()
                }
            }
        }
    }

    private fun requestPermission() {
        requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE)
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        when (requestCode) {
            REQUEST_CODE -> {
                var grantedPermission = 0;
                if (grantResults.isNotEmpty()) {
                    for (i in grantResults) {
                        if (i == PackageManager.PERMISSION_GRANTED) {
                            grantedPermission++
                        }
                    }
                    if (grantResults.size == grantedPermission) {
                        viewModel.getLocation()
                    } else {
                        showDialog(
                            getString(R.string.dialog_title_no_gps),
                            getString(R.string.dialog_message_no_gps)
                        )
                    }
                } else {
                    showDialog(
                        getString(R.string.dialog_title_no_gps),
                        getString(R.string.dialog_message_no_gps)
                    )
                }
            }
        }
    }

    private fun showDialog(tile: String, message: String) {

        requireContext().let {
            AlertDialog.Builder(it)
                .setTitle(tile)
                .setMessage(message)
                .setNegativeButton(R.string.dialog_button_close) { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }

    private fun showRationaleDialog() {
        requireActivity().let {
            AlertDialog.Builder(it)
                .setTitle(R.string.dialog_rationale_title)
                .setMessage(R.string.dialog_rationale_meaasge)
                .setPositiveButton(it.getString(R.string.dialog_rationale_give_access))
                { _, _ ->
                    requestPermission()
                }
                .setNegativeButton(R.string.dialog_rationale_decline) { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }
}