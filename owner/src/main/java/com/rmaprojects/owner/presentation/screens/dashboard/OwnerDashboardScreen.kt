package com.rmaprojects.owner.presentation.screens.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.rmaprojects.core.presentation.components.BranchCard
import com.rmaprojects.core.presentation.screen.SharedErrorScreen
import com.rmaprojects.core.presentation.screen.SharedLoadingScreen
import com.rmaprojects.owner.navigation.OwnerNavigation

@OptIn(ExperimentalMaterial3Api::class)
@Destination<OwnerNavigation>(start = true)
@Composable
fun OwnerDashboardScreen(
    navigator: DestinationsNavigator,
    viewModel: DashboardViewModel = hiltViewModel()
) {

    val branchListState = viewModel.branchListState.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        viewModel.retrieveAllBranch()
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Dashboard") })
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(12.dp)
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                "Hai owner,",
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                "Pendapatan Bulan ini:,",
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(24.dp))
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(128.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(18.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    branchListState.value.DisplayResult(
                        onLoading = { CircularProgressIndicator(modifier = Modifier.size(32.dp)) },
                        onSuccess = {
                            Text(text = "${viewModel.getMonthIncomeTotal(it)}")
                        },
                        onError = {
                            Text(text = "Error!", color = MaterialTheme.colorScheme.error)
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(
                    onClick = {

                    }
                ) {
                    Text(text = "Analisis")
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(modifier = Modifier.weight(1F), text = "(Top) Penghasilan tiap cabang")
                TextButton(modifier = Modifier.weight(1F), onClick = { }) {
                    Text(text = "Tampilkan semua")
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            branchListState.value.DisplayResult(
                modifier = Modifier.fillMaxSize(),
                onLoading = {
                    SharedLoadingScreen()
                },
                onSuccess = { branchList ->

                    LazyColumn {
                        items(branchList) { branchData ->
                            BranchCard(
                                branchName = branchData.name,
                                totalIncomeThisMonth = branchData.totalIncome
                            ) {

                            }
                        }
                    }
                },
                onError = {
                    SharedErrorScreen(
                        onRetryAction = { viewModel.retrieveAllBranch() },
                        exceptionMessage = it
                    )
                }
            )
        }
    }

}