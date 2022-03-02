package com.example.mysql.models

class Students {
    var id:Int? = null
    var name:String? = null
    var number:String? = null

    var group:Group? = null

    constructor(id: Int?, name: String?, number: String?, group: Group?) {
        this.id = id
        this.name = name
        this.number = number
        this.group = group
    }

    constructor(name: String?, number: String?, group: Group?) {
        this.name = name
        this.number = number
        this.group = group
    }

}