package com.example.mysubmissiononecompose

import android.content.Context

object FoodRepository {
    fun getFoods(context: Context): List<Food> {
        val names = context.resources.getStringArray(R.array.food_name)
        val locations = context.resources.getStringArray(R.array.food_location)
        val descriptions = context.resources.getStringArray(R.array.food_description)
        val imageUrls = context.resources.getStringArray(R.array.food_image)

        val count = minOf(names.size, locations.size, descriptions.size, imageUrls.size)
        return List(count) { index ->
            Food(
                name = names[index],
                location = locations[index],
                description = descriptions[index],
                imageUrl = imageUrls[index],
            )
        }
    }
}
