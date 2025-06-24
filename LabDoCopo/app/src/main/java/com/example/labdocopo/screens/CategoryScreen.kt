// Pacote onde se encontra o ecrã de categorias
package com.example.labdocopo.screens

// Importações necessárias para layout e UI
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

// Função Composable para mostrar bebidas de uma determinada categoria
@Composable
fun CategoryScreen(categoria: String, navController: NavController) {

    // Lista simulada de bebidas com nome e ingredientes fictícios
    val bebidas = List(4) { "$categoria Exemplo ${it + 1}" to "Ingredientes demo" }

    Column(
        modifier = Modifier
            .fillMaxSize()    // Ocupa todo o ecrã
            .padding(16.dp)   // Espaçamento interno
    ) {
        // Título com o nome da categoria recebida
        Text(categoria, style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(16.dp)) // Espaço abaixo do título

        // Para cada bebida na lista, mostra um cartão com o nome e descrição
        bebidas.forEach { (nome, desc) ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()          // Ocupa a largura total
                    .padding(vertical = 8.dp) // Espaço vertical entre os cartões
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(nome, style = MaterialTheme.typography.titleMedium)  // Nome da bebida
                    Text(desc, style = MaterialTheme.typography.bodyMedium)   // Ingredientes (simulados)
                }
            }
        }
    }
}
