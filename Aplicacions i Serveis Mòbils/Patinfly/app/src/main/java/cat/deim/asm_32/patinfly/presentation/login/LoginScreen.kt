package cat.deim.asm_32.patinfly.presentation.login

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import cat.deim.asm_32.patinfly.R
import cat.deim.asm_32.patinfly.domain.usecase.LoginUseCase
import cat.deim.asm_32.patinfly.presentation.main.MainActivity
import cat.deim.asm_32.patinfly.data.datasource.local.UserLocalDataSource
import cat.deim.asm_32.patinfly.data.repository.UserRepository
import cat.deim.asm_32.patinfly.ui.theme.Nunito

@Composable
fun LoginScreen(loginUseCase: LoginUseCase) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    fun attemptLogin() {
        try {
            val success = loginUseCase.execute(email, password)
            if (success) {
                val intent = Intent(context, MainActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
                context.startActivity(intent)
            } else {
                error = true
            }
        } catch (e: Exception) {
            error = true
            Log.e("LOGIN", "Error", e)
        }
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .width(dimensionResource(R.dimen.login_form_width))
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
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))

            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text(stringResource(R.string.email_hint)) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
            )
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))

            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(stringResource(R.string.password_hint)) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                        attemptLogin()
                    }
                )
            )
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))

            Button(
                onClick = { attemptLogin() },
                modifier = Modifier.fillMaxWidth(),
                enabled = email.isNotBlank() && password.isNotBlank()
            ) {
                Text(stringResource(R.string.login_button))
            }

            if (error) {
                Text(
                    text = stringResource(R.string.login_error),
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_small))
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewUserLoginForm() {
    val context = LocalContext.current
    LoginScreen(
        loginUseCase = LoginUseCase(
            UserRepository(
                UserLocalDataSource.getInstance(context)
            )
        )
    )
}