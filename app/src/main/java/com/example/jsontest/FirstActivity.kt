package com.example.jsontest

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.lang.Exception


fun isJsonObject(jsonString:String):Boolean {
    try {
        val jsonObj:JSONObject= JSONObject(jsonString)
    } catch (e: JSONException) {
        return false
    }
    return true
}
fun isJsonArray(jsonString:String):Boolean {
    try {
        val jsonObj= JSONArray(jsonString)
    } catch (e: JSONException) {
        return false
    }
    return true
}


fun queryJSON(queryString:String,jsonString: String):String {
    var result= "failed"
    var oldString=jsonString
    var newString=jsonString

    val list = queryString.split(" ")
    for (str in list){
        oldString=newString
        var flag=0

        if (isJsonObject(oldString)){
            val tempJSONObject=JSONObject(oldString)
            try {
                newString=tempJSONObject.getJSONArray(str).toString()
            }catch (e: Exception){
                flag+=1
            }
            try {
                newString=tempJSONObject.getJSONObject(str).toString()
            }catch (e: Exception){
                flag+=1
            }
            try {
                newString=tempJSONObject.getString(str)
            }catch (e: Exception){
                flag+=1
            }
        }
        else if(isJsonArray(oldString)){
            var str=str
            val tempJSONArray=JSONArray(oldString)
            str=str.replace("[","")
            str=str.replace("]","")
            try {
                val index=str.toInt()
                newString=tempJSONArray.getJSONArray(index).toString()
            }catch (e: Exception){
                flag+=1
            }
            try {
                val index=str.toInt()
                newString=tempJSONArray.getJSONObject(index).toString()
            }catch (e: Exception){
                flag+=1
            }
            try {
                val index=str.toInt()
                newString=tempJSONArray.getString(index)
            }catch (e: Exception){
                flag+=1
            }
        }
        else{
            return "failed"
        }
        if (flag==3)
            return "failed"
    }

    result=newString
    return result
}




class FirstActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.first_layout)
        var str=" "
        var jsonStr=" "
        val editText1: EditText=findViewById(R.id.editText)
        val editText2: EditText=findViewById(R.id.editText2)
        val button1:Button=findViewById(R.id.button)
        val button2:Button=findViewById(R.id.button2)
        button1.setOnClickListener {
            val str=editText1.getText().toString()
            if(isJsonObject(str) or isJsonArray(str)) {
                Toast.makeText(this,"解析成功",Toast.LENGTH_SHORT).show()
                if (isJsonObject(str)){
                    Toast.makeText(this,"对象是一个JsonObject",Toast.LENGTH_SHORT).show()
                    val dataJson=JSONObject(str)
                    jsonStr=dataJson.toString()
                }
                else{
                    Toast.makeText(this,"对象是一个JsonArray",Toast.LENGTH_SHORT).show()
                    val dataJson=JSONArray(str)
                    jsonStr=dataJson.toString()
                }
            }
            else
            {
                Toast.makeText(this,"不是合法的JSON",Toast.LENGTH_SHORT).show()
            }
        }

        //如何查询目标对象？实例
        //sites [0] name ==> "baidu"
        button2.setOnClickListener {
            val str=editText1.getText().toString()
            val queryString=editText2.getText().toString()
            val str2=queryJSON(queryString,str)
            Toast.makeText(this,str2,Toast.LENGTH_SHORT).show()
        }




    }
}