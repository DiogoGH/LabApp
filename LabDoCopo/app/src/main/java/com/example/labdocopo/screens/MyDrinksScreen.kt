// Pacote do ecrã "Minhas Bebidas"
package com.example.labdocopo.screens

// Importações necessárias para UI, estado, base de dados e navegação
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.labdocopo.data.Drink
import com.example.labdocopo.data.LabDoCopoDatabase
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDrinksScreen(navController: NavController) {
    // Contexto atual e acesso ao DAO
    val context = LocalContext.current
    val dao = LabDoCopoDatabase.getDatabase(context).drinkDao()

    // Lista de todas as bebidas observada em tempo real (Flow → State)
    val allDrinks by dao.getAll().collectAsState(initial = emptyList())

    // Escopo para corrotinas
    val scope = rememberCoroutineScope()

    // Lista de tipos para filtro
    val tipos = listOf("Todos", "Shot", "Sumo", "Cocktail", "Água", "Outro")
    var tipoSelecionado by remember { mutableStateOf("Todos") }
    var expanded by remember { mutableStateOf(false) }

    // Filtra as bebidas com base no tipo selecionado
    val bebidasFiltradas = remember(tipoSelecionado, allDrinks) {
        if (tipoSelecionado == "Todos") allDrinks
        else allDrinks.filter { it.categoria == tipoSelecionado }
    }

    // Interface do ecrã
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Vossas Bebidas", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(16.dp))

        // Dropdown para filtrar por tipo
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            TextField(
                value = tipoSelecionado,
                onValueChange = {},
                readOnly = true,
                label = { Text("Filtrar por tipo") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                tipos.forEach { tipo ->
                    DropdownMenuItem(
                        text = { Text(tipo) },
                        onClick = {
                            tipoSelecionado = tipo
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        // Lista de bebidas filtradas
        bebidasFiltradas.forEach { drink ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(drink.nome, style = MaterialTheme.typography.titleMedium)
                    Text(drink.descricao, style = MaterialTheme.typography.bodyMedium)
                    Text("Tipo: ${drink.categoria}", style = MaterialTheme.typography.bodySmall)
                    Spacer(Modifier.height(8.dp))
                    Row {
                        // Botão para editar bebida
                        Button(
                            onClick = {
                                navController.navigate("edit_drink/${drink.id}")
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Editar")
                        }
                        Spacer(Modifier.width(8.dp))
                        // Botão para apagar bebida
                        OutlinedButton(
                            onClick = {
                                scope.launch { dao.delete(drink) }
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Apagar")
                        }
                    }
                }
            }
        }
    }
}

