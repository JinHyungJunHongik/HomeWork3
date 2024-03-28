package com.example.homework3

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import org.w3c.dom.Text
import kotlin.random.Random

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val homeID = findViewById<TextView>(R.id.home_id)
        val homeName = findViewById<TextView>(R.id.home_name)
        val homeMBTI = findViewById<TextView>(R.id.home_MBTI)
        val homeAge = findViewById<TextView>(R.id.home_age)
        val btnMain = findViewById<Button>(R.id.btn_return)
        val homeImg = findViewById<ImageView>(R.id.img_home)
        btnMain.setBackgroundColor(Color.rgb(0,0,255))
        var data = intent.getStringExtra("LoginID")
        Log.d("메인에서 전달받은 데이터", "$data")
        for(i in 0..MemberData.size - 1){
            if(MemberData[i]._ID == data){
                homeID.text = "아이디 : ${MemberData[i]._ID}"
                homeName.text = "이름 : ${MemberData[i]._name}"
                homeMBTI.text = "MBTI: ${MemberData[i].MBTI}"
                homeAge.text = "생년월일: ${MemberData[i].Birth}"
            }
        }
        //랜덤 이미지 뽑기
        var random = Random.nextInt(5)
        when (random){
            0 -> homeImg.setImageResource(R.drawable.man_icon1)
            1 -> homeImg.setImageResource(R.drawable.man_icon2)
            2 -> homeImg.setImageResource(R.drawable.man_icon3)
            3 -> homeImg.setImageResource(R.drawable.man_icon4)
            else -> homeImg.setImageResource(R.drawable.man_icon5)
        }

        btnMain.setOnClickListener {
            finish()
        }
    }
}