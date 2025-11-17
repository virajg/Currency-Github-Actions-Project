package org.example.cmp.presentation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import cmp.composeapp.generated.resources.Res
import cmp.composeapp.generated.resources.switch_ic
import org.example.cmp.domain.utils.ApiResult
import org.example.cmp.domain.model.CurrencyCode
import org.example.cmp.domain.model.CurrencyData
import org.example.cmp.domain.model.CurrencyType
import org.jetbrains.compose.resources.painterResource

@Composable
fun CurrencyInput(
    source: ApiResult<CurrencyData>,
    target: ApiResult<CurrencyData>,
    onSwitchClick: () -> Unit,
    onRatesRefresh: () -> Unit,
    onCurrencyTypeSelect: (CurrencyType) -> Unit
){

    var animationStated by remember { mutableStateOf(false) }
    val animatedRotation by animateFloatAsState(
        targetValue =  if(animationStated) 180f else 0f,
        animationSpec = tween(durationMillis = 500)
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ){
        CurrencyView(
            placeholder = "from",
            currency = source,
            onclick = {
                if(source.isSuccess()){
                    onCurrencyTypeSelect(
                        CurrencyType.Source(
                            currencyCode = CurrencyCode.valueOf(source.getSuccessData().code.orEmpty())
                        )
                    )
                }
            }
        )
        Spacer(modifier = Modifier.height(14.dp))
        IconButton(
            modifier = Modifier
                .padding(top = 24.dp)
                .graphicsLayer{
                     rotationY  = animatedRotation
                },
            onClick = {
                animationStated = !animationStated
                onSwitchClick()
            }
        ){
            Icon(
                painter = painterResource(Res.drawable.switch_ic),
                contentDescription = "Switch Icon",
                tint = Color.White
            )
        }
        Spacer(modifier = Modifier.height(14.dp))
        CurrencyView(
            placeholder = "to",
            currency = target,
            onclick = {
                if(target.isSuccess()){
                    onCurrencyTypeSelect(
                        CurrencyType.Target(
                            currencyCode = CurrencyCode.valueOf(target.getSuccessData().code.orEmpty())
                        )
                    )
                }
            }
        )
    }
}