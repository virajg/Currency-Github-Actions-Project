package org.example.cmp.domain.utils

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

sealed class ApiResult<out T> {
    data class Success<out T>(val data: T): ApiResult<T>()
    data class Error(val errorMessage: String?, val errorCode: Int? = null, val throwable: Throwable? = null): ApiResult<Nothing>()
    object Loading: ApiResult<Nothing>()

    fun isSuccess() = this is Success
    fun isLoading() = this is Loading
    fun isError() = this is Error
    fun getSuccessData() = (this as Success).data
    fun getErrorMsg() = (this as Error).errorMessage.orEmpty()
}

@Composable
fun <T> ApiResult<T>.DisplayResult(
    onSuccess: @Composable (T) -> Unit,
    onError: (@Composable (String) -> Unit)? = null,
    onLoading: (@Composable () -> Unit)? = null,
    transitionSpec : ContentTransform = scaleIn(tween(durationMillis = 400))
    + fadeIn(tween(durationMillis = 800))
    togetherWith scaleOut(
        tween(durationMillis = 400))
            + fadeOut(
        tween(durationMillis = 800)
            )
) {
    AnimatedContent(
        targetState = this,
        transitionSpec = {transitionSpec},
        label = "content animation"
    ){ state ->
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            when(state){
                is ApiResult.Loading ->{
                    onLoading?.invoke()
                }
                is ApiResult.Success ->{
                    onSuccess(state.getSuccessData())
                }
                is ApiResult.Error ->{
                    onError?.invoke(state.getErrorMsg())
                }
            }
        }
    }
}

