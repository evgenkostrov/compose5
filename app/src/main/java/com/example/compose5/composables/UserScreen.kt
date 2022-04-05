package com.example.compose5.composables

import androidx.annotation.DrawableRes
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.compose5.R
import com.example.compose5.UserProfile
import com.example.compose5.ui.theme.*
import com.narcissus.marketplace.ui.user.UserViewModel

@Composable
fun UserScreen(
    viewModel: UserViewModel,
    userProfile: UserProfile,
    isAppInDarkTheme: Boolean,
) {
    Column {
        TopAppBar(
            title = {
                Text(
                    text = "Profile",
                    style = MaterialTheme.typography.h5.regular,
                )
            },
            backgroundColor = MaterialTheme.colors.surface,
        )

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState()),
        ) {
            ProfileInfo(userProfile)

            Header(text = "My Profile")

            Item(
                text = "Orders",
                iconResId = R.drawable.ic_cart,
            ) {
                viewModel.goToOrders()
            }

            Item(
                text = "Billing",
                iconResId = R.drawable.ic_card,
            ) {
                viewModel.goToBilling()
            }

            Item(
                text = "Sign out",
                iconResId = R.drawable.ic_sign_out,
            ) {
                viewModel.signOut()
            }

            Header(text = "Application")

            SwitchItem(
                text = "Dark Theme",
                iconResId = R.drawable.ic_crescent,
                checked = isAppInDarkTheme,
            ) { checked ->
                viewModel.switchTheme(checked)
            }

            Item(
                text = "Clear data",
                iconResId = R.drawable.ic_broom,
            ) {
                viewModel.clearData()
            }

            Item(
                text = "Report bug",
                iconResId = R.drawable.ic_bug,
            ) {
                viewModel.reportBug()
            }

            Item(
                text = "Source code",
                iconResId = R.drawable.ic_code,
            ) {
                viewModel.goToSourceCode()
            }
        }
    }
}

@Composable
fun ProfileInfo(userProfile: UserProfile) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(userProfile.iconUrl)
                .crossfade(true)
                .build(),
            fallback = painterResource(R.drawable.profile_avatar_placeholder_large),
            error = painterResource(R.drawable.profile_avatar_placeholder_large),
            contentScale = ContentScale.Crop,
            contentDescription = "Profile picture",
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape),
        )

        Spacer(modifier = Modifier.height(DefaultPadding))

        Text(
            text = userProfile.name ?: "nullable? wtf???",
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.onPrimary,
        )

        Text(
            fontFamily = Montserrat,
            text = userProfile.email,
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.colors.secondaryVariant,
        )

        Spacer(modifier = Modifier.height(32.dp))
    }
}

val dummyUser = UserProfile(
    "some id",
    "Joe Ordinary",
    "example@gmail.com",
    "https://301-1.ru/uploads/image/ha-ha-ya-zdes-zhivu_pOLMNliEp9.jpeg",
)

@Preview
@Composable
fun ProfileInfoPreviewLight() {
    LightTheme {
        ProfileInfo(dummyUser)
    }
}

@Preview
@Composable
fun ProfileInfoPreviewDark() {
    DarkTheme {
        ProfileInfo(dummyUser)
    }
}

@Composable
fun Header(text: String) {
    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = Modifier
            .fillMaxWidth()
            .height(HeaderHeight)
            .padding(horizontal = DefaultPadding, vertical = HalfPadding)
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colors.onSecondary),
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.subtitle1,
            color = MaterialTheme.colors.onPrimary,
            modifier = Modifier.padding(horizontal = HalfPadding),
        )
    }
}

@Preview
@Composable
fun HeaderPreview() {
    DefaultTheme {
        Header(text = "My Profile")
    }
}

@Composable
fun Item(
    text: String,
    @DrawableRes iconResId: Int,
    tailContent: @Composable () -> Unit = {},
    onClick: () -> Unit = {},
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(ItemHeight)
            .padding(horizontal = DefaultPadding)
            .clip(MaterialTheme.shapes.medium)
            .clickable { onClick() },
    ) {
        Spacer(modifier = Modifier.width(HalfPadding))

        Image(
            painter = painterResource(id = iconResId),
            contentDescription = text,
            colorFilter = ColorFilter.tint(MaterialTheme.colors.onPrimary),
            modifier = Modifier.size(IconSize),
        )

        Spacer(modifier = Modifier.width(IntermediatePadding))

        Text(
            text = text,
            style = MaterialTheme.typography.body1.regular,
            modifier = Modifier.padding(vertical = SmallPadding),
            color = MaterialTheme.colors.onPrimary,
        )

        Spacer(modifier = Modifier.fillMaxWidth(0.8f))

        tailContent()
    }
}

@Composable
@Preview
fun ItemPreviewLight() {
    LightTheme {
        Item("Orders", R.drawable.ic_cart) {}
    }
}

@Composable
@Preview
fun ItemPreviewDark() {
    DarkTheme {
        Item("Orders", R.drawable.ic_cart) {}
    }
}

@Composable
fun SwitchItem(
    text: String,
    @DrawableRes iconResId: Int,
    checked: Boolean,
    onChecked: (Boolean) -> Unit,
) {
    Item(
        text = text,
        iconResId = iconResId,
        tailContent = {
            var isChecked by remember { mutableStateOf(checked) }
            Switch(
                checked = isChecked,
                onCheckedChange = {
                    isChecked = !isChecked
                    onChecked(isChecked)
                },
            )

            Spacer(modifier = Modifier.width(DefaultPadding))
        },
    ) {}
}

@Composable
@Preview
fun SwitchItemPreviewLight() {
    LightTheme {
        SwitchItem("Orders", R.drawable.ic_cart, true) {}
    }
}

@Composable
@Preview
fun SwitchItemPreviewDark() {
    DarkTheme {
        SwitchItem("Orders", R.drawable.ic_cart, true) {}
    }
}
