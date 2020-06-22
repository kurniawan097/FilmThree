package com.cuursoft.filmsubmitthree


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.loopj.android.http.RequestParams
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import java.util.ArrayList

class MainViewModel : ViewModel() {
    private val listMovie = MutableLiveData<ArrayList<Movie>>()
    private val listTvMovie = MutableLiveData<ArrayList<TvMovie>>()

    internal val movie: LiveData<ArrayList<Movie>>
        get() = listMovie

    internal val tvMovie: LiveData<ArrayList<TvMovie>>
        get() = listTvMovie


    internal fun setMovieTvMovie(type: String) {
        val listItemsMovie = ArrayList<Movie>()
        val listItemsTvMovie = ArrayList<TvMovie>()

        val params = RequestParams()
        val constants = Constants()
        params.put("api_key", constants.TMDB_API_KEY)
        params.put("language", "en-US")
        val client = AsyncHttpClient()
        val url = "https://api.themoviedb.org/3/discover/"+type
        client.get(url, params, object : AsyncHttpResponseHandler() {

            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                try {
                    val result = String(responseBody)
                    val responseObject = JSONObject(result)
                    val list = responseObject.getJSONArray("results")
                    for (i in 0 until list.length()) {
                        if (type == "movie"){
                            val movie = list.getJSONObject(i)
                            val movieItems = Movie(movie)
                            listItemsMovie.add(movieItems)
                            listMovie.postValue(listItemsMovie)

                        }
                        else if (type == "tv"){
                            val tvMovie = list.getJSONObject(i)
                            val tvMovieItems = TvMovie(tvMovie)
                            listItemsTvMovie.add(tvMovieItems)
                            listTvMovie.postValue(listItemsTvMovie)

                        }

                    }
                } catch (e: Exception) {
                    Log.d("Exception", e.message)
                }

            }

            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
                Log.d("onFailure", error.message)
            }
        })
    }

}
