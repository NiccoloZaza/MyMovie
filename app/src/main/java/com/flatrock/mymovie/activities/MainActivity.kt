package com.flatrock.mymovie.activities

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.flatrock.glib.activities.BaseCompatActivity
import com.flatrock.glib.extensions.ifNotNull
import com.flatrock.glib.extensions.setOnPageSelectedListener
import com.flatrock.glib.views.FancySelector
import com.flatrock.glib.views.SelectableItem
import com.flatrock.mymovie.R
import com.flatrock.mymovie.adapters.viewpageradapters.MainPagerAdapter
import com.flatrock.mymovie.enums.Filters
import com.flatrock.mymovie.fragments.BaseMoviesFragment
import com.flatrock.mymovie.fragments.FavoriteMoviesFragment
import com.flatrock.mymovie.fragments.PopularMoviesFragment
import com.flatrock.mymovie.fragments.TopMoviesFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseCompatActivity() {
    override val layoutId: Int
        get() = R.layout.activity_main

    private var currentBottomNavSelection: Int = R.id.navigation_main

    private val fancySelector: FancySelector<Filters>? get() {
        return try {
            top_popular_selector as FancySelector<Filters>
        } catch (exception: Exception) {
            return null
        }
    }

    private val mainPagerFragments: ArrayList<Fragment> = arrayListOf(
        TopMoviesFragment(),
        PopularMoviesFragment(),
        FavoriteMoviesFragment()
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initFancySelector()
        initBottomNav()
        initMainPager()
    }

    private fun initMainPager() {
        main_pager.offscreenPageLimit = mainPagerFragments.size
        main_pager.adapter = MainPagerAdapter(supportFragmentManager, mainPagerFragments)
        main_pager.setOnPageSelectedListener {
            onPageSelected {
                val fragment = mainPagerFragments[it]
                if (fragment is BaseMoviesFragment)
                    fragment.initPage()
            }
        }
    }

    private fun initBottomNav() {
        main_bottom_nav.setOnNavigationItemSelectedListener {
            if (it.itemId != currentBottomNavSelection) {
                currentBottomNavSelection = it.itemId
                if (it.itemId == R.id.navigation_main) {
                    fancySelector?.selectedIdentifier?.ifNotNull { filter ->
                        initPageByFilter(filter)
                    }
                    top_popular_selector.animate()?.cancel()
                    top_popular_selector.clearAnimation()
                    top_popular_selector.alpha = 0.0f
                    top_popular_selector.visibility = View.VISIBLE
                    top_popular_selector.post {
                        top_popular_selector.animate()
                            .alpha(1f)
                            .setDuration(300)
                            .setListener(null)
                    }
                } else {
                    initPageByFilter(Filters.Favorite)
                    top_popular_selector.animate()?.cancel()
                    top_popular_selector.clearAnimation()
                    top_popular_selector.post {
                        top_popular_selector.animate()
                            .alpha(0f)
                            .setDuration(300)
                            .setListener(object : AnimatorListenerAdapter() {
                                override fun onAnimationEnd(animation: Animator?) {
                                    top_popular_selector.visibility = View.INVISIBLE
                                }
                            })
                    }
                }
            }
            true
        }
    }

    private fun initFancySelector() {
        top_popular_selector.addItems(
            arrayListOf(
                SelectableItem(
                    Filters.TopRated,
                    R.drawable.ic_top
                ),
                SelectableItem(
                    Filters.Popular,
                    R.drawable.ic_popular
                )
            ),
            0
        )

        top_popular_selector.setOnItemSelectListener {
            if (it != null && it is Filters) {
                initPageByFilter(it)
            }
        }
    }

    private fun initPageByFilter(filter: Filters) {
        when (filter) {
            Filters.Popular -> {
                main_pager.setCurrentItem(1, false)
                main_header.text = getString(R.string.popular_movies)
            }
            Filters.TopRated -> {
                main_pager.setCurrentItem(0, false)
                main_header.text = getString(R.string.top_rated_movies)
            }
            else -> {
                main_pager.setCurrentItem(2, false)
                main_header.text = getString(R.string.favorites)
            }
        }
    }
}