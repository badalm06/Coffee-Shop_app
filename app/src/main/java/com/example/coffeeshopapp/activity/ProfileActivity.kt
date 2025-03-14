package com.example.coffeeshopapp.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.coffeeshopapp.R
import com.example.coffeeshopapp.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var user: FirebaseUser

    private val PICK_IMAGE_REQUEST = 1
    private lateinit var profileImageView: ImageView
    private lateinit var sharedPreferences: android.content.SharedPreferences

    private var isEditing = false  // Track edit mode

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        user = firebaseAuth.currentUser!!

        sharedPreferences = getSharedPreferences("UserProfile", Context.MODE_PRIVATE)

        // Bind UI elements
        profileImageView = findViewById(R.id.profile_image)
        val userName = binding.etName
        val userEmail = binding.etEmail
        val userPhone = binding.etPhone

        binding.btnBack.setOnClickListener { finish() }

        // Disable editing initially
        setEditable(false)

        // Auto-fill email from Firebase Authentication
        userEmail.setText(user.email)

        // Load saved profile data
        loadUserProfile()
        loadSavedProfileImage()

        // Edit button
        binding.editBtn.setOnClickListener {
            toggleEditMode()
        }

        // Profile image click (only if in edit mode)
        profileImageView.setOnClickListener {
            if (isEditing) {
                selectProfileImage()
            }
        }

        // Logout button
        binding.logoutBtn.setOnClickListener {
            firebaseAuth.signOut()
            val intent = Intent(this, loginInActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            Toast.makeText(this, "Logout Successfully", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun loadUserProfile() {
        val userId = user.uid
        firestore.collection("users").document(userId).get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    binding.etName.setText(document.getString("name"))
                    binding.etPhone.setText(document.getString("phone"))
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to load profile", Toast.LENGTH_SHORT).show()
            }
    }

    private fun toggleEditMode() {
        isEditing = !isEditing
        setEditable(isEditing)

        if (isEditing) {
            binding.editBtn.text = "Save"
        } else {
            saveUserProfile()
            binding.editBtn.text = "Edit"
        }
    }

    private fun setEditable(editable: Boolean) {
        binding.etName.isEnabled = editable
        binding.etPhone.isEnabled = editable
        binding.etEmail.isEnabled = false // Email remains non-editable
    }

    private fun saveUserProfile() {
        val userId = user.uid
        val userData = mapOf(
            "name" to binding.etName.text.toString(),
            "phone" to binding.etPhone.text.toString()
        )

        firestore.collection("users").document(userId).set(userData)
            .addOnSuccessListener {
                Toast.makeText(this, "Profile updated", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to update profile", Toast.LENGTH_SHORT).show()
            }
    }

    private fun selectProfileImage() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri = data.data
            if (selectedImageUri != null) {
                val imagePath = saveImageToInternalStorage(selectedImageUri)
                if (imagePath != null) {
                    profileImageView.setImageURI(Uri.parse(imagePath))
                    saveProfileImagePath(imagePath)
                } else {
                    Toast.makeText(this, "Failed to save image", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun saveImageToInternalStorage(uri: Uri): String? {
        try {
            val inputStream: InputStream? = contentResolver.openInputStream(uri)
            if (inputStream != null) {
                val file = File(filesDir, "profile_image.jpg")
                val outputStream = FileOutputStream(file)
                inputStream.copyTo(outputStream)
                inputStream.close()
                outputStream.close()
                return file.absolutePath
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    private fun saveProfileImagePath(imagePath: String) {
        val editor = sharedPreferences.edit()
        editor.putString("profile_image_path", imagePath)
        editor.apply()
    }

    private fun loadSavedProfileImage() {
        val savedPath = sharedPreferences.getString("profile_image_path", null)
        if (!savedPath.isNullOrEmpty()) {
            val file = File(savedPath)
            if (file.exists()) {
                profileImageView.setImageURI(Uri.fromFile(file))
            } else {
                profileImageView.setImageResource(R.drawable.chai_background)
            }
        } else {
            profileImageView.setImageResource(R.drawable.chai_background)
        }
    }
}
