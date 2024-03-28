package com.example.homework3

class Member(name: String, id: String, password: String) {
    var _name: String = ""
    var _ID: String = ""
    private var _Password: String = ""
    var Birth: String = ""
    var MBTI: String = ""

    init{
        _name = name
        _ID = id
        _Password = password
    }

    fun inputMBTI(str: String) {
        this.MBTI = str
    }

    fun inputBirth(str: String) {
        this.Birth = str
    }
    fun getPwd(): String{
        return this._Password
    }
}