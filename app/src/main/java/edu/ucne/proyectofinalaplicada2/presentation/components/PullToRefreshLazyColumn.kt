@file:OptIn(ExperimentalMaterial3Api::class)

package edu.ucne.proyectofinalaplicada2.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import edu.ucne.proyectofinalaplicada2.ui.theme.ProyectoFinalAplicada2Theme

@Composable
fun PullToRefreshLazyColumn(
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    content: @Composable () -> Unit
){
    val pullToRefreshState = rememberPullToRefreshState()

    Box(
        modifier = Modifier
            .nestedScroll(pullToRefreshState.nestedScrollConnection)
            .fillMaxSize()
    ) {
        content()

        if (pullToRefreshState.isRefreshing) {
            LaunchedEffect(key1 = true) {
                onRefresh()
            }
        }

        LaunchedEffect(isRefreshing) {
            if (isRefreshing) {
                pullToRefreshState.startRefresh()
            } else {
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
            isRefreshing = false,
            onRefresh = {},
            content = {}
        )
    }
}