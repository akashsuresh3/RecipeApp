package com.task.ui.component.addrecipe

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.task.data.DataRepositorySource
import com.task.data.Resource
import com.task.data.dto.login.CreateRecipeRequest
import com.task.data.dto.login.LoginRequest
import com.task.data.dto.login.LoginResponse
import com.task.data.error.CHECK_YOUR_FIELDS
import com.task.data.error.DESCRIPTION_ERROR
import com.task.data.error.HEADLINE_ERROR
import com.task.data.error.NAME_ERROR
import com.task.data.error.PASS_WORD_ERROR
import com.task.data.error.USER_NAME_ERROR
import com.task.ui.base.BaseViewModel
import com.task.utils.RegexUtils.isValidEmail
import com.task.utils.SingleEvent
import com.task.utils.wrapEspressoIdlingResource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddRecipeViewModel @Inject
constructor(private val dataRepository: DataRepositorySource) : BaseViewModel() {

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val createRecipeLiveDataPrivate = MutableLiveData<Resource<Boolean>>()
    val createRecipeLiveData: LiveData<Resource<Boolean>> get() = createRecipeLiveDataPrivate

    /** Error handling as UI **/

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val showSnackBarPrivate = MutableLiveData<SingleEvent<Any>>()
    val showSnackBar: LiveData<SingleEvent<Any>> get() = showSnackBarPrivate

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val showToastPrivate = MutableLiveData<SingleEvent<Any>>()
    val showToast: LiveData<SingleEvent<Any>> get() = showToastPrivate


    fun createRecipe(name: String, headline: String, description: String) {
        val isNameValid = isValidNonEmptyString(name)
        val isHeadlineValid = isValidNonEmptyString(headline)
        val isDescriptionValid = isValidNonEmptyString(description)
        if (!isNameValid) {
            createRecipeLiveDataPrivate.value = Resource.DataError(NAME_ERROR)
        } else if (!isHeadlineValid) {
            createRecipeLiveDataPrivate.value = Resource.DataError(HEADLINE_ERROR)
        } else if (!isDescriptionValid) {
            createRecipeLiveDataPrivate.value = Resource.DataError(DESCRIPTION_ERROR)
        } else {
            viewModelScope.launch {
                createRecipeLiveDataPrivate.value = Resource.Loading()
                wrapEspressoIdlingResource {
                    dataRepository.createRecipe(createRecipeRequest = CreateRecipeRequest(name, headline, description)).collect {
                        createRecipeLiveDataPrivate.value = it
                    }
                }
            }
        }
    }

    fun isValidNonEmptyString(s : String) : Boolean {
        if(s.trim().length > 0) return true
        return false
    }

    fun showToastMessage(errorCode: Int) {
        val error = errorManager.getError(errorCode)
        showToastPrivate.value = SingleEvent(error.description)
    }

}