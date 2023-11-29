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
        // Create a new MaterialAlertDialogBuilder with the specified style
        dialog = MaterialAlertDialogBuilder(this, R.style.MaterialAlertDialog_Rounded)
            // Set the view of the dialog to be the network_dialog layout
            .setView(R.layout.network_dialog)
            // Set the dialog to be not cancelable, meaning the user can't dismiss it by clicking outside of it or pressing the back button
            .setCancelable(false)
            // Create the AlertDialog from the builder
            .create()

        val networkManager = NetworkManager(this)
        // Observe the network status. The lambda function provided will be invoked whenever the network status changes
        networkManager.observe(this) {
            // 'it' refers to the network status. If the network is not connected (!it)
            if (!it) {
                // If the dialog is not already showing, show it
                if (!dialog.isShowing) dialog.show()
            } else {
                // If the network is connected and the dialog is showing, hide it
                if (dialog.isShowing) dialog.hide()
            }
        }

        window.statusBarColor = Color.BLACK;
        //====================== No Connection End =====================================
    }
}