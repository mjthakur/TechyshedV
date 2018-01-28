package com.techyshed.techyshedn


/**
 * Created by Mj 2 on 27-Jan-18.
 */

class Post {

    var id: Long?=null

    var date: String?=null

    var slug: String?=null

    var link: String?=null

    var title:String?=null

    var image: String?=null

    //var excerpt: String?=null

    constructor(id: Long, date: String, slug: String, link: String, title:String, image: String)
    {
        this.id = id
        this.date = date
        this.link = link
        this.slug = slug
        this.title = title
        this.image = image
        //this.excerpt = excerpt
    }

}


/*class Title(
        val rendered:String?=null
)

class Content(
        val rendered: String?=null
)

class Excerpt(
        val rendered: String?=null
)

class Result (
        val items: List<Post>?=null

)*/
