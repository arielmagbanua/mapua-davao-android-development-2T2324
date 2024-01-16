package com.quadrants.magbanua.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import com.quadrants.magbanua.R

@Composable
fun Quadrants(modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier.fillMaxSize()
    ) {
        Row(modifier = Modifier.weight(0.5f)) {
            Quadrant(
                title = stringResource(R.string.text_composable),
                paragraph = stringResource(R.string.text_composable_paragraph),
                background = colorResource(R.color.color_q1),
                modifier = Modifier.weight(0.5f)
            )
            Quadrant(
                title = stringResource(R.string.image_composable),
                paragraph = stringResource(R.string.image_composable_paragraph),
                background = colorResource(R.color.color_q2),
                modifier = Modifier.weight(0.5f)
            )
        }
        Row(modifier = Modifier.weight(0.5f)) {
            Quadrant(
                title = stringResource(R.string.row_composable),
                paragraph = stringResource(R.string.row_composable_paragraph),
                background = colorResource(R.color.color_q3),
                modifier = Modifier.weight(0.5f)
            )
            Quadrant(
                title = stringResource(R.string.column_composable),
                paragraph = stringResource(R.string.column_composable_paragraph),
                background = colorResource(R.color.color_q4),
                modifier = Modifier.weight(0.5f)
            )
        }
    }
}
