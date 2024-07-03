package com.rmaprojects.core.common

import androidx.compose.ui.graphics.vector.ImageVector
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec

data class BottomBarNavigation(
    val direction: DirectionDestinationSpec,
    val icon: ImageVector,
    val label: String
)