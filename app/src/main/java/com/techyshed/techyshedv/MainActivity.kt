package com.techyshed.techyshedv

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.GridLayout
import android.widget.LinearLayout
import com.techyshed.techyshedn.Post
import com.techyshed.techyshedv.Adapter.MyRecyclerAdapter
import com.techyshed.volleyk.APIController
import com.techyshed.volleyk.ServiceVolley
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.recycler_items.*
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {

    var list:ArrayList<Post>?=null

    private var recyclerView: RecyclerView? = null
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: MyRecyclerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        list = ArrayList()
        recyclerView = findViewById(R.id.recycler) as RecyclerView
        layoutManager = LinearLayoutManager(applicationContext)
        recyclerView!!.layoutManager = layoutManager
        recyclerView!!.setHasFixedSize(true)

        val service = ServiceVolley()

        val apiController = APIController(service)

        val path = "wp-json/wp/v2/media"

        apiController.getArray(path)
        {
            response ->

            // Parse the result
            Log.i(path, "/post request OK! Response9999: $response")

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

            adapter = MyRecyclerAdapter(this,list!!)
            recyclerView!!.adapter = adapter

        }
    }

    fun SplitString(str:String):String
    {
        var array = str.split("/")
        var string = StringBuilder(array[0]+"/" + array[1]+"/" + array[2]+"/" + array[3]+"/")
        return string.toString()
    }
}
