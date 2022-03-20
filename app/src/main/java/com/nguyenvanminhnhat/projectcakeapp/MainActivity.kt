package com.nguyenvanminhnhat.projectcakeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var drawerLayout: DrawerLayout? = null
    private var navigationView: NavigationView? = null
    private var bottomNavigationView: BottomNavigationView? = null
    private lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        setContentView(R.layout.activity_main)


        drawerLayout = findViewById(R.id.drawer_layout)
        val navController = findNavController(R.id.fragment_host)
        appBarConfiguration = AppBarConfiguration(setOf( R.id.home, R.id.favourite, R.id.blog, R.id.chat, R.id.profile))

        bottomNavigation.setupWithNavController(navController)


    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragment_host)
        return NavigationUI.navigateUp(navController, drawer_layout)
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.home ->
                Navigation.findNavController(this,R.id.fragment_host).navigate(R.id.home)
            R.id.favourite ->
                Navigation.findNavController(this,R.id.fragment_host).navigate(R.id.favourite)
            R.id.blog ->
                Navigation.findNavController(this,R.id.fragment_host).navigate(R.id.blog)
            R.id.chat ->
                Navigation.findNavController(this, R.id.fragment_host).navigate(R.id.chat)
            R.id.profile ->
                Navigation.findNavController(this, R.id.fragment_host).navigate(R.id.profile)

        }
        return super.onOptionsItemSelected(item)
    }

}