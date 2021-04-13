package com.example.project4221

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.project4221.Fragment.MyFavoriteFragment
import com.example.project4221.Fragment.NowPlayingFragment
import com.example.project4221.Fragment.TopRatingFragment
import com.example.project4221.Room.MoviesDatabase
import com.google.android.material.bottomnavigation.BottomNavigationView

const val TYPE_VIEW = "TYPE_VIEW"

class MainActivity : AppCompatActivity(){
    lateinit var nowPlayingFragment: NowPlayingFragment
    lateinit var topRatingFragment: TopRatingFragment
    lateinit var myFavoriteFragment: MyFavoriteFragment
    lateinit var database: MoviesDatabase
    companion object {
        private val TAG = MainActivity::class.java.simpleName
        private val PREFERENCE_NAME = "SETTING_FILES"
        val NAME_FRAGMENT_KEY = "NAME_FRAGMENT_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        database = MoviesDatabase.invoke(this)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)


        val preference = getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
        val prefEditor = preference.edit()

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val fragmentKey = preference.getString(NAME_FRAGMENT_KEY, "NOW_PLAYING")
        if (fragmentKey == "NOW_PLAYING"){
            setCurrentFragment(
                NowPlayingFragment(
                    false
                , database), tag = "NOW_PLAYING")
            bottomNavigation.selectedItemId = R.id.NowPlaying
        }else if (fragmentKey == "TOP_RATING"){
            setCurrentFragment(
                TopRatingFragment(
                    false,
                    database
                ), tag = "TOP_RATNG")
            bottomNavigation.selectedItemId = R.id.TopRating
        }else if (fragmentKey == "MY_FAVORITE"){
            setCurrentFragment(
                MyFavoriteFragment(
                    false, database
                ), tag = "MY_FAVORITE")
            bottomNavigation.selectedItemId = R.id.MyFavorite
        }


        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.NowPlaying -> {
                    setCurrentFragment(
                        NowPlayingFragment(
                            false
                        , database), "NOW_PLAYING")
                    prefEditor.putString(NAME_FRAGMENT_KEY, "NOW_PLAYING")
                    prefEditor.apply()
                }
                R.id.TopRating -> {
                    setCurrentFragment(
                        TopRatingFragment(
                            false,
                            database
                        ), "TOP_RATNG")
                    prefEditor.putString(NAME_FRAGMENT_KEY, "TOP_RATING")
                    prefEditor.apply()
                }
                R.id.MyFavorite -> {
                    setCurrentFragment(
                        MyFavoriteFragment(
                            false,
                            database
                        ), "MY_FAVORITE")
                    prefEditor.putString(NAME_FRAGMENT_KEY, "MY_FAVORITE")
                    prefEditor.apply()
                }
            }
            true
        }

    }

    private fun setCurrentFragment(fragment: Fragment, tag: String){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment,fragment, tag)
                    .addToBackStack(null)
            commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.item_menu, menu)
        return true;
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var itemview: Int = item.itemId
        when(itemview){
            R.id.btn_grid -> {
                val nowPlaying = supportFragmentManager.findFragmentByTag("NOW_PLAYING")
                if (nowPlaying != null && nowPlaying.isVisible){
                    nowPlayingFragment =
                        NowPlayingFragment(true, database)
                    supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.flFragment, nowPlayingFragment, "NOW_PLAYING")
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .commit()
                }
                val topRating = supportFragmentManager.findFragmentByTag("TOP_RATNG")
                if (topRating != null && topRating.isVisible){
                    topRatingFragment =
                        TopRatingFragment(true, database)
                    supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.flFragment, topRatingFragment, "TOP_RATNG")
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .commit()
                }
                val myFavorite = supportFragmentManager.findFragmentByTag("MY_FAVORITE")
                if (myFavorite != null && myFavorite.isVisible){
                    myFavoriteFragment =
                        MyFavoriteFragment(true, database)
                    supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.flFragment, myFavoriteFragment, "MY_FAVORITE")
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .commit()
                }
            }
            R.id.btn_linear -> {
                val nowPlaying = supportFragmentManager.findFragmentByTag("NOW_PLAYING")
                if (nowPlaying != null && nowPlaying.isVisible){
                    nowPlayingFragment =
                        NowPlayingFragment(false, database)
                    supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.flFragment, nowPlayingFragment,"NOW_PLAYING")
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .commit()
                }
                val topRating = supportFragmentManager.findFragmentByTag("TOP_RATNG")
                if (topRating != null && topRating.isVisible){
                    topRatingFragment =
                        TopRatingFragment(false, database)
                    supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.flFragment, topRatingFragment,"TOP_RATNG")
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .commit()
                }
                val myFavorite = supportFragmentManager.findFragmentByTag("MY_FAVORITE")
                if (myFavorite != null && myFavorite.isVisible){
                    myFavoriteFragment =
                        MyFavoriteFragment(false, database)
                    supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.flFragment, myFavoriteFragment,"MY_FAVORITE")
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .commit()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}