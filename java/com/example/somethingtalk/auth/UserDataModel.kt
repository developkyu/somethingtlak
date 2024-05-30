package com.example.somethingtalk.auth

data class UserDataModel (
    // 회원가입시 유저 데이터(uid, nickname 등)를 파이어 베이스 리얼타임 데이터 베이스로 넘기기 쉽게 만들어 둔 코틀린 파일
    val uid : String? = null,
    val nickname : String? = null,
    val age : String? = null,
    val gender : String? = null,
    val city : String? = null,
    val token : String? = null

)