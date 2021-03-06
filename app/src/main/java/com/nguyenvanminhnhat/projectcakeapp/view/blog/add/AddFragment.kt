package com.nguyenvanminhnhat.projectcakeapp.view.blog.add

import android.app.Activity.RESULT_OK
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.nguyenvanminhnhat.projectcakeapp.R
import com.nguyenvanminhnhat.projectcakeapp.utils.Constant.Companion.BASE_FIREBASE_URL
import com.nguyenvanminhnhat.projectcakeapp.utils.onHideKeyBoard
import com.nguyenvanminhnhat.projectcakeapp.pojo.model.UserModel
import kotlinx.android.synthetic.main.fragment_add.*
import java.util.*
import kotlin.collections.HashMap


class AddFragment : Fragment() {
    private val imgBack = 1
    lateinit var storage : StorageReference
    lateinit var imgData : Uri
    private var rootUser : DatabaseReference ?= null
    private var firebaseUser : FirebaseUser? = null
    private lateinit var userModel : UserModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        ivBlog.setOnClickListener {
            choosePicture()
        }
        btnOnBack.setOnClickListener {
            findNavController().navigate(R.id.action_add_to_blog)
        }
        getUser()
    }

    private fun init() {
        firebaseUser = FirebaseAuth.getInstance().currentUser
        storage = FirebaseStorage.getInstance().reference.child("ImageBlog")
        rootUser = FirebaseDatabase.getInstance().reference.child("Users").child(firebaseUser!!.uid)
    }

    private fun choosePicture(){
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, imgBack)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == imgBack){
            if (resultCode == RESULT_OK){
                imgData = data?.data!!
                ivBlog.setImageURI(imgData)
                btnPost.setOnClickListener {
                    uploadPicture()
                    onHideKeyBoard.hideKeyboard(requireActivity())
                }
            }
        }
    }

    private fun uploadPicture(){
        var titleStr = edtTitle.text.toString().trim()
        var descriptStr = edtDescription.text.toString().trim()

        val progress = ProgressDialog(context)
        progress.setTitle("??ang ????ng b??i vi???t")
        progress.show()
        var countLike = 0
        var randomId : String = UUID.randomUUID().toString()
        for (i in 0..100){
            countLike = i
        }
        val imgName : StorageReference = storage.child("image$randomId")
        imgName.putFile(imgData).addOnSuccessListener(OnSuccessListener { _ ->
            progress.dismiss()
            imgName.downloadUrl.addOnSuccessListener {
                val databaseReference: DatabaseReference
                        = FirebaseDatabase.getInstance()
                    .getReferenceFromUrl(BASE_FIREBASE_URL)
                    .child("Blog").push()
                val hashMap: HashMap<String, String> = HashMap()
                hashMap["imageUrl"] = it.toString()
                hashMap["userName"] = userModel.username.toString()
                hashMap["countLike"] = "${countLike}"
                hashMap["title"] = titleStr
                hashMap["description"] = descriptStr
                databaseReference.setValue(hashMap)
                Toast.makeText(context, "????ng t???i b??i vi???t th??nh c??ng", Toast.LENGTH_SHORT).show()
                edtTitle.setText("")
                edtDescription.setText("")
                ivBlog.setImageResource(R.drawable.default_image)
            }.addOnFailureListener {
                Toast.makeText(context, "????ng ???nh th???t b???i", Toast.LENGTH_SHORT).show()
            }
        })
            .addOnProgressListener {
                var progressPercent = (100.0 * it.bytesTransferred / it.totalByteCount)
                progress.setMessage("??ang t???i: ${progressPercent.toInt()} %")
            }
    }

    fun getUser(){
        rootUser?.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    val userMd = snapshot.getValue(UserModel::class.java)
                    if (userMd != null) {
                        userModel = userMd
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}