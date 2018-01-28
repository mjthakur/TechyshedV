package com.techyshed.techyshedv

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.*
import com.techyshed.techyshedn.Post
import com.techyshed.techyshedv.Adapter.MyRecyclerAdapter
import com.techyshed.volleyk.APIController
import com.techyshed.volleyk.ServiceVolley
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.recycler_items.*
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {

    var list:ArrayList<Post>?=null
    var isScrolling = false
    private var recyclerView: RecyclerView? = null
    //private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: MyRecyclerAdapter? = null

    var progressBar:ProgressBar?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar = findViewById(R.id.progressBar) as ProgressBar
        list = ArrayList()
        recyclerView = findViewById(R.id.recycler) as RecyclerView
        var layoutManager = LinearLayoutManager(applicationContext)
        recyclerView!!.layoutManager = layoutManager
        recyclerView!!.setHasFixedSize(true)

        adapter = MyRecyclerAdapter(this,list!!)
        recyclerView!!.adapter = adapter

        fetchData()

        recyclerView!!.addOnScrollListener(object : RecyclerView.OnScrollListener(){

            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                {
                    isScrolling = true
                }
               // val totalItemCount = recyclerView!!.layoutManager.itemCount
            }

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                //var layout = LinearLayoutManager(this@MainActivity)
                val visible = layoutManager.childCount

                val total = layoutManager.itemCount

                val first = layoutManager.findFirstVisibleItemPosition()

                if(isScrolling && (first + visible == total ))
                {
                    isScrolling = false


                    if(page<max)
                    {
                        Toast.makeText(applicationContext,"Fetching More Posts.....",Toast.LENGTH_LONG).show()

                        fetchData()
                    }
                    else
                    {
                        Toast.makeText(applicationContext,"No More Posts",Toast.LENGTH_LONG).show()
                    }

                }

            }
        })


    }


    fun fetchData(){

        progressBar!!.setVisibility(View.VISIBLE);
        val service = ServiceVolley()

        val apiController = APIController(service)

        val path = "wp-json/wp/v2/media?page="+page

        apiController.getArray(path)
        {
            response ->

            // Parse the result
            Log.i("Response----", "/post request OK! Response9999: $response")
            Log.i("PageNo.----",page.toString())
            var len = response!!.length()
            Log.i("Length---------",len.toString())
            for(i in 0..len-1)
            {
                var jsonObject = response!!.getJSONObject(i)
                var id = jsonObject.getLong("id")
                var date = jsonObject.getString("date_gmt")
                var slug = jsonObject.getString("slug")
                var link = jsonObject.getString("link")
                var url = SplitString(link)

                Log.i("___URL_______",url)

                var tit = jsonObject.getJSONObject("title")
                var title = tit.getString("rendered")

                var media = jsonObject.getString("source_url")

                //var con = jsonObject.getJSONObject("content")
                //var content = con.getString("rendered")

                //var exc = jsonObject.getJSONObject("excerpt")
                //var excerpt = exc.getString("rendered")


                list!!.add(Post(id,date,slug,url,title,media))

            }

            page++
            adapter!!.notifyDataSetChanged()
            progressBar!!.setVisibility(View.GONE);

        }

    }


    fun SplitString(str:String):String
    {
        var array = str.split("/")
        var string = StringBuilder(array[0]+"/" + array[1]+"/" + array[2]+"/" + array[3]+"/")
        return string.toString()
    }

    companion object {
        var page = 1
        val max = 8
    }
}
