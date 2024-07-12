package com.rmaprojects.owner.presentation.screens.laundry

import androidx.lifecycle.ViewModel
import com.rmaprojects.owner.domain.usecases.OwnerUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    useCases: OwnerUseCases
) : ViewModel() {

}