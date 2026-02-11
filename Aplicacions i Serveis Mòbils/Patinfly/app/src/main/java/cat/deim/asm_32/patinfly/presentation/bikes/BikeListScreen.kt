package cat.deim.asm_32.patinfly.presentation.bikes

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import cat.deim.asm_32.patinfly.domain.models.Bike
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import cat.deim.asm_32.patinfly.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BikeListScreen(
    viewModel: BikeListViewModel,
    onProfileClick: () -> Unit,
    onBackClick: () -> Unit
) {
    val bicis by viewModel.bikes.collectAsState()
    var actual by remember { mutableStateOf<Bike?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (actual == null) stringResource(R.string.bike_list_title)
                        else stringResource(R.string.bike_details_title)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        if (actual != null) {
                            actual = null
                        } else {
                            onBackClick()
                        }
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_back),
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onProfileClick) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_profile),
                            contentDescription = stringResource(R.string.profile)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        if (actual != null) {
            BikeDetailScreen(
                bike = actual!!,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(bicis) { bike ->
                    EachBike(
                        bici = bike,
                        onDetailsClick = { actual = bike }
                    )
                }
            }
        }
    }
}

@Composable
fun EachBike(bici: Bike, onDetailsClick: () -> Unit = {}) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = dimensionResource(R.dimen.padding_small)),
        elevation = CardDefaults.cardElevation(dimensionResource(R.dimen.elevation_medium)),
        shape = RoundedCornerShape(dimensionResource(R.dimen.corner_radius_medium))
    ) {
        Column(modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))) {
            Image(
                painter = painterResource(id = R.drawable.bike_card),
                contentDescription = stringResource(R.string.bike_image_desc),
                modifier = Modifier
                    .height(dimensionResource(R.dimen.bike_image_height))
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(dimensionResource(R.dimen.corner_radius_large)))
            )
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_medium)))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = bici.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = if (bici.isActive) stringResource(R.string.available)
                    else stringResource(R.string.not_available),
                    color = if (bici.isActive) Color.Green else Color.Red
                )
            }
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_medium)))
            Text(text = "${stringResource(R.string.battery)} ${bici.batteryLvl.toInt()}%")
            Text(text = "${stringResource(R.string.distance)} ${bici.meters}m")
            Text(text = "${stringResource(R.string.type)} ${bici.type.name}")

            if (bici.isActive) {
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_medium)))
                Button(
                    onClick = onDetailsClick,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(dimensionResource(R.dimen.corner_radius_small)),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(stringResource(R.string.view_details), color = Color.White)
                }
            }
        }
    }
}

@Composable
fun BikeDetailScreen(
    bike: Bike,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(dimensionResource(R.dimen.elevation_large))
        ) {
            Column(
                modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium)),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_medium))
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_medium)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.bike_card),
                        contentDescription = stringResource(R.string.bike_photo_desc),
                        modifier = Modifier
                            .size(dimensionResource(R.dimen.profile_image_size))
                            .clip(RoundedCornerShape(dimensionResource(R.dimen.corner_radius_large)))
                    )
                    Text(
                        text = bike.name,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                }
                HorizontalDivider()
                Detalles(stringResource(R.string.id_label), bike.uuid)
                Detalles(stringResource(R.string.type), bike.type.name)
                Detalles(stringResource(R.string.battery), "${bike.batteryLvl.toInt()}%")
                Detalles(stringResource(R.string.distance), "${bike.meters}m")
            }
        }
    }
}

@Composable
private fun Detalles(texto: String, valor: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = texto,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = valor,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}