package com.gtech.aectnp.ui.auth

import java.io.Serializable

class UserModel():Serializable {
    var name: String? = null;
    var usertoken: String? = null;
    var timestamp: MutableMap<String,String>? = null;
    var dob: String? = null;
    var email: String? = null;
    var currentsem: String? = null
    var branch: String? = null
    var number:String?=null
}

class GetUserModel():Serializable {
    var name: String? = null;
    var usertoken: String? = null;
    var timestamp: Long? = null;
    var dob: String? = null;
    var email: String? = null;
    var currentsem: String? = null
    var branch: String? = null
    var number:String?=null
}
