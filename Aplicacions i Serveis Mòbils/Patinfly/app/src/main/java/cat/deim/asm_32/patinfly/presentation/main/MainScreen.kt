package cat.deim.asm_32.patinfly.presentation.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import cat.deim.asm_32.patinfly.R
import cat.deim.asm_32.patinfly.ui.theme.Nunito
import cat.deim.asm_32.patinfly.ui.theme.PatinflyTheme

@Composable
fun MainScreen(
    perfilClick: () -> Unit,
    bicisClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.padding_medium)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.displayLarge.copy(
                fontFamily = Nunito,
                fontWeight = FontWeight.Bold
            ),
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))

        Image(
            painter = painterResource(id = R.drawable.bike_splash_screen),
            contentDescription = stringResource(R.string.logo_desc),
            modifier = Modifier
                .height(dimensionResource(R.dimen.logo_height))
                .clip(RoundedCornerShape(dimensionResource(R.dimen.logo_corner_radius)))
        )
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))

        Button(
            onClick = perfilClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.button_padding))
        ) {
            Text(stringResource(R.string.view_profile))
        }

        Button(
            onClick = bicisClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.button_padding))
        ) {
            Text(stringResource(R.string.view_bikes))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    PatinflyTheme {
        MainScreen(
            perfilClick = {},
            bicisClick = {}
        )
    }
}