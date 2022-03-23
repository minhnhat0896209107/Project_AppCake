package com.nguyenvanminhnhat.projectcakeapp.view.blog.add

import android.app.Activity.RESULT_OK
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.nguyenvanminhnhat.projectcakeapp.R
import com.nguyenvanminhnhat.projectcakeapp.const.Constant.Companion.BASE_FIREBASE_URL
import com.nguyenvanminhnhat.projectcakeapp.const.onHideKeyBoard
import kotlinx.android.synthetic.main.fragment_add.*
import java.util.*
import kotlin.collections.HashMap


class AddFragment : Fragment() {
    private val imgBack = 1
    lateinit var storage : StorageReference
    lateinit var imgData : Uri
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        storage = FirebaseStorage.getInstance().reference.child("ImageBlog")
        ivBlog.setOnClickListener {
            choosePicture()
        }
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
        val progress : ProgressDialog = ProgressDialog(context)
        progress.setTitle("Đang đăng ảnh")
        progress.show()
        var randomId : String = UUID.randomUUID().toString()
        val imgName : StorageReference = storage.child("image$randomId")
        imgName.putFile(imgData).addOnSuccessListener(OnSuccessListener {
                t->
            progress.dismiss()
            imgName.downloadUrl.addOnSuccessListener {
                val databaseReference: DatabaseReference
                        = FirebaseDatabase.getInstance()
                    .getReferenceFromUrl(BASE_FIREBASE_URL)
                    .child("ImageBlog").push()
                val hashMap: HashMap<String, String> = HashMap()
                hashMap["imageUrl"] = it.toString()
                hashMap["title"] = titleStr
                hashMap["description"] = descriptStr
                databaseReference.setValue(hashMap)
                Toast.makeText(context, "Đăng tải bài viết thành công", Toast.LENGTH_SHORT).show()
                edtTitle.setText("")
                edtDescription.setText("")
                ivBlog.setImageResource(R.drawable.default_image)
            }.addOnFailureListener {
                Toast.makeText(context, "Đăng ảnh thất bại", Toast.LENGTH_SHORT).show()
            }
        })
            .addOnProgressListener {
                var progressPercent = (100.0 * it.bytesTransferred / it.totalByteCount)
                progress.setMessage("Đang tải: ${progressPercent.toInt()} %")
            }
    }
}