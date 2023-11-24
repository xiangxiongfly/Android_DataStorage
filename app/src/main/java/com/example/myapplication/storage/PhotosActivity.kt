package com.example.myapplication.storage

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityPhotosBinding
import com.example.myapplication.getScreenWidth

class PhotosActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityPhotosBinding

    private val mList = arrayListOf<Image>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityPhotosBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        initViews()
        loadImages()
    }

    private fun initViews() {
        viewBinding.rvPhotos.adapter = PhotosAdapter(this, mList)
        viewBinding.rvPhotos.layoutManager = GridLayoutManager(this, 3)
    }

    private fun loadImages() {
        val cursor = contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            null,
            null,
            null,
            "${MediaStore.MediaColumns.DATE_ADDED} desc"
        )
        if (cursor != null) {
            while (cursor.moveToNext()) {
                val id = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns._ID))
                val displayName =
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DISPLAY_NAME))
                val uri =
                    ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
                Log.e("TAG", "查询成功:$displayName $uri")
                mList.add(Image(displayName, uri))
            }
            cursor.close()
            viewBinding.rvPhotos.adapter!!.notifyDataSetChanged()
        }
    }
}

class PhotosAdapter(val mContext: Context, val mList: ArrayList<Image>) :
    RecyclerView.Adapter<PhotosAdapter.ViewHolder>() {
    private val layoutInflater = LayoutInflater.from(mContext)
    private val width: Int by lazy {
        getScreenWidth() / 3
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val displayName = itemView.findViewById<TextView>(R.id.displayName)
        val imageView = itemView.findViewById<ImageView>(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = layoutInflater.inflate(R.layout.item_photo, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val image = mList[position]
        holder.displayName.text = image.display
        Glide.with(mContext).load(image.uri)
            .override(width)
            .into(holder.imageView)
    }

    override fun getItemCount(): Int = mList.size
}

data class Image(val display: String, val uri: Uri)