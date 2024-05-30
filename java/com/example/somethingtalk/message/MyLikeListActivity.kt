package com.example.somethingtalk.message

import android.os.Bundle
import android.util.Log
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.somethingtalk.R
import com.example.somethingtalk.auth.UserDataModel
import com.example.somethingtalk.utils.FirebaseAuthUtils
import com.example.somethingtalk.utils.FirebaseRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// 내가 좋아한 사람들이 나를 좋아요 한 리스트
// 내가 예를들어 민지, 현아, 다솜이를 좋아했을 때, 현아와 다솜이만 나를 좋아요 한다면, 둘만 리스트 뜨도록 하는 클래스
class MyLikeListActivity : AppCompatActivity() {

    private val TAG = "myLikeListActivity"
    private val uid = FirebaseAuthUtils.getUid()

    private val likeUserListUid = mutableListOf<String>()
    private val likeUserList = mutableListOf<UserDataModel>()

    lateinit var listViewAdapter: ListViewAdapter


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_like_list)


        val userListView = findViewById<ListView>(R.id.userListView)

        listViewAdapter = ListViewAdapter(this, likeUserList)
        userListView.adapter = listViewAdapter

        //전체 유저 데이터 받아오기
//        getUserDataList()

        // 내가 좋아요 한 사람들을 불러오기
        getMyLikeList()
        // 나를 좋아요 한 사람들의 리스트를 받아와야 한다.

        userListView.setOnItemClickListener { parent, view, position, id ->

//            Log.d(TAG, likeUserList[position].uid.toString())
            checkMatching(likeUserList[position].uid.toString())

        }


    }

    private fun checkMatching(otherUid : String){

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                Log.d(TAG, otherUid)
                Log.e(TAG, dataSnapshot.toString())

                if(dataSnapshot.children.count() == 0) {

                    Toast.makeText( this@MyLikeListActivity, "매칭이 되지 않았습니다.", Toast.LENGTH_LONG).show()

                } else {

                    for (dataModel in dataSnapshot.children) {

                        val likeUserKey = dataModel.key.toString()
                        if (likeUserKey.equals(uid)) {
                            Toast.makeText(this@MyLikeListActivity, "매칭이 되었습니다.", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(this@MyLikeListActivity,"매칭이 되지 않았습니다.",Toast.LENGTH_LONG).show()
                        }

                    }

                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        FirebaseRef.userlikeRef.child(otherUid).addValueEventListener(postListener)

    }

    // 내가 좋아요한 사람이 누구를 좋아요 했는지 알 수 없음
    private fun getMyLikeList(){

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (dataModel in dataSnapshot.children) {
//                    Log.d(TAG, dataModel.key.toString())
                    //내가 좋아요 한 사람들의 uid가 likeUserList에 들어있음
                    likeUserListUid.add(dataModel.key.toString())

                }
                //위에 로직이 다 실행되면 아래 겟 유저 데이터 리스트를 실행시킴
                getUserDataList()

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        FirebaseRef.userlikeRef.child(uid).addValueEventListener(postListener)

    }

    private fun getUserDataList(){

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (dataModel in dataSnapshot.children) {
                    //데이터를 가지고 오는데 유저 데이터 모델의 형태로 받겠다.
                    val user = dataModel.getValue(UserDataModel::class.java)
                    //만약에 라이크 유저 리스트 안에 포함된다면... 유저의 uid가...
                    if(likeUserListUid.contains(user?.uid)){
                        //만약 위와 같다면 전체 유저 중에 내가 좋아요한 사람들의 정보만 add 함
                        likeUserList.add(user!!)
                    }



                }
                listViewAdapter.notifyDataSetChanged()
                Log.d(TAG, likeUserList.toString())

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        FirebaseRef.userInfoRef.addValueEventListener(postListener)


    }

}