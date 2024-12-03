package com.task.ui.base.listeners

import com.task.data.dto.recipes.RecipesItem

/**
 * Created by AkashPS
 */

interface RecyclerItemListener {
    fun onItemSelected(recipe : RecipesItem)
}
