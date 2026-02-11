package cat.deim.asm_32.patinfly.presentation.splash

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import cat.deim.asm_32.patinfly.R
import cat.deim.asm_32.patinfly.presentation.login.LoginActivity
import cat.deim.asm_32.patinfly.ui.theme.Nunito
import kotlinx.coroutines.delay

@Composable
fun SplashScreen() {
    val context = LocalContext.current

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.primary
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.displayLarge.copy(
                    fontFamily = Nunito,
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_extra_small)))
            Text(
                text = stringResource(R.string.splash_subtitle),
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontFamily = Nunito,
                    fontWeight = FontWeight.Thin
                )
            )
        }
    }
    LaunchedEffect(Unit) {
        delay(2000)
        context.startActivity(Intent(context, LoginActivity::class.java))
        (context as Activity).finish()
    }
}