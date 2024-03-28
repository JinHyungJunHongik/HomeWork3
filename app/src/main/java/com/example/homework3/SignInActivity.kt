package com.example.homework3

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog

val MemberData = mutableListOf<Member>()
class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        val btnLogIn = findViewById<Button>(R.id.btn_Login)
        val btnSignUp = findViewById<Button>(R.id.btn_register)
        val editId = findViewById<EditText>(R.id.input_ID)
        val editPwd = findViewById<EditText>(R.id.input_PWD)
        var isIdInput = 0
        var isPwdInput = 0
        var homeInputID = ""
        btnSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }

        //초기 버튼들 설정
        btnLogIn.setBackgroundColor(Color.rgb(232,236,229))
        btnSignUp.setBackgroundColor(Color.rgb(0,0,255))
        btnLogIn.isEnabled = false

        //로그인버튼 활성화 및 비활성화에 활용할 EditText의 addTextChangedLister
        editId.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(editId.text.length >= 5){
                    isIdInput = 1
                    homeInputID = editId.getText().toString()
                }
                else {
                    isIdInput = 0
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
        editPwd.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(editPwd.text.length >= 8){
                    isPwdInput = 1
                }
                else{
                    isPwdInput = 0
                }

                if(isIdInput == 1 && isPwdInput == 1){
                    btnLogIn.setBackgroundColor(Color.rgb(0,0,255))
                    btnLogIn.isEnabled = true
                }
                else {
                    btnLogIn.setBackgroundColor(Color.rgb(232,236,229))
                    btnLogIn.isEnabled = false
                }
            }
            override fun afterTextChanged(s: Editable?) {
            }
        })

        //로그인 버튼에 활용할 함수, 멤버 데이터에서 데이터를 찾고 없거나 비밀번호가 일치하지 않으면 로그인X
        fun logIn(id: String, pwd: String) {
            var data: Member? = null
            for(i in 0..MemberData.size-1){
               if(MemberData[i]._ID == id){
                   data = MemberData[i]
               }
            }
            if(data == null){
                Toast.makeText(this, "아이디가 존재하지 않습니다.", Toast.LENGTH_SHORT).show()
                editId.text.clear()
                editPwd.text.clear()
            }

            if(data != null) {
                if (data.getPwd() == pwd) {
                    Toast.makeText(this, "로그인 성공.", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, HomeActivity::class.java)
                    intent.putExtra("LoginID", "${editId.getText().toString()}")
                    startActivity(intent)
//                    finish()
                } else {
                    Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                    editPwd.text.clear()
                }
            }
        }

        btnLogIn.setOnClickListener {
            logIn(editId.text.toString(), editPwd.text.toString())
        }

        //기입 후 관련 설정
        var registerID: String?
        var registerPwd: String? = null
        registerID = intent.getStringExtra("registerData")
        if(registerID != null){
            for(i in 0..MemberData.size-1){
                if(registerID == MemberData[i]._ID){
                    registerPwd = MemberData[i].getPwd()
                }
            }
            editId.setText(registerID)
            editPwd.setText(registerPwd)
        }

//        var resultLauncher : ActivityResultLauncher<Intent>
//        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
//            result -> if(result.resultCode == Activity.RESULT_OK) {
//                var id = result.data?.getStringExtra("id")
//                var pwd = result.data?.getStringExtra("pwd")
//                editId.setText(id)
//                editPwd.setText("pwd")
//            }
//        }
//        }
    }
}