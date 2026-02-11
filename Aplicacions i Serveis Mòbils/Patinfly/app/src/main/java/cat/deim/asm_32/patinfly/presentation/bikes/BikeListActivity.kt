package cat.deim.asm_32.patinfly.presentation.bikes

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cat.deim.asm_32.patinfly.data.datasource.local.BikeLocalDataSource
import cat.deim.asm_32.patinfly.data.repository.BikeRepository
import cat.deim.asm_32.patinfly.domain.usecase.BikeListUseCase
import cat.deim.asm_32.patinfly.ui.theme.PatinflyTheme
import cat.deim.asm_32.patinfly.presentation.profile.ProfileActivity

class BikeListActivity : ComponentActivity() {

    private val TAG = BikeListActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(TAG, "BikeListActivity onCreate")

        val dataSource = BikeLocalDataSource.getInstance(applicationContext)
        val repository = BikeRepository(dataSource)
        val useCase = BikeListUseCase(repository)

        Log.d(TAG, "Creant viewModel Bike...")

        val viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return BikeListViewModel(useCase) as T
            }
        })[BikeListViewModel::class.java]

        Log.d(TAG,"Before setContent Execution")

        setContent {
            PatinflyTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    BikeListScreen(
                        viewModel = viewModel,
                        onProfileClick = {
                            startActivity(Intent(this, ProfileActivity::class.java))
                        },
                        onBackClick = {
                            finish()
                        }
                    )
                }
            }
        }
        Log.d(TAG, "After setContent Execution")
    }
}