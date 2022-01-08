package online.tatarintsev.testforpassport.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import online.tatarintsev.testforpassport.R
import online.tatarintsev.testforpassport.databinding.FragmentImageBinding
import android.content.Intent
import android.net.Uri
import android.text.SpannableString
import android.text.style.UnderlineSpan


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class ImageFragment : Fragment() {

    private var _binding: FragmentImageBinding? = null

    private val args: ImageFragmentArgs by navArgs()
    private var uri: String? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        uri = args.link
        _binding = FragmentImageBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        uri?.let {
            val span = SpannableString(it)
            span.setSpan(UnderlineSpan(), 0, it.length, 0)
            binding.imageLink.text = span

            binding.imageLink.setOnClickListener { _ ->
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
                startActivity(browserIntent)
            }

            Glide.with(view.context)
                .load(uri)
                .error(R.mipmap.ic_launcher)
                .into(binding.image)

        }

        binding.buttonSecond.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}