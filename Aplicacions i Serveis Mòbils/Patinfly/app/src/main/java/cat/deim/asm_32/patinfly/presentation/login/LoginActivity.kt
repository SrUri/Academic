package cat.deim.asm_32.patinfly.presentation.login

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import cat.deim.asm_32.patinfly.data.datasource.local.UserLocalDataSource
import cat.deim.asm_32.patinfly.data.repository.UserRepository
import cat.deim.asm_32.patinfly.domain.usecase.LoginUseCase
import cat.deim.asm_32.patinfly.ui.theme.PatinflyTheme

class LoginActivity : ComponentActivity() {

    private val TAG = LoginActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(TAG, "LoginActivity onCreate. Before setContent Execution")

        setContent {
            PatinflyTheme {
                val context = LocalContext.current
                val loginUseCase = LoginUseCase(
                    UserRepository(
                        UserLocalDataSource.getInstance(context)
                    )
                )
                LoginScreen(loginUseCase = loginUseCase)
            }
        }
        Log.d(TAG, "After setContent Execution")
    }
}