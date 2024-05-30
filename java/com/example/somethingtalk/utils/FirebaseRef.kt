package com.example.somethingtalk.utils

import com.google.firebase.Firebase
import com.google.firebase.auth.UserInfo
import com.google.firebase.database.database

//유저 정보를 불러오는 액티비티
class FirebaseRef {

    companion object {
        // 파이어 베이스 - 실시간 데이터베이스 - 안드로이드 - 시작하기 => 데이터베이스에 쓰기
        val database = Firebase.database
        // 파이어 베이스 - 실시간 데이터 베이스 - 데이터 - userInfo에 관한 정보를 쓰는 명령어
        val userInfoRef = database.getReference("userInfo")
        val userlikeRef = database.getReference("userLike")

    }
}