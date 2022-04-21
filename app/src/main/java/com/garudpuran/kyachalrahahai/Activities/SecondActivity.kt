package com.garudpuran.kyachalrahahai.Activities

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.garudpuran.kyachalrahahai.Adapters.CategoryRvAdapter
import com.garudpuran.kyachalrahahai.Adapters.NewsRvAdapter
import com.garudpuran.kyachalrahahai.DataResource
import com.garudpuran.kyachalrahahai.Interfaces.RetrofitAPI
import com.garudpuran.kyachalrahahai.Models.Articles
import com.garudpuran.kyachalrahahai.Models.NewsModel
import com.garudpuran.kyachalrahahai.databinding.ActivitySecondBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SecondActivity : AppCompatActivity(), CategoryRvAdapter.CategoryClickInterface {
    private lateinit var _binding: ActivitySecondBinding
    private val binding get() = _binding
    private lateinit var recyclerViewNews: RecyclerView
    private lateinit var recyclerViewCategory: RecyclerView
    private lateinit var adapter: NewsRvAdapter
    private var newsList: MutableList<Articles> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySecondBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        recyclerViewNews = binding.newsFeedRcView
        recyclerViewCategory = binding.categoryRcView
        adapter =  NewsRvAdapter(this@SecondActivity, newsList)
        recyclerViewNews.adapter = adapter
        val categoryList = DataResource().loadCategories()
        getNews("everything")
        binding.progressB.visibility = View.VISIBLE




        recyclerViewCategory.adapter = CategoryRvAdapter(this, categoryList, object :
            CategoryRvAdapter.CategoryClickInterface {
            override fun onCategoryClick(position: Int) {
                val  selectedCategory = categoryList[position].category.toString().trim()
                    getNews(selectedCategory)
            }
        })
        binding.searchBar.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                if(!binding.searchBar.text.isNullOrEmpty()){
                    val searchQ = binding.searchBar.text.toString().trim()
                    getNews(searchQ)
                    binding.searchBar.text.clear()
                    binding.searchBar.hideSoftInput()
                }
                else{
                    Toast.makeText(this@SecondActivity,"search query is null",Toast.LENGTH_SHORT)
                        .show()
                }

                return@OnKeyListener true
            }
            false
        })
    }

    private fun getNews(category: String) {
        binding.progressB.visibility = View.VISIBLE
        newsList.clear()
        adapter.notifyDataSetChanged()
        val categoryS = "https://newsapi.org/v2/everything?q=$category&apiKey=ee9f862c532042a7b47ba24d71aa42f6"
        val simpleURL =
            "https://newsapi" +
                    ".org/v2/top-headlines?country=in&apiKey=ee9f862c532042a7b47ba24d71aa42f6"
        val baseURL = "https://newsapi.org/"
        val rtFit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val rtAPI: RetrofitAPI = rtFit.create(RetrofitAPI::class.java)

        if (category == "everything") {
            rtAPI.getAllNews(simpleURL)
        } else {
            rtAPI.getAllNews(categoryS)
        }
        rtAPI.getAllNews(simpleURL).enqueue(object : Callback<NewsModel> {
            override fun onResponse(call: Call<NewsModel>, response: Response<NewsModel>) {
                val nModel: NewsModel? = response.body()
                binding.progressB.visibility = View.GONE
                val articles: List<Articles> = nModel?.articles!!
                for (i in articles.indices) {
                    newsList.add(
                        Articles(
                            articles[i].title,
                            articles[i].description,
                            articles[i].urlToImage,
                            articles[i].url,
                            articles[i].content,
                            articles[i].publishedAt,
                            articles[i].author
                        )
                    )
                }

                  adapter.notifyDataSetChanged()

            }

            override fun onFailure(call: Call<NewsModel>, t: Throwable) {
                Toast.makeText(this@SecondActivity, t.message, Toast.LENGTH_SHORT)
                    .show()

            }
        })
    }
    fun View.hideSoftInput() {
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    }

}