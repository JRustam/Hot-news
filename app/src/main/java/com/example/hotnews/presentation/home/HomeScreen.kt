package com.example.hotnews.presentation.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.hotnews.data.network.response.Articles
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.hotnews.BuildConfig
import com.example.hotnews.R

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    NewsList(viewModel.articles.collectAsState().value)
}

@Composable
fun NewsList(articles: List<Articles>) {
    LazyColumn(modifier = Modifier.padding(10.dp)) {
        items(articles) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(6.dp),
                colors = CardDefaults.outlinedCardColors(Color.Black)
            ) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    model = it.urlToImage
                        ?: painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = "urlToImage",
                    contentScale = ContentScale.Crop
                )

                Text(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
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

                LinkedText(article = it)
            }
        }
    }
}

@Composable
fun LinkedText(article: Articles) {
    val annotatedString = buildAnnotatedString {
        pushStringAnnotation(tag = article.url, annotation = article.url)

        withStyle(style = SpanStyle(
            color = Color(0xff64B5F6),
            textDecoration = TextDecoration.Underline,
            fontSize = 18.sp
        )) {
            append(article.url)
        }

        pop()
    }

    val uriHandler = LocalUriHandler.current

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