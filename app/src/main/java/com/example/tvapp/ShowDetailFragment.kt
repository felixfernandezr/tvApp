package com.example.tvapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tvapp.api.model.ShowModelItem
import com.example.tvapp.database.AppDataBase
import com.example.tvapp.database.ShowDAO
import com.example.tvapp.databinding.FragmentMovieDetailBinding

class ShowDetailFragment : Fragment() {

    private lateinit var binding: FragmentMovieDetailBinding
    private lateinit var show: ShowModelItem

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMovieDetailBinding.inflate(inflater, container, false)

        // Get the movie data passed from the activity or adapter
        arguments?.getParcelable<ShowModelItem>("SHOW_DETAIL")?.let {
            show = it
            displayMovieDetails(show)
        }

        // Handling back button click
        binding.backBtn.setOnClickListener {
            (activity as? ShowList)?.showListLayout()
            requireActivity().supportFragmentManager.popBackStack() // Pop the fragment from the back stack
        }

        return binding.root
    }

    private fun displayMovieDetails(show: ShowModelItem) {
        binding.showTitle.text = show.show?.name ?: "No title available"
        binding.showDescription.text = show.show?.summary ?: "No description available"
    }



    companion object {
        fun newInstance(show: ShowModelItem): ShowDetailFragment {
            val fragment = ShowDetailFragment()
            val bundle = Bundle()
            bundle.putParcelable("SHOW_DETAIL", show) // Pass the movie data to the fragment
            fragment.arguments = bundle
            return fragment
        }
    }
}