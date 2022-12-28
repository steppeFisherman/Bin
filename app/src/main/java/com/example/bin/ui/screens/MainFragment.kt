package com.example.bin.ui.screens

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.example.bin.R
import com.example.bin.databinding.FragmentMainBinding
import com.example.bin.ui.BaseFragment
import com.example.bin.ui.MapsActivity
import com.example.bin.ui.WebViewActivity
import com.example.bin.ui.model.DataUi
import com.example.bin.utils.TextWatcher
import com.example.bin.utils.snackLong
import com.example.bin.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>() {

    private val vm by viewModels<MainFragmentViewModel>()
    private lateinit var bin: DataUi

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentMainBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vm.bin.observe(viewLifecycleOwner) { dataUi ->
            bin = dataUi
            displayResult(dataUi)
        }

        vm.error.observe(viewLifecycleOwner) { errorType ->
            when (errorType.ordinal) {
                0 -> view.snackLong(R.string.no_connection_exception_message)
                1 -> view.snackLong(R.string.http_exception_message)
                2 -> view.snackLong(R.string.database_exception_message)
                3 -> view.snackLong(R.string.generic_exception_message)
            }
        }

        vm.loading.observe(viewLifecycleOwner) { loading ->
            if (loading) binding.progressBar.visible(true)
            else {
                binding.progressBar.visible(false)
                binding.binCardGroup.visible(true)
            }
        }

        binding.binCardLayout.bankUrlMain.setOnClickListener {
            if (bin.url.isNotBlank()) {
                val intent = Intent(view.context, WebViewActivity::class.java)
                intent.putExtra("url", bin.url)
                startActivity(intent)
            }
        }

        binding.binCardLayout.bankPhoneMain.setOnClickListener {
            if (bin.phone.isNotBlank()) {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:${binding.binCardLayout.bankPhoneMain.text}")
                ContextCompat.startActivity(view.context, intent, null)
            }
        }

        binding.binCardLayout.btnLocationMain.setOnClickListener {
            if (bin.latitude != 0 && bin.longitude != 0) {
                val intent = Intent(view.context, MapsActivity::class.java)
                intent.putExtra("bin", bin)
                startActivity(intent)
            }
        }
    }

    private fun displayResult(dataUi: DataUi) {
        binding.binCardLayout.apply {
            bankName.text = dataUi.nameBank
            bankUrlMain.text = dataUi.url
            bankPhoneMain.text = dataUi.phone
            schemeType.text = dataUi.scheme
            brandType.text = dataUi.brand
            lengthValue.text = dataUi.length.toString()
            if (dataUi.luhn) luhnYes.setTextColor(Color.BLACK)
            else luhnNo.setTextColor(Color.BLACK)
            if (dataUi.type == DEBIT_TYPE) typeDebit.setTextColor(Color.BLACK)
            else typeCredit.setTextColor(Color.BLACK)
            if (dataUi.prepaid) prepaidYes.setTextColor(Color.BLACK)
            else prepaidNo.setTextColor(Color.BLACK)
            countryName.text = dataUi.nameCountry
            latMain.text = getString(R.string.lat_placeHolder, dataUi.latitude.toString())
            lngMain.text = getString(R.string.lng_placeHolder, dataUi.longitude.toString())
        }
    }

    override fun onResume() {
        super.onResume()

        if (binding.editTextBin.length() < 6) binding.binCardGroup.visible(false)

        binding.editTextBin.addTextChangedListener(TextWatcher { textChanged ->
            when (textChanged?.length) {
                in 0..5 -> binding.binCardGroup.visible(false)
                6 -> vm.fetch(binNumber = textChanged.toString())
            }
        })
    }

    companion object {
        private const val DEBIT_TYPE = "debit"
    }
}
