package com.anishop.aniShopsellers_android.presentation.ui.screens.main.account

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.anishop.aniShopsellers_android.R
import com.anishop.aniShopsellers_android.presentation.ui.components.appBars.AppTopBar
import com.anishop.aniShopsellers_android.presentation.ui.components.buttons.GradientButton
import com.anishop.aniShopsellers_android.presentation.ui.screens.main.account.viewModel.ProfileScreenViewModel
import com.anishop.aniShopsellers_android.utils.network.UiState

@Composable
fun ProfileScreen(
    onNavigateBack: () -> Unit,
    onUpdateClick: () -> Unit,
    onChangePasswordClick: () -> Unit,
    onPickupAddressClick: () -> Unit,
    profileScreenViewModel: ProfileScreenViewModel = hiltViewModel()
) {
    val uiState by profileScreenViewModel.uiStateGetSellerAccount.collectAsState()
    val context = LocalContext.current
    val seller by profileScreenViewModel.sellerAccount.collectAsState()

    LaunchedEffect(Unit) {
        profileScreenViewModel.getSellerAccount()
    }

    Scaffold(
        topBar = {
            AppTopBar(
                "",
                onBackNavigationClick = {
                    onNavigateBack()
                }
            )
        }
    ) {innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .width(200.dp)
                    .height(200.dp)
                    .clip(RoundedCornerShape(50.dp))
            ){
                Image(
                    painter = painterResource(R.drawable.user),
                    contentDescription = "Default Profile Image",
                    modifier = Modifier.fillMaxSize()
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = seller?.name ?: "",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = seller?.email ?: "",
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleSmall
            )
            Spacer(modifier = Modifier.height(50.dp))
            DetailsRow(
                icon = ImageVector.vectorResource(R.drawable.ic_phone),
                text = ("Mobile Number - ${seller?.mobileNumber ?: ""}")
            )
            DetailsRow(
                icon = ImageVector.vectorResource(R.drawable.ic_person),
                text = ("Seller ID - ${seller?.id ?: ""}")
            )
            DetailsRow(
                icon = ImageVector.vectorResource(R.drawable.id_card),
                text = ("Pan ID - ${seller?.panId ?: ""}")
            )
            DetailsRow(
                icon = ImageVector.vectorResource(R.drawable.ic_verified_user),
                text = ("Verified - ${seller?.isVerified ?: ""}")
            )
            Spacer(modifier = Modifier.height(10.dp))
            GradientButton(
                text = "Update Name or Mobile Number",
                onClick = {onUpdateClick()},
                enabled = true
            )
            GradientButton(
                text = "Change Password",
                onClick = {onChangePasswordClick()},
                enabled = true
            )
            GradientButton(
                text = "Add Pickup Address",
                onClick = {onPickupAddressClick()},
                enabled = true
            )
        }
        if (uiState is UiState.Loading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f))
                    .clickable(enabled = false) { },
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color.White)
            }
        }
    }
    when(uiState){
        is UiState.onSuccess ->{

        }
        is UiState.onFailure ->{
            Toast.makeText(
                context, (uiState as UiState.onFailure).message, Toast.LENGTH_SHORT
            ).show()
            profileScreenViewModel.resetState()
            onNavigateBack()
        }
        else -> Unit
    }
}

@Composable
fun DetailsRow(
    icon: ImageVector,
    text: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "Phone icon"
        )
        Spacer(modifier = Modifier.width(30.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium
        )
    }
}