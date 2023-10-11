package com.example.inactivitydetector

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity(), UserInactivityDetector.Listener {
    private lateinit var userInactivityDetector: UserInactivityDetector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userInactivityDetector = UserInactivityDetector(5000, this)

    }

    override fun onResume() {
        super.onResume()
        // Start monitoring user activity when the activity is resumed
        userInactivityDetector.startMonitoring()
    }

    override fun onPause() {
        super.onPause()
        // Stop monitoring user activity when the activity is paused
        userInactivityDetector.stopMonitoring()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        // Handle touch events here
        userInactivityDetector.onUserInteraction()// Reset the inactivity timer
        return super.onTouchEvent(event)
    }

    override fun onUserInactive() {
        // This method is called when user inactivity is detected
        // You can perform actions like showing a dialog, logging out the user, etc.
        // For example, show an AlertDialog when the user is inactive for 5 minutes
        showInactiveDialog()
    }

    private fun showInactiveDialog() {
        // Implement your code to show a dialog to the user when inactive
        // You can customize the dialog's appearance and behavior here
        val dialog = AlertDialog.Builder(this)
            .setTitle("Inactive Session")
            .setMessage("You have been inactive for a while.")
            .setCancelable(false)
            .setPositiveButton("OK") { _, _ ->
                userInactivityDetector.stopMonitoring()
                userInactivityDetector.startMonitoring()
                // Handle the user's response, e.g., logout or dismiss the dialog
            }
            .create()

        dialog.show()
    }

}