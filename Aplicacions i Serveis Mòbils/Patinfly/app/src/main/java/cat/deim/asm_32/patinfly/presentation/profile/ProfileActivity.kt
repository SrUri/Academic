package cat.deim.asm_32.patinfly.presentation.profile

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import cat.deim.asm_32.patinfly.data.datasource.local.UserLocalDataSource
import cat.deim.asm_32.patinfly.ui.theme.PatinflyTheme

class ProfileActivity : ComponentActivity() {

    private val tag = ProfileActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(tag, "ProfileActivity onCreate. Before setContent Execution")

        val user = UserLocalDataSource.getInstance(applicationContext).getUser()

        setContent {
            PatinflyTheme {
                user?.let {
                    ProfileScreen(usuari = it)
                }
            }
        }
        Log.d(tag, "After setContent Execution")
    }
}