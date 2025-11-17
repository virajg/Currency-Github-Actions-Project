package org.example.cmp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.example.cmp.domain.utils.ApiResult
import org.example.cmp.domain.utils.DisplayResult
import org.example.cmp.domain.model.CurrencyCode
import org.example.cmp.domain.model.CurrencyData
import org.jetbrains.compose.resources.painterResource

@Composable
fun RowScope.CurrencyView(
    placeholder:String,
    currency: ApiResult<CurrencyData>,
    onclick:()-> Unit
){
    Column(modifier = Modifier.weight(1f)) {
        Text(
            text = placeholder,
            modifier = Modifier.padding(start = 12.dp),
            fontSize = MaterialTheme.typography.bodySmall.fontSize,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(size = 8.dp))
                .background(Color.White.copy(alpha = 0.05f))
                .height(54.dp)
                .clickable{ onclick() },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            currency.DisplayResult(
                onSuccess = { data ->
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(
                            resource = CurrencyCode.valueOf(
                                currency.getSuccessData().code.orEmpty()
                            ).flag
                        ),
                        tint = Color.Unspecified,
                        contentDescription = "Country Flag"
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = CurrencyCode.valueOf(
                            currency.getSuccessData().code
                        ).name,
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        }
    }
}