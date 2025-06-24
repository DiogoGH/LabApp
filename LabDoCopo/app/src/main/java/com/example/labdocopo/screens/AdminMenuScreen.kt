// Pacote onde está o ecrã
package com.example.labdocopo.screens

// Importações necessárias para UI com Jetpack Compose
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

// Função Composable que representa o menu do administrador
@Composable
fun AdminMenuScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()        // Ocupa todo o ecrã
            .padding(24.dp),      // Espaçamento em volta do conteúdo
        verticalArrangement = Arrangement.Center,           // Alinha verticalmente ao centro
        horizontalAlignment = Alignment.CenterHorizontally  // Alinha horizontalmente ao centro
    ) {
        // Título do ecrã
        Text("Conta Admin", style = MaterialTheme.typography.headlineSmall)

        Spacer(Modifier.height(24.dp)) // Espaço entre o título e o botão

        // Botão para ver a lista de bebidas criadas
        Button(
            onClick = { navController.navigate("my_drinks") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Lista de Bebidas")
        }

        Spacer(Modifier.height(8.dp)) // Espaço entre os botões

        // Botão para fazer logout (voltar ao login)
        OutlinedButton(
            onClick = { navController.navigate("login") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Sair")
        }
    }
}
