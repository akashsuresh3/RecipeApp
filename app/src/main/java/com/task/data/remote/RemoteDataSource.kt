package com.task.data.remote

import com.task.data.Resource
import com.task.data.dto.recipes.Recipes

/**
 * Created by AkashPS
 */

internal interface RemoteDataSource {
    suspend fun requestRecipes(): Resource<Recipes>
}
