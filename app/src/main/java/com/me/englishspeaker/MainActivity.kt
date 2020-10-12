package com.me.englishspeaker

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.me.englishspeaker.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var wordList: MutableList<String>
    private lateinit var itr: MutableListIterator<String>
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        binding.buttonSpeak.setOnClickListener {
            speakWord(binding.textWord.text.toString())
        }

        binding.buttonDelete.setOnClickListener {
            itr = wordList.listIterator()
            while (itr.hasNext()) {
                if (itr.next() == binding.textWord.text) {
                    itr.remove()
                    if (itr.hasNext()) {
                        binding.textWord.text = itr.next()
                        break
                    } else if (itr.hasPrevious()) {
                        binding.textWord.text = itr.previous()
                        break
                    }
                }
            }
        }

        binding.buttonNext.setOnClickListener {
            if (itr.hasNext()) {
                while (itr.hasNext()) {
                    val word = itr.next()
                    if (word != binding.textWord.text) {
                        binding.textWord.text = word
                        break
                    }
                }
            }
        }

        binding.buttonPrev.setOnClickListener {
            while (itr.hasPrevious()) {
                val word = itr.previous()
                if (word != binding.textWord.text) {
                    binding.textWord.text = word
                    break
                }
            }
        }

        binding.buttonAdd.setOnClickListener {
            intent = Intent(this, WordAddActivity::class.java)
            startActivity(intent)
        }

        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        val words = readWordsFromFile()

        // split empty string always return 1 size array, so make empty list
        wordList = if (words.isBlank()) {
            Collections.emptyList()
        } else {
            val wordArray = words.split(" ")
            wordArray.toMutableList()
        }

        itr = wordList.listIterator()

        if (itr.hasNext()) {
            binding.textWord.text = itr.next()
        }
    }

    override fun onPause() {
        super.onPause()
        updateWordsFile()
    }

    private fun updateWordsFile() {
        val itr = wordList.listIterator()
        val wordsBuilder = StringBuilder()
        while (itr.hasNext()) {
            wordsBuilder.append(itr.next())
            wordsBuilder.append(" ")
        }

        var words = wordsBuilder.toString()
        if (!words.isBlank()) {
            words = words.substring(0, words.length - 1)
        }

        words.saveWordsToFile(this)
    }

    private fun speakWord(word: String) {
        val playUrl = "https://dict.youdao.com/dictvoice?audio=$word"
        val player = MediaPlayer()

        player.setOnPreparedListener {
            it.start()
        }
        player.setDataSource(playUrl)
        player.prepareAsync()

//        val renderersFactory = DefaultRenderersFactory(applicationContext)
//        val bandwidthMeter = DefaultBandwidthMeter()
//        val trackSelectionFactory = AdaptiveTrackSelection.Factory(bandwidthMeter)
//        val trackSelector = DefaultTrackSelector(trackSelectionFactory)
//
//        val player = ExoPlayerFactory.newSimpleInstance(this, renderersFactory, trackSelector)
////        player.addListener(this)
//
//        val dataSourceFactory = DefaultDataSourceFactory(applicationContext, "ExoplayerDemo")
//        val extractorsFactory = DefaultExtractorsFactory()
//        val mainHandler = Handler()
//        val mediaSource = ExtractorMediaSource(
//            Uri.parse(playUrl),
//            dataSourceFactory,
//            extractorsFactory,
//            mainHandler,
//            null
//        )
//
//        player.prepare(mediaSource)
//        player.playWhenReady = true
    }
}