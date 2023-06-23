package com.example.feature.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.feature.R
import com.example.feature.model.NewUiModel
import com.example.feature.ui.contract.HomeContract

@Composable
fun DetailComposable(
    newUiModel: NewUiModel,
    onBackClicked: () -> Unit
) {
    Scaffold(
        backgroundColor = colorResource(R.color.white),
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {
                        onBackClicked.invoke()
                    }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = null,
                            tint = colorResource(R.color.toolbarTextColor)
                        )
                    }
                },
                backgroundColor = colorResource(R.color.toolbarColor),
                title = {
                    Text(
                        text = newUiModel.sourceUiModel.name,
                        color = colorResource(R.color.toolbarTextColor)
                    )
                }
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .background(colorResource(R.color.white))
                    .padding(padding)
                    .fillMaxSize(),
            ) {
                NewCardItem(model = newUiModel)
            }
        }
    )
}

@Composable
private fun NewCardItem(model: NewUiModel) {
    Card(
        backgroundColor = colorResource(R.color.cardViewColor),
        shape = RoundedCornerShape(dimensionResource(R.dimen.dimen_8)),
        modifier = Modifier
            .padding(dimensionResource(R.dimen.dimen_4))
            .clickable { }
    ) {
        Column(
            modifier = Modifier
                .padding(dimensionResource(R.dimen.dimen_16)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(model.urlToImage)
                    .crossfade(true)
                    .build(),
                contentDescription = stringResource(R.string.text_news_header),
                contentScale = ContentScale.Crop,
            )
            Text(
                text = model.title,
                color = colorResource(R.color.textColor),
                modifier = Modifier
                    .padding(top = dimensionResource(R.dimen.dimen_16)),
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )
            Text(
                text = model.author,
                color = colorResource(R.color.textColor),
                modifier = Modifier
                    .padding(top = dimensionResource(R.dimen.dimen_8)),
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )
            Text(
                text = model.publishedAt,
                color = colorResource(R.color.textColor),
                modifier = Modifier
                    .padding(top = dimensionResource(R.dimen.dimen_8)),
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )
            Text(
                text = model.description,
                color = colorResource(R.color.textColor),
                modifier = Modifier
                    .padding(top = dimensionResource(R.dimen.dimen_8)),
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )
            Text(
                text = model.content,
                color = colorResource(R.color.textColor),
                modifier = Modifier
                    .padding(top = dimensionResource(R.dimen.dimen_8)),
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}