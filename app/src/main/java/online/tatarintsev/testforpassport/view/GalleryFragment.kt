package online.tatarintsev.testforpassport.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import online.tatarintsev.testforpassport.R
import online.tatarintsev.testforpassport.databinding.FragmentGalleryBinding
import online.tatarintsev.testforpassport.view.adapters.GalleryRecyclerViewAdapter
import online.tatarintsev.testforpassport.viewmodel.GalleryViewModel
import java.util.*
import kotlin.concurrent.schedule

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null

    private val binding get() = _binding!!

    private lateinit var viewModel: GalleryViewModel

    private var adapter: GalleryRecyclerViewAdapter? = null

    // данные для тапа вниз и перегрузки данных по таймеру
    private var xTapped: Float? = null
    private var refreshTimer: Timer? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(GalleryViewModel::class.java)

        adapter = GalleryRecyclerViewAdapter(listOf()) { goToDetailImage(it) }
        val recyclerView: RecyclerView = binding.imagesRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        recyclerView.adapter = adapter

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.uiState.observe(viewLifecycleOwner) {
            it?.let { state ->
                if(state.showProgress) {
                    binding.progressBar.visibility = View.VISIBLE
                } else {
                    binding.progressBar.visibility = View.GONE
                    if (state.error != null) {
                        Snackbar.make(
                            binding.imagesRecyclerView,
                            getString(state.error),
                            Snackbar.LENGTH_LONG
                        ).show()
                    } else {
                        var elevenImages = state.imageList?.toMutableList() ?: mutableListOf()
                        if(elevenImages.size == 12) {
                            elevenImages = elevenImages.take(11).toMutableList()
                        }
                        adapter?.updateItems(elevenImages.toList())
                        binding.imagesRecyclerView.post {
                            adapter?.notifyDataSetChanged()
                        }
                    }
                }
            }
        }

        binding.imagesRecyclerView.setOnTouchListener { _, motionEvent ->
            var result = false
            motionEvent?.let { event ->
                when(event.action) {
                    MotionEvent.ACTION_MOVE -> {
                        if (xTapped == null) {
                            xTapped = event.x
                            result = true
                        }
                    }
                    MotionEvent.ACTION_UP -> {
                        if (xTapped!! > event.x + 1) {
                            refreshData()
                            result = false
                        }
                        xTapped = null
                    }
                    else -> xTapped = null
                }
            }
            result
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun refreshData() {
        refreshTimer?.cancel()
        refreshTimer = Timer()
        refreshTimer?.schedule(700) {
            viewModel.retrieveGalleryList()
        }
    }

    private fun goToDetailImage(uri: String) {
        val bundle = bundleOf("link" to uri.trim())
        findNavController().navigate(R.id.action_GalleryFragment_to_ImageFragment, bundle)
    }
}