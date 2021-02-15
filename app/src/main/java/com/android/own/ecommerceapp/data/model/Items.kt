package com.android.own.ecommerceapp.data.model

class Items {
    var name: String? = null
    var price: String? = null
    var details: String? = null

    constructor() {}
    constructor(
        name: String?,
        price: String?,
        details: String?
    ) {
        this.name = name
        this.price = price
        this.details = details
    }

}