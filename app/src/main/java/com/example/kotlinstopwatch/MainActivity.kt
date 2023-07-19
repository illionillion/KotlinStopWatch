package com.example.kotlinstopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TERM_MILLISECOND: Long = 100 // 0.1秒
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // View取得
        val startButton = findViewById<Button>(R.id.start_button)
        val stopButton = findViewById<Button>(R.id.stop_button)
        val clearButton = findViewById<Button>(R.id.clear_button)
        val countText = findViewById<TextView>(R.id.count_label)

        // 経過時間
        var time = 0L
        var isStart = false
        val dataFormat = SimpleDateFormat("mm:ss.S", Locale.getDefault())

        // HandlerとRunnableを使ってタイマーを実装
        val handler = Handler(Looper.getMainLooper())
        val timer = object : Runnable {
            override fun run() {
                // 0.1秒ずつ追加
                time += TERM_MILLISECOND
                // countTextに時間を描画
                countText.text = dataFormat.format(time)
                // 0.1秒後に再度呼ばれるようにする
                handler.postDelayed(this, TERM_MILLISECOND)
            }
        }

        // STARTを押した時にtimerをpost
        startButton.setOnClickListener {
            if (isStart) return@setOnClickListener
            isStart = true
            handler.post(timer)
        }
        // STOPでタイマーを削除
        stopButton.setOnClickListener {
            isStart = false
            handler.removeCallbacks(timer)
        }
        // CLEARで経過時間をリセット
        clearButton.setOnClickListener {
            time = 0L
            // countTextに時間を描画
            countText.text = dataFormat.format(time)
        }
    }
}
