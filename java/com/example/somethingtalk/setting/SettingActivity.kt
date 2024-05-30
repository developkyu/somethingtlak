package com.example.somethingtalk.setting

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.somethingtalk.R
import com.example.somethingtalk.auth.IntroActivity
import com.example.somethingtalk.message.MyLikeListActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class SettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        val mybtn = findViewById<Button>(R.id.myPageBtn)
        mybtn.setOnClickListener{

            val intent = Intent(this, MyPageActivity::class.java)
            startActivity(intent)

        }
        //myLikeList버튼을 누르면
        val myLikeBtn = findViewById<Button>(R.id.myLikeList)
        myLikeBtn.setOnClickListener {
            //MyLikeListActivity로 화면을 이동을 시킨다.
            val intent = Intent(this, MyLikeListActivity::class.java)
            startActivity(intent)

        }
        //로그아웃 버튼을 찾고, 로그아웃 버튼을 클릭하면 아래와 같이 이벤트를 실행시키는 명령어
        val logoutBtn = findViewById<Button>(R.id.logoutBtn)
        logoutBtn.setOnClickListener {

            // 파이어베이스 인증을 로그아웃해라
            val auth = Firebase.auth
            auth.signOut()

            //로그아웃이 되면 인트로액티비티로 화면이 전환되도록 해라
            val intent = Intent(this, IntroActivity::class.java)
            startActivity(intent)

        }

    }
}