package com.freaky.iou.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.freaky.iou.ui.data.models.Group
import com.freaky.iou.ui.theme.IOUTheme
import java.util.UUID

@Composable
fun GroupCard(group: Group, modifier: Modifier = Modifier) {
    Card(modifier = modifier.fillMaxWidth()) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .background(
                        Color.hsl(
                            hue = group.id.toHue(),
                            saturation = 0.3f,
                            lightness = 0.5f
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier.size(48.dp)
                )
            }

            Text(
                text = group.name,
                modifier = Modifier.padding(12.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

fun UUID.toHue(): Float {
    // Combine the upper and lower 64 bits and ensure a non-negative value
    val hash = (mostSignificantBits xor leastSignificantBits).hashCode()
    // Avoid Int.MIN_VALUE edge case where abs(Int.MIN_VALUE) stays negative
    val positiveHash = hash and 0x7FFFFFFF
    return (positiveHash % 360).toFloat()
}

@Preview(showBackground = true)
@Composable
fun GroupCardPreview() {
    IOUTheme(darkTheme = true) {
        GroupCard(Group(id = UUID.randomUUID(), "Hello"))
    }
}
