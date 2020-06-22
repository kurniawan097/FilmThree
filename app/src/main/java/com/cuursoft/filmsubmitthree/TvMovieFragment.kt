package com.cuursoft.filmsubmitthree


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_tvmovie.*



class TvMovieFragment : Fragment() {


    private lateinit var mainViewModel: MainViewModel
    private lateinit var mAdapter: TvMovieAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tvmovie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        mainViewModel.tvMovie.observe(this, getTvMovie)
        mainViewModel.setMovieTvMovie("tv")

        progressBar_tvmovie.visibility = View.VISIBLE

    }

    private val getTvMovie = Observer<ArrayList<TvMovie>> {
            tvMovie ->
            if (tvMovie != null) {
                mAdapter = TvMovieAdapter(tvMovie, context,  object : TvMovieAdapter.OnItemClicked {
                    override fun onItemClick(position: Int){
                        Toast.makeText(context, tvMovie[position].title+" Clicked", Toast.LENGTH_SHORT)
                        val intent = Intent(context, DetailActivity::class.java)
                        intent.putExtra("extra_id_tvmovie", tvMovie[position].id)
                        intent.putExtra("extra_type", "tv")
                        startActivity(intent)
                    }
                })

                fragment_tvmovie.apply {
                    adapter = mAdapter

                    layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                }

                progressBar_tvmovie.visibility = View.GONE

            }
        }


}