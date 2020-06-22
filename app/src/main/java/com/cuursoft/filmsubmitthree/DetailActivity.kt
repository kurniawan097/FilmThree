package com.cuursoft.filmsubmitthree

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.loopj.android.http.RequestParams
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.activity_detail.*
import org.json.JSONObject

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        progressBar_detailmovie.visibility = View.VISIBLE

        val type = intent.getStringExtra("extra_type")
        val params = RequestParams()
        val constants = Constants()
        params.put("api_key", constants.TMDB_API_KEY)
        params.put("language", "en-US")
        val client = AsyncHttpClient()
        val url = "https://api.themoviedb.org/3/discover/" + type

        if (type == "movie") {
            val id = intent.getStringExtra("extra_id_movie")
            Log.d("TES123", "id = "+id)
            client.get(url, params, object : AsyncHttpResponseHandler() {

                override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                    try {
                        val result = String(responseBody)
                        val responseObject = JSONObject(result)
                        val list = responseObject.getJSONArray("results")
                        for (i in 0 until list.length()) {
                            if (list.getJSONObject(i).getString("id") == id) {
                                val movie = Movie(list.getJSONObject(i))
                                tv_rilis.text = getString(R.string.release_date)
                                tv_skor.text = getString(R.string.user_score)
                                tv_desk.text = getString(R.string.overview)

                                tv_judul_detail.text = movie.title
                                tv_tanggal_rilis.text = movie.releaseDate
                                tv_nilai_skor.text = movie.score
                                tv_dekripsi_detail.text = movie.description

                                Glide.with(applicationContext).load("https://image.tmdb.org/t/p/w185" + movie.poster)
                                    .into(poster_detail_movie)
                            }

                        }
                    } catch (e: Exception) {
                        Log.d("Exception", e.message)
                    }

                    progressBar_detailmovie.visibility = View.GONE

                }

                override fun onFailure(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    responseBody: ByteArray?,
                    error: Throwable?
                ) {
                    Toast.makeText(applicationContext, error!!.message, Toast.LENGTH_SHORT)
                }

            })
        }
        else if (type == "tv"){
            val id = intent.getStringExtra("extra_id_tvmovie")
            client.get(url, params, object : AsyncHttpResponseHandler() {

                override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                    try {
                        val result = String(responseBody)
                        val responseObject = JSONObject(result)
                        val list = responseObject.getJSONArray("results")
                        for (i in 0 until list.length()) {
                            if (list.getJSONObject(i).getString("id") == id) {
                                val tvMovie = TvMovie(list.getJSONObject(i))
                                tv_rilis.text = getString(R.string.first_air_date)
                                tv_skor.text = getString(R.string.user_score)
                                tv_desk.text = getString(R.string.overview)

                                tv_judul_detail.text = tvMovie.title
                                tv_tanggal_rilis.text = tvMovie.releaseDate
                                tv_nilai_skor.text = tvMovie.score
                                tv_dekripsi_detail.text = tvMovie.description

                                Glide.with(applicationContext).load("https://image.tmdb.org/t/p/w185" + tvMovie.poster)
                                    .into(poster_detail_movie)
                            }

                        }
                    } catch (e: Exception) {
                        Log.d("Exception", e.message)
                    }

                    progressBar_detailmovie.visibility = View.GONE


                }

                override fun onFailure(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    responseBody: ByteArray?,
                    error: Throwable?
                ) {
                    Toast.makeText(applicationContext, error!!.message, Toast.LENGTH_SHORT)

                }

            })
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}