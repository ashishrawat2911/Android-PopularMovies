package com.example.android_popularmovies.presentation.movie.view


import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.android_popularmovies.R
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config


@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class MainActivityTest {
    private var activity: MainActivity? = null
    private lateinit var movieListFragment: MovieListFragment
    private lateinit var movieDetailFragment: MovieDetailFragment

    @Before
    @Throws(Exception::class)
    fun setUp() {
        movieListFragment = MovieListFragment()
        movieDetailFragment = MovieDetailFragment()

        activity = Robolectric.buildActivity(MainActivity::class.java)
            .create()
            .resume()
            .get()

        if (activity != null) {
            activity!!
                .supportFragmentManager
                .beginTransaction()
                .add(movieListFragment, null)
                .commit()
        }
        Robolectric.flushForegroundThreadScheduler()
    }

    @Test
    @Throws(Exception::class)
    fun activityShouldNotBeNull() {
        assert(activity != null)
    }

    @Test
    @Throws(Exception::class)
    fun testMovieListFragmentAdded() {
        assert(movieListFragment.isAdded)
    }


    @Test
    @Throws(Exception::class)
    fun testItemsInRecyclerView() {
        val recycler = movieListFragment.view!!.findViewById(R.id.recyclerView) as RecyclerView
        recycler.measure(
            View.MeasureSpec.UNSPECIFIED,
            View.MeasureSpec.UNSPECIFIED
        )
        recycler.layout(0, 0, 100, 1000)
        val adapter = recycler.adapter
        assert(adapter?.itemCount != 0)
    }
}