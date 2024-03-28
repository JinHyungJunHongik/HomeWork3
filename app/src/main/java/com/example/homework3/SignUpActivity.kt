package com.example.homework3

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        //findViewById 묶기
        val inputName = findViewById<EditText>(R.id.register_InputName)
        val inputID = findViewById<EditText>(R.id.register_InputID)
        val inputPwd = findViewById<EditText>(R.id.register_InputPWD)
        val pwdCheck = findViewById<EditText>(R.id.register_InputPWDCheck)
        val isIdExist = findViewById<Button>(R.id.btn_isIdExist)
        val btnNext = findViewById<Button>(R.id.btn_registerNext)
        val errorText = findViewById<TextView>(R.id.tx_errorCheck)
        var isEnterName = 0
        var isEnterID = 0
        var availableID = 0
        var isEnterPWD = 0
        var isPwdOK = 0
        var text: String = ""
        //초기 설정
        btnNext.setBackgroundColor(Color.rgb(232,236,229))
        btnNext.isEnabled = false
        isIdExist.setBackgroundColor(Color.rgb(0,0,255))
        errorText.visibility = View.INVISIBLE
        inputID.text.clear()
        inputName.text.clear()
        inputPwd.text.clear()
        inputPwd.text.clear()
        //회원가입 진행 버튼 활성화 설정에 활용하는 EditText 리스너 설정
        inputName.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(inputName.text.length >= 2){
                    isEnterName = 1
                }
                else{
                    isEnterName = 0
                }
            }
            override fun afterTextChanged(s: Editable?) {
            }
        })
        inputID.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(inputID.text.toString().length >= 5 && inputID.text.toString().length <= 10){
                    isEnterID = 1
                }
                else if(inputID.text.length > 10){
                    Toast.makeText(this@SignUpActivity, "아이디는 10자를 넘을 수 없습니다.", Toast.LENGTH_SHORT).show()
                    isEnterID = 0
                }
                else{
                    isEnterID = 0
                }
            }
            override fun afterTextChanged(s: Editable?) {
                text = inputID.getText().toString()
                Log.d("데이터", "$text")
            }
        })
        inputPwd.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(inputPwd.text.toString(). length >= 8 && inputPwd.text.toString().length <= 20){
                    isEnterPWD = 1
                }
                else if(inputPwd.text.length
                    > 20){
                    Toast.makeText(this@SignUpActivity, "비밀번호는 20자를 넘을 수 없습니다.", Toast.LENGTH_SHORT).show()
                    isEnterPWD = 0
                }
                else{
                    isEnterPWD = 0
                }
            }
            override fun afterTextChanged(s: Editable?) {
            }
        })
        pwdCheck.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                if((pwdCheck.text.length >= 1) && (inputPwd.text != pwdCheck.text)){
                    Log.d("일치확인", "${inputPwd.text},${pwdCheck.text}")
                    isPwdOK = 0
                    errorText.visibility = View.VISIBLE
                    errorText.text = "비밀번호가 일치하지 않습니다"
                    errorText.setTextColor(Color.rgb(255,0,0))
                }
                if(inputPwd.text.toString() == pwdCheck.text.toString()){
                    isPwdOK = 1
                    errorText.visibility = View.INVISIBLE
                }

            }
            override fun afterTextChanged(s: Editable?) {

            }
        })
        fun isEverythingOK(){
            Log.d("확인","${isEnterID}, $isEnterName,$isEnterPWD, $availableID, $isPwdOK")
            if(isEnterID == 1 && isEnterName == 1 && isEnterPWD == 1 && availableID == 1 &&
                isPwdOK == 1){
                btnNext.setBackgroundColor(Color.rgb(0,0,255))
                btnNext.isEnabled = true
            }
            else{
                btnNext.setBackgroundColor(Color.rgb(232,236,229))
                btnNext.isEnabled = false
            }
        }

        lifecycleScope.launch {
            whenStarted {
                while(true){
                    delay(500)
                    isEverythingOK()
                }
            }
        }

        //아이디 중복 체크 버튼 함수
        fun isExistID(str: String): Int {
            var check = 1
            if(inputID.text.length < 5){
                Toast.makeText(this@SignUpActivity, "올바른 아이디를 입력해주세요", Toast.LENGTH_SHORT).show()
            }
            else {
                for (i in 0..MemberData.size - 1) {
                    if (MemberData[i]._ID == str) {
                        check = 0
                        break
                    }
                }
            }
            return check
        }



        //버튼 클릭 관련 함수들
        isIdExist.setOnClickListener {
            availableID = isExistID(inputID.text.toString())
            if(availableID == 1){
                Toast.makeText(this@SignUpActivity, "사용할 수 있는 아이디입니다", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(this@SignUpActivity, "이미 존재하는 아이디입니다.", Toast.LENGTH_SHORT).show()
                inputID.text.clear()
            }
        }

        btnNext.setOnClickListener {
            var data = Member(inputName.getText().toString(), inputID.getText().toString(), inputPwd.getText().toString())
            MemberData.add(data)
            val intent = Intent(this, SignUpExtraActivity::class.java)
            intent.putExtra("data", text)
            startActivity(intent)
            finish()
        }
    }
}