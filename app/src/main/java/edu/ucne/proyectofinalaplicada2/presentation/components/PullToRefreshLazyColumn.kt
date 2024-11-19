@file:OptIn(ExperimentalMaterial3Api::class)

package edu.ucne.proyectofinalaplicada2.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import edu.ucne.proyectofinalaplicada2.ui.theme.ProyectoFinalAplicada2Theme

@Composable
fun <I, E> PullToRefreshLazyColumn(
    items: List<I>,
    content: @Composable (I) -> Unit,
    isRefreshing: Boolean,
    onRefresh: (E) -> Unit,
    onEvent: E,
    modifier: Modifier = Modifier,
    lazyListState: LazyListState = rememberLazyListState()
){
    val pullToRefreshState = rememberPullToRefreshState()

    Box(
        modifier = modifier
            .nestedScroll(pullToRefreshState.nestedScrollConnection)
    ){
        LazyColumn(
            state = lazyListState,
            contentPadding = PaddingValues(8.dp),
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ){
            items(items){
                content(it)
            }
        }

        if(pullToRefreshState.isRefreshing){
            LaunchedEffect(key1 = true) {
                onRefresh(onEvent)
            }
        }

        LaunchedEffect(isRefreshing) {
            if(isRefreshing){
                pullToRefreshState.startRefresh()
            } else{
                pullToRefreshState.endRefresh()
            }
        }

        PullToRefreshContainer(
            state = pullToRefreshState,
            modifier = Modifier
                .align(Alignment.TopCenter)
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PullToRefreshLazyColumnPreview(){
    ProyectoFinalAplicada2Theme {
        PullToRefreshLazyColumn(
            items = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10),
            content = {
                Text(text = it.toString())
            },
            isRefreshing = false,
            onRefresh = {},
            onEvent = {}
        )
    }
}