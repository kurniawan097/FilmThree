package com.cuursoft.filmsubmitthree

import  android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_listmovie.*

class ListMovieFragment : Fragment() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var mAdapter: LisMovieAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_listmovie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        mainViewModel.movie.observe(this, getListMovie)
        mainViewModel.setMovieTvMovie("movie")

        progressBar_movie.visibility = View.VISIBLE

    }

    private val getListMovie = Observer<ArrayList<Movie>> {
            movie ->
            if (movie != null) {
                mAdapter = LisMovieAdapter(movie, context,  object : LisMovieAdapter.OnItemClicked {
                    override fun onItemClick(position: Int){
                        Toast.makeText(context, movie[position].title+" Clicked", Toast.LENGTH_SHORT)
                        val intent = Intent(context, DetailActivity::class.java)
                        intent.putExtra("extra_id_movie", movie[position].id)
                        intent.putExtra("extra_type", "movie")
                        startActivity(intent)
                    }
                })

                fragment_movie.apply {
                    adapter = mAdapter

                    layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                }

                progressBar_movie.visibility = View.GONE

            }
        }



}