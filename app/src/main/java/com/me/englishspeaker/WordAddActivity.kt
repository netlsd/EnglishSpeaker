package com.me.englishspeaker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.me.englishspeaker.databinding.ActivityWordAddBinding


class WordAddActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWordAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWordAddBinding.inflate(layoutInflater)
        binding.editWords.setText(readWordsFromFile())

        setContentView(binding.root)
    }

    override fun onPause() {
        super.onPause()
        saveWordsToFile(binding.editWords.text.toString())
    }

}