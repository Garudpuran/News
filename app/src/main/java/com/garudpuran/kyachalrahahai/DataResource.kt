package com.garudpuran.kyachalrahahai


import com.garudpuran.kyachalrahahai.Models.CategoryRvModel


class DataResource {
    fun loadCategories(): List<CategoryRvModel> {
        return listOf(
            CategoryRvModel("everything"),
            CategoryRvModel("business"),
            CategoryRvModel("entertainment"),
            CategoryRvModel("health"),
            CategoryRvModel("science"),
            CategoryRvModel("sports"),
            CategoryRvModel("technology")
        )
    }
}