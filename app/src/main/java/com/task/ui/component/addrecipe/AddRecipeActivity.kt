package com.task.ui.component.addrecipe

import android.os.Bundle
import android.view.Menu
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.task.R
import com.task.data.Resource
import com.task.data.dto.login.LoginResponse
import com.task.databinding.AddrecipeLayoutBinding
import com.task.ui.base.BaseActivity
import com.task.utils.toGone
import com.task.utils.toVisible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddRecipeActivity : BaseActivity() {

    private lateinit var binding: AddrecipeLayoutBinding

    private val addRecipeViewModel: AddRecipeViewModel by viewModels()


    override fun initViewBinding() {
        binding = AddrecipeLayoutBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = getString(R.string.add_recipe)
//        recipesListViewModel.getRecipes()
        binding.submitButton.setOnClickListener { createRecipe() }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
    }

    override fun observeViewModel() {
        TODO("Not yet implemented")
    }

    private fun createRecipe() {
        addRecipeViewModel.createRecipe(
            binding.nameEditText.text.toString(),
            binding.headlineEditText.text.toString(),
            binding.descriptionEditText.text.toString()
        )
    }

//    private fun handleRecipeResult(status: Resource<LoginResponse>) {
//        when (status) {
//            is Resource.Loading -> binding.loaderView.toVisible()
//            is Resource.Success -> status.data?.let {
//                binding.loaderView.toGone()
//                navigateToMainScreen()
//            }
//            is Resource.DataError -> {
//                binding.loaderView.toGone()
//                status.errorCode?.let { loginViewModel.showToastMessage(it) }
//            }
//        }
//    }
}
