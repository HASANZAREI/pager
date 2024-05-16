package com.example.pager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pager.ui.theme.PagerTheme
import kotlin.math.absoluteValue

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PagerTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    val pic = listOf(
                        R.drawable.image1,
                        R.drawable.image2,
                        R.drawable.image3,
                        R.drawable.image4,
                        R.drawable.image5,
                        R.drawable.image1,
                        R.drawable.image2,
                        R.drawable.image3,
                        R.drawable.image4,
                        R.drawable.image5,
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        val pagerState = rememberPagerState(
                            pageCount = {
                                pic.count()
                            }
                        )
                        val pagerState2 = rememberPagerState(
                            pageCount = {
                                pic.count()
                            }
                        )
                        HorizontalPager(
                            modifier = Modifier
                                .size(250.dp),
                            state = pagerState
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(250.dp)
                                    .applyCubic(pagerState, it, true),
                            ) {
                                Image(
                                    modifier = Modifier.fillMaxSize(),
                                    painter = painterResource(id = pic[it]),
                                    contentScale = ContentScale.Crop,
                                    contentDescription = ""
                                )
                            }
                        }
                        Spacer(modifier = Modifier.size(16.dp))
                        VerticalPager(
                            modifier = Modifier
                                .size(250.dp),
                            state = pagerState2
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(250.dp)
                                    .applyCubic(pagerState2, it, false),
                            ) {
                                Image(
                                    modifier = Modifier.fillMaxSize(),
                                    painter = painterResource(id = pic[it]),
                                    contentScale = ContentScale.Crop,
                                    contentDescription = ""
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
fun Modifier.applyCubic(state: PagerState, page: Int, isHorizontal: Boolean = true): Modifier {
    return graphicsLayer {
        val pageOffset = state.offsetForPage(page)
        val offScreenRight = pageOffset < 0f
        val def = if (isHorizontal) {
            105f
        } else {
            -100f
        }
        val interpolated = FastOutLinearInEasing.transform(pageOffset.absoluteValue)

        val  rotation = (interpolated*if (offScreenRight) def else -def).coerceAtMost(90f)
        if (isHorizontal) {
            rotationY = rotation
        } else {
            rotationX = rotation
        }
        // rotationY = (interpolated*if (offScreenRight) def else -def).coerceAtMost(90f)

        transformOrigin = if (isHorizontal) {
            TransformOrigin(
                pivotFractionX = if (offScreenRight) 0f else 1f,
                pivotFractionY = .5f
            )
        } else {
            TransformOrigin(
                pivotFractionY = if (offScreenRight) 0f else 1f,
                pivotFractionX = .5f
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
fun PagerState.offsetForPage(page: Int) = (currentPage - page) + currentPageOffsetFraction

@OptIn(ExperimentalFoundationApi::class)
fun PagerState.startOffsetForPage(page: Int) = offsetForPage(page).coerceAtLeast(minimumValue = 0f)

@OptIn(ExperimentalFoundationApi::class)
fun PagerState.endOffsetForPage(page: Int) = offsetForPage(page).coerceAtMost(maximumValue = 0f)

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PagerTheme {
    }
}