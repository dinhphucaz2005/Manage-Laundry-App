package com.example.manage.laundry.ui.customer

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.manage.laundry.R
import com.example.manage.laundry.viewmodel.CustomerState
import com.example.manage.laundry.viewmodel.CustomerViewModel

@Composable
fun CustomerHomeContent(viewModel: CustomerViewModel) {

    LaunchedEffect(Unit) {
        viewModel.searchShops()
    }

    val shopSearchState by viewModel.shopSearchState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Search bar
        var searchText by remember { mutableStateOf("") }
        OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            placeholder = { Text("Tìm kiếm tiệm giặt gần bạn...") },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon"
                )
            },
            shape = RoundedCornerShape(12.dp),
            singleLine = true
        )

        // Filter options
        Text(
            text = "Bộ lọc",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(
                listOf(
                    "Gần nhất", "Đánh giá cao", "Giá thấp", "Giặt nhanh",
                    "Giặt khô", "Giặt hấp", "Miễn phí giao hàng"
                )
            ) { filter ->
                FilterChip(
                    selected = false,
                    onClick = { /* TODO: Apply filter */ },
                    label = { Text(filter) },
                    modifier = Modifier.padding(end = 4.dp)
                )
            }
        }

        // Promotion banner
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .clickable { /* TODO: Handle promotion click */ },
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(16.dp)
                ) {
                    Text(
                        text = "GIẢM 20%",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        text = "cho đơn hàng đầu tiên",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "Promotion Image",
                    modifier = Modifier.size(100.dp)
                )
            }
        }

        // Nearby laundry shops
        Text(
            text = "Tiệm giặt gần bạn",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        when (val result = shopSearchState) {
            is CustomerState.ShopSearch.Error -> {
                TODO()
            }

            CustomerState.ShopSearch.Idle -> CircularProgressIndicator()

            CustomerState.ShopSearch.Loading -> CircularProgressIndicator()
            is CustomerState.ShopSearch.Success -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.height(500.dp)
                ) {
                    itemsIndexed(
                        items = result.shops,
                        key = { _, item -> item.shopId }) { _, item ->
                        ShopCard(
                            shop = ShopItem(
                                name = item.name,
                                distance = "${item.location.length} km",
                                rating = item.averageRating.toFloat(),
                                hours = "Mở cửa: ${item.openTime}"
                            ),
                            onClick = {
                                // Handle shop click
                            }
                        )
                    }
                }
            }
        }


    }
}

data class ShopItem(
    val name: String,
    val distance: String,
    val rating: Float,
    val hours: String
)

@Composable
fun ShopCard(shop: ShopItem, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(MaterialTheme.colorScheme.secondaryContainer)
            ) {
                // Here you would ideally load an image from a URL using a library like Coil
                // For this example, we'll use a placeholder
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = "Shop Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = shop.name,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.Star,
                        contentDescription = "Rating",
                        tint = Color(0xFFFFB300),
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = shop.rating.toString(),
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.LocationOn,
                        contentDescription = "Distance",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = shop.distance,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Text(
                    text = shop.hours,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.outline
                )
            }
        }
    }
}