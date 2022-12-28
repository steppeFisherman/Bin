package com.example.bin.ui.screens

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.example.bin.R
import com.example.bin.databinding.FragmentHistoryBinding
import com.example.bin.ui.BaseFragment
import com.example.bin.ui.MapsActivity
import com.example.bin.ui.WebViewActivity
import com.example.bin.ui.adapters.HistoryFragmentAdapter
import com.example.bin.ui.model.DataUi
import com.example.bin.utils.snackLong
import com.example.bin.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment : BaseFragment<FragmentHistoryBinding>() {

    private val vm by viewModels<HistoryFragmentViewModel>()

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentHistoryBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = HistoryFragmentAdapter(object : HistoryFragmentAdapter.Listener {

            override fun toUrl(bin: DataUi) {
                if (bin.url.isNotBlank()) {
                    val intent = Intent(view.context, WebViewActivity::class.java)
                    intent.putExtra("url", bin.url)
                    startActivity(intent)
                }
            }

            override fun dial(bin: DataUi) {
                if (bin.phone.isNotBlank()) {
                    val intent = Intent(Intent.ACTION_DIAL)
                    intent.data = Uri.parse("tel:${bin.phone}")
                    ContextCompat.startActivity(view.context, intent, null)
                }
            }

            override fun toLocation(bin: DataUi) {
                if (bin.latitude != 0 && bin.longitude != 0) {
                    val intent = Intent(view.context, MapsActivity::class.java)
                    intent.putExtra("bin", bin)
                    startActivity(intent)
                }
            }
        })

        binding.historyFragmentRv.adapter = adapter

        vm.bins.observe(viewLifecycleOwner) { listDataUi ->
            if (listDataUi.isNullOrEmpty()) binding.progressBar.visible(true)
            else {
                adapter.submitList(listDataUi.asReversed())
                binding.progressBar.visible(false)
            }
        }

        vm.error.observe(viewLifecycleOwner) {
            when (it.ordinal) {
                0 -> view.snackLong(R.string.no_connection_exception_message)
                1 -> view.snackLong(R.string.http_exception_message)
                2 -> view.snackLong(R.string.database_exception_message)
                3 -> view.snackLong(R.string.generic_exception_message)
            }
        }
    }
}
