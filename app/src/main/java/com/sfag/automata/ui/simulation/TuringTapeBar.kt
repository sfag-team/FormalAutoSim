package com.sfag.automata.ui.simulation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sfag.automata.core.machine.TuringTape

@Composable
fun TuringTapeBar(
    tape: TuringTape,
    cellSize: Int = 50
) {
    val view = tape.view()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(cellSize.dp + 20.dp)
            .background(MaterialTheme.colorScheme.surface),
        contentAlignment = Alignment.Center
    ) {
        LazyRow(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(view) { (index, symbol) ->
                val isHead = index == tape.head

                Box(
                    modifier = Modifier
                        .size(cellSize.dp)
                        .border(
                            2.dp,
                            if (isHead) Color.Red else MaterialTheme.colorScheme.primary,
                            MaterialTheme.shapes.medium
                        )
                        .background(
                            if (isHead) Color(0xFFFFCDD2)
                            else Color(0xFFE3F2FD),
                            MaterialTheme.shapes.medium
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = symbol.toString(),
                        fontSize = 26.sp,
                        color = Color.Black
                    )
                }

                Spacer(Modifier.width(6.dp))
            }
        }
    }
}
