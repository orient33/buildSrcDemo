package cn.abc.demo66

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.AJ

class MainActivity : AppCompatActivity() {
    var tv: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tv = findViewById(R.id.text)
    }

    override fun onResume() {
        super.onResume()
        tv?.append(AJ().printMap())
    }
}