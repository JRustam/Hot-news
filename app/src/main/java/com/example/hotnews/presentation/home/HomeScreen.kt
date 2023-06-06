package com.example.hotnews.presentation.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.hotnews.R
import com.example.hotnews.data.network.response.Articles

@Composable
fun HomeScreen(modifier: Modifier, viewModel: HomeViewModel = hiltViewModel()) {
    TopAppBarWithMenu()

    NewsList(
        modifier = modifier,
        viewModel.articles.collectAsState().value,
        viewModel.isLoading.collectAsState().value
    )

    if (viewModel.exceptionMessage.collectAsState().value != "") {
        ShowMessage(viewModel.exceptionMessage.collectAsState().value)
    }
}

@Composable
fun NewsList(modifier: Modifier, articles: List<Articles>, isLoading: Boolean) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(progress = if (isLoading) 50f else 0f)
    }

    val uriHandler = LocalUriHandler.current

    LazyColumn(modifier = modifier.padding(vertical = 70.dp, horizontal = 10.dp)) {
        items(articles) {
            Card(
                modifier = modifier
                    .padding(vertical = 15.dp)
                    .fillMaxWidth()
                    .wrapContentHeight(align = Alignment.Top)
                    .clickable {
                        uriHandler.openUri(it.url)
                    },
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(6.dp),
                colors = CardDefaults.outlinedCardColors(Color.Black)
            ) {
                AsyncImage(
                    modifier = modifier.fillMaxSize(),
                    model = it.urlToImage,
                    error = painterResource(id = R.drawable.ic_error),
                    contentDescription = "urlToImage",
                    contentScale = ContentScale.FillBounds
                )

                Text(
                    modifier = modifier.padding(vertical = 8.dp, horizontal = 5.dp),
                    text = it.title,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.White,
                    fontSize = 17.sp,
                    style = TextStyle(
                        fontFamily = FontFamily.SansSerif
                    ),
                    fontWeight = FontWeight.Medium,
                    maxLines = 2
                )

                LinkedText(article = it, uriHandler = uriHandler)

                Text(
                    modifier = modifier.padding(vertical = 8.dp, horizontal = 5.dp),
                    text = it.content ?: "",
                    overflow = TextOverflow.Ellipsis,
                    color = Color.White,
                    fontSize = 17.sp,
                    style = TextStyle(
                        fontFamily = FontFamily.SansSerif
                    ),
                    maxLines = 3
                )
            }
        }
    }
}

@Composable
fun LinkedText(article: Articles, uriHandler: UriHandler) {
    val annotatedString = buildAnnotatedString {
        pushStringAnnotation(tag = article.url, annotation = article.url)

        withStyle(
            style = SpanStyle(
                color = Color(0xff64B5F6),
                textDecoration = TextDecoration.Underline,
                fontSize = 18.sp
            )
        ) {
            append(article.url)
        }

        pop()
    }

    ClickableText(
        modifier = Modifier.padding(vertical = 5.dp, horizontal = 8.dp),
        text = annotatedString,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
        onClick = {
            annotatedString.getStringAnnotations(article.url, it, it)
                .firstOrNull()?.let { stringAnnotation ->
                    uriHandler.openUri(stringAnnotation.item)
                }
        })
}

@Composable
fun ShowMessage(message: String) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxHeight()) {
        Text(
            text = message,
            modifier = Modifier.padding(start = 25.dp),
            textAlign = TextAlign.Center,
            color = Color.Black,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun OverFlowMenu(content: @Composable () -> Unit) {
    var showMenu by remember { mutableStateOf(false) }

    IconButton(onClick = {
        showMenu = !showMenu
    }) {
        Icon(
            imageVector = Icons.Outlined.MoreVert,
            contentDescription = null
        )
    }

    DropdownMenu(
        expanded = showMenu,
        onDismissRequest = {
            showMenu = false
        }) {
        content()
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarWithMenu() {
    TopAppBar(
        title = {
            Text(text = stringResource(id = R.string.app_name))
        },
        actions = {
            OverFlowMenu {
                DropdownMenuItem(text = {
                    Text(text = stringResource(id = R.string.business))
                }, onClick = {})

                DropdownMenuItem(
                    text = {
                        Text(text = stringResource(id = R.string.science))
                    }, onClick = {})
            }
        }
    )
}


