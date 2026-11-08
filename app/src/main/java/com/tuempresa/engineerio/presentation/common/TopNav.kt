package com.tuempresa.engineerio.presentation.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.tuempresa.engineerio.R
import com.tuempresa.engineerio.domain.model.UserStats

@Composable
fun TopNav(
    userStats: UserStats,
    onProfileClick: () -> Unit,
    onMapClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Logo que regresa al Mapa de Aprendizaje
        Image(
            painter = painterResource(id = R.drawable.systego_logo),
            contentDescription = "SysteGo Logo",
            modifier = Modifier
                .size(40.dp)
                .clickable { onMapClick() }
        )

        // Sección de Vidas (Corazones) y Perfil
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Contador de vidas
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Vidas",
                    tint = Color.Red
                )
                Text(
                    text = "${userStats.hearts}",
                    modifier = Modifier.padding(start = 4.dp)
                )
            }

            // Icono de perfil que abre la pantalla de Perfil
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Perfil de Usuario",
                tint = Color.Gray,
                modifier = Modifier
                    .size(28.dp)
                    .clickable { onProfileClick() }
            )
        }
    }
}
