package com.example.mysql.models

import java.io.Serializable

class Group:Serializable {
    var id:Int? = null
    var name:String? = null

    constructor(id: Int?, name: String?) {
        this.id = id
        this.name = name
    }

    constructor(name: String?) {
        this.name = name
    }

}