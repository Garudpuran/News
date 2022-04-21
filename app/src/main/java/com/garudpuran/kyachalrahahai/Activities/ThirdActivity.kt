package com.garudpuran.kyachalrahahai.Activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.garudpuran.kyachalrahahai.R
import com.garudpuran.kyachalrahahai.databinding.ActivitySecondBinding
import com.garudpuran.kyachalrahahai.databinding.ActivityThirdBinding
import com.squareup.picasso.Picasso

class ThirdActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityThirdBinding
    private val binding get() = _binding
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding =ActivityThirdBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val content = intent.getStringExtra("content")
        val description = intent.getStringExtra("description")
        val url = intent.getStringExtra("urlNews")
        val urlToImage = intent.getStringExtra("urlToImage")
        val publishTime = intent.getStringExtra("publishedAt")
        val title = intent.getStringExtra("title")

        Picasso.get().load(urlToImage).into(binding.fullImageNews)
        binding.titleTv.text = title
        binding.contentTv.text = content?.dropLast(13)+"CLICK READ MORE"
        binding.descriptionTv.text = description

        binding.timeTv.text = publishTime?.dropLast(10)


        binding.readBtn.setOnClickListener{
            url.asUri()?.openInBrowser(this)
        }
    }
    private fun String?.asUri(): Uri? {
        return try {
            Uri.parse(this)
        } catch (e: Exception) {
            null
        }
    }
    private fun Uri?.openInBrowser(context: Context) {
        this ?: return // Do nothing if uri is null
        val browserIntent = Intent(Intent.ACTION_VIEW, this)
        ContextCompat.startActivity(context, browserIntent, null)
    }
}