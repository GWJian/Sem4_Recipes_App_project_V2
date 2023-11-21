package com.gwj.recipesappV2

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.gwj.recipesappV2.core.service.AuthService
import com.gwj.recipesappV2.core.util.NetworkManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var authService: AuthService
    lateinit var navController: NavController
    private lateinit var dialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val backgroundColor = ResourcesCompat.getColor(resources, R.color.gray_gray, null)
        window.decorView.setBackgroundColor(backgroundColor)

        navController = findNavController(R.id.navHostFragment)
        // Get the current user from AuthService
        val currentUser = authService.getCurrentUser()
        // If the user is already logged in, navigate to the TabContainerFragment
        if (currentUser != null) {
            navController.navigate(R.id.tabContainerFragment)
        }

        //====================== No Connection Start =====================================
        dialog = MaterialAlertDialogBuilder(this, R.style.MaterialAlertDialog_Rounded)
            .setView(R.layout.network_dialog)
            .setCancelable(false)
            .create()

        val networkManager = NetworkManager(this)
        networkManager.observe(this) {
            if (!it) {
                if (!dialog.isShowing) dialog.show()
            } else {
                if (dialog.isShowing) dialog.hide()
            }
        }

        window.statusBarColor = Color.BLACK;
        //====================== No Connection End =====================================
    }
}