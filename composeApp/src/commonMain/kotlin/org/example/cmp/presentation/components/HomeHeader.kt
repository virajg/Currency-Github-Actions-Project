package org.example.cmp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cmp.composeapp.generated.resources.Res
import cmp.composeapp.generated.resources.exchange_illustration
import cmp.composeapp.generated.resources.refresh_ic
import org.example.cmp.domain.utils.ApiResult
import org.example.cmp.domain.model.CurrencyData
import org.example.cmp.domain.model.CurrencyType
import org.example.cmp.domain.model.RateStatus
import org.example.cmp.ui.theme.headerColor
import org.example.cmp.ui.theme.staleColor
import org.example.cmp.utils.displayCurrentDateTime
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun HomeHeader(
    status : RateStatus,
    source: ApiResult<CurrencyData>,
    target: ApiResult<CurrencyData>,
    amount: Double,
    onAmountChanged: (Double) -> Unit,
    onRatesRefresh : () -> Unit,
    onSwitchClick: () -> Unit,
    onCurrencyTypeSelect: (CurrencyType) -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = headerColor)
            .clip(RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp))
            .padding(24.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        RatesStatus(
            status = status,
            onRatesRefresh = onRatesRefresh
        )
        Spacer(modifier = Modifier.height(24.dp))
        CurrencyInput(
            source = source,
            target = target,
            onSwitchClick = onSwitchClick,
            onRatesRefresh =  onRatesRefresh,
            onCurrencyTypeSelect = onCurrencyTypeSelect
        )
        Spacer(modifier = Modifier.height(24.dp))
        AmountInput(
            amount = amount,
            onAmountChanged = onAmountChanged
        )
    }
}

@Composable
fun RatesStatus(status: RateStatus, onRatesRefresh: ()-> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            Image(
                modifier = Modifier.size(50.dp),
                painter = painterResource(Res.drawable.exchange_illustration),
                contentDescription = "Exchange rates Illustration"
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = displayCurrentDateTime(),
                    color = Color.White
                )
                Text(
                    text = status.title,
                    fontSize = MaterialTheme.typography.bodySmall.fontSize,
                    color = status.color
                )
            }
            if(status == RateStatus.Stale){
                IconButton(onClick = onRatesRefresh){
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(Res.drawable.refresh_ic),
                        contentDescription = "Refresh icon",
                        tint =  staleColor
                    )
                }
            }
        }
    }
}