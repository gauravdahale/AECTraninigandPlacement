package com.gtech.aectnp

import android.content.Intent
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.gtech.aectnp.ui.main.SectionsPagerAdapter
import com.gtech.aectnp.databinding.ActivityMainBinding
import com.gtech.aectnp.ui.auth.LoginActivity
import com.gtech.aectnp.ui.notifications.NotificationsFragment
import com.gtech.aectnp.ui.profile.ProfileFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
lateinit var mAuth :FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
mAuth = FirebaseAuth.getInstance()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//if(mAuth.currentUser?.uid == null){
//    val intent = Intent(this,LoginActivity::class.java)
//    startActivity(intent)
//    finish()
//}
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        sectionsPagerAdapter.addFrag(NotificationsFragment(), "Notifications")
        sectionsPagerAdapter.addFrag(ProfileFragment(), "Profile")

        val viewPager: ViewPager = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        tabs.setupWithViewPager(viewPager)
        val fab: FloatingActionButton = binding.fab

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }
}