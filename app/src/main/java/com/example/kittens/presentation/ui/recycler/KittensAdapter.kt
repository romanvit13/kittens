package com.example.kittens.presentation.ui.recycler

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.kittens.R
import com.example.kittens.domain.models.Cat

class KittensAdapter(cats: MutableList<Cat>?) : RecyclerView.Adapter<KittensAdapter.ViewHolder>() {
    private val TAG = "RecyclerViewAdapter"
    private var mCats: MutableList<Cat>? = cats

    init {
        Log.i(TAG, "Constructor")
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        Log.i(TAG, "onCreateViewHolder")
        val view: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.layout_listitem, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        Log.i(TAG, "onBindViewHolder")
        val context = viewHolder.mImageView.context
        val glideOptions = RequestOptions()
        Glide.with(context)
            .asBitmap()
            .load(mCats!![viewHolder.adapterPosition].url)
            .apply(glideOptions.centerCrop())
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(viewHolder.mImageView)

        viewHolder.mTextView.text = mCats!![viewHolder.adapterPosition].id
        viewHolder.mImageView.setOnClickListener {
            Log.i(TAG, "OnClick: " + mCats!![viewHolder.adapterPosition].id)
            Toast.makeText(
                context, mCats!![viewHolder.adapterPosition].id,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun getItemCount(): Int {
        return mCats!!.size
    }

    fun clear() {
        mCats!!.clear()
        notifyDataSetChanged()
    }

    fun addAll(cats: MutableList<Cat>) {
        mCats!!.addAll(cats)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mImageView: ImageView
        var mTextView: TextView
        //var mRecyclerView: RelativeLayout

        init {
            mImageView = itemView.findViewById(R.id.imageView)
            mTextView = itemView.findViewById(R.id.imageHeader)
            //mRecyclerView = itemView.findViewById(R.id.kittens_rv)
        }
    }
}