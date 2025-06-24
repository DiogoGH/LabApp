// Pacote do ecrã de menu do utilizador
package com.example.labdocopo.screens

// Importações necessárias para layout, UI e navegação
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun UserMenuScreen(navController: NavController) {
    // Layout em coluna centralizada
    Column(
        modifier = Modifier
            .fillMaxSize()       // Ocupa todo o ecrã
            .padding(24.dp),     // Espaçamento interno
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Título do ecrã
        Text("Menu Utilizador", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(24.dp))

        // Botão para ir para o ecrã de criação de bebida
        Button(
            onClick = { navController.navigate("create_drink") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Criar Bebida")
        }

        Spacer(Modifier.height(8.dp))

        // Botão para ver lista de bebidas do utilizador
        Button(
            onClick = { navController.navigate("my_drinks") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Vossas Bebidas")
        }

        Spacer(Modifier.height(8.dp))

        // Botão para sair (voltar ao login)
        OutlinedButton(
            onClick = { navController.navigate("login") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Sair")
        }
    }
}

