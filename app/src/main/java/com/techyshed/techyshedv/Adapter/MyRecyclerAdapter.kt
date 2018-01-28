package com.techyshed.techyshedv.Adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.techyshed.techyshedn.Post
import com.techyshed.techyshedv.DetailsActivity
import com.techyshed.techyshedv.R
import kotlinx.android.synthetic.main.recycler_items.view.*

/**
 * Created by Mj 2 on 28-Jan-18.
 */

class MyRecyclerAdapter(private val context:Context, private val list:ArrayList<Post>) : RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder>(){

    /*var list:ArrayList<Post>?=null
    var context:Context?=null
    constructor(context: Context, list:ArrayList<Post>) : this() {
        this.context = context
        this.list = list
    }*/

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyViewHolder {

        val view = LayoutInflater.from(parent!!.context)
                .inflate(R.layout.recycler_items, null, false);
        view.setLayoutParams(RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder?, position: Int) {

        var item = list[position]
        holder!!.id!!.text = list[position].id.toString()
        holder!!.date!!.text = list[position].date
        holder!!.title!!.text = list[position].title

        Glide.with(context).load(list[position].image)
                .placeholder(R.drawable.techyshed)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .fitCenter()
                .into(holder.image);

        holder.image!!.setOnClickListener(View.OnClickListener {

            Toast.makeText(context,"Clicked",Toast.LENGTH_LONG).show()
            var intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra("link",item.link)
            context.startActivity(intent)

        })
        //holder!!.excerpt!!.text = list[position].excerpt
        //holder!!.date.text = list[position].date
    }

    inner class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {

        internal var id : TextView?=null
        internal var date : TextView?=null
        internal var title : TextView?=null
        internal var image : ImageView?=null

        init {
            id = itemView.findViewById(R.id.tvID) as TextView
            date = itemView.findViewById(R.id.tvDate) as TextView
            title = itemView.findViewById(R.id.tvTitle) as TextView
            image = itemView.findViewById(R.id.iv) as ImageView
        }

    }


}