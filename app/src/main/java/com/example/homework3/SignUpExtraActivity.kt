package com.example.homework3

import android.app.DatePickerDialog
import android.app.DatePickerDialog.*
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class SignUpExtraActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_extra)

        //findViewById
        val inputBirth = findViewById<TextView>(R.id.register_InputBirth)
        val inputMBTI = findViewById<EditText>(R.id.register_InputMBTI)
        val btnRegister = findViewById<Button>(R.id.btn_registerDone)


        //초기 설정들
        var data: String? = null
        var member: Member? = null
        var IDtext: String = ""
        var Pwdtext: String = ""
        data = intent.getStringExtra("data")
        Log.d("data", "$data")
        Log.d("멤버리스트 확인", "${MemberData[0]._ID}")
        for(i in 0..MemberData.size-1){
            if(MemberData[i]._ID == data){
                member = MemberData[i]
                Log.d("전달 받은 데이터", "${member._ID}, ${member._name}")
                IDtext = member._ID
//                Pwdtext = member.getPwd()
                break
            }
        }
        btnRegister.isEnabled = false
        btnRegister.setBackgroundColor(Color.rgb(232,236,229))


        //데이터 입력 관련 버튼 함수들
        inputBirth.setOnClickListener {
            DatePickerDialog(this@SignUpExtraActivity, object: DatePickerDialog.OnDateSetListener{
                override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                    inputBirth.text = "${year}.${month+1}.${dayOfMonth}"
                }
            }, 2000,2,18).show()
        }

        inputMBTI.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(inputBirth.text.length > 4 && inputMBTI.text.length == 4){
                    btnRegister.setBackgroundColor(Color.rgb(0,0,255))
                    btnRegister.isEnabled = true
                }
                else {
                    btnRegister.isEnabled = false
                    btnRegister.setBackgroundColor(Color.rgb(232,236,229))
                }
            }
            override fun afterTextChanged(s: Editable?) {
            }
        })

        btnRegister.setOnClickListener {
            Toast.makeText(this, "회원가입이 완료되었습니다!", Toast.LENGTH_SHORT).show()
            member?.inputBirth(inputBirth.getText().toString())
            member?.inputMBTI(inputMBTI.getText().toString())
            Log.d("메인으로 보낼 데이터", "$IDtext")
            val intent = Intent(this, SignInActivity::class.java )
            intent.putExtra("registerData", IDtext)
            startActivity(intent)
            finish()
        }

//        btnRegister.setOnClickListener {
//            Toast.makeText(this, "회원가입이 완료되었습니다!", Toast.LENGTH_SHORT).show()
//            member?.inputBirth(inputBirth.getText().toString())
//            member?.inputMBTI(inputMBTI.getText().toString())
//            val intent = Intent(this, SignInActivity::class.java )
//            intent.putExtra("id", IDtext)
//            intent.putExtra("pwd", Pwdtext)
//            setResult(RESULT_OK, intent)
//            finish()
//        }
    }
}