package cat.deim.asm_32.patinfly.presentation.profile

import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.core.app.ActivityCompat
import cat.deim.asm_32.patinfly.R
import cat.deim.asm_32.patinfly.data.datasource.model.UserModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(usuari: UserModel) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.profile)) },
                navigationIcon = {
                    IconButton(onClick = {
                        ActivityCompat.finishAfterTransition(context as ComponentActivity)
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_back),
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(dimensionResource(R.dimen.padding_medium))
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(dimensionResource(R.dimen.profile_card_corner_radius)),
                elevation = CardDefaults.cardElevation(dimensionResource(R.dimen.profile_card_elevation))
            ) {
                Column(
                    modifier = Modifier
                        .padding(dimensionResource(R.dimen.padding_medium))
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.profile_picture),
                            contentDescription = stringResource(R.string.profile_picture_desc),
                            modifier = Modifier
                                .size(dimensionResource(R.dimen.profile_image_size))
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                        Column {
                            Text(
                                text = usuari.name,
                                style = MaterialTheme.typography.headlineSmall.copy(
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_extra_small)))
                            Text(
                                text = usuari.email,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                    HorizontalDivider()
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = dimensionResource(R.dimen.padding_small)),
                        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_medium))
                    ) {
                        DetallesText(stringResource(R.string.uuid_label), usuari.uuid)
                        DetallesText(stringResource(R.string.device_id_label), usuari.deviceId)
                        DetallesText(stringResource(R.string.created_label), usuari.creationDate.toString())
                        DetallesText(stringResource(R.string.last_connection_label), usuari.lastConnection.toString())
                    }
                }
            }
        }
    }
}

@Composable
fun DetallesText(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.width(dimensionResource(R.dimen.label_width))
        )
        Text(text = value)
    }
}