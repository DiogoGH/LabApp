// Pacote do ecrã de criação de bebida
package com.example.labdocopo.screens

// Importações para layout, UI, base de dados e corrotinas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.labdocopo.data.Drink
import com.example.labdocopo.data.LabDoCopoDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// Marca que vamos usar componentes experimentais do Material 3
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateDrinkScreen(navController: NavController) {
    // Estado do nome da bebida
    var nome by remember { mutableStateOf("") }

    // Estado da descrição da bebida
    var descricao by remember { mutableStateOf("") }

    // Lista de tipos disponíveis
    val tipos = listOf("Shot", "Sumo", "Cocktail", "Água", "Outro")

    // Tipo atualmente selecionado
    var tipoSelecionado by remember { mutableStateOf(tipos[0]) }

    // Controla se o menu dropdown está expandido ou não
    var expanded by remember { mutableStateOf(false) }

    // Contexto e acesso ao DAO da base de dados
    val context = LocalContext.current
    val dao = LabDoCopoDatabase.getDatabase(context).drinkDao()

    Column(
        modifier = Modifier
            .fillMaxSize()     // Ocupa todo o ecrã
            .padding(24.dp),   // Espaçamento interno
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Título da página
        Text("Criar Bebida", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(24.dp))

        // Campo de texto para o nome
        OutlinedTextField(
            value = nome,
            onValueChange = { nome = it },
            label = { Text("Nome da bebida") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        // Campo de texto para a descrição/receita
        OutlinedTextField(
            value = descricao,
            onValueChange = { descricao = it },
            label = { Text("Descrição/receita") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        // Dropdown de seleção do tipo de bebida
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            // Campo que mostra o tipo selecionado (readOnly)
            TextField(
                value = tipoSelecionado,
                onValueChange = {},
                readOnly = true,
                label = { Text("Tipo de bebida") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )

            // Menu suspenso com os tipos disponíveis
            ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
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

        Spacer(Modifier.height(24.dp))

        // Botão para guardar a bebida na base de dados
        Button(
            onClick = {
                CoroutineScope(Dispatchers.IO).launch {
                    // Inserção da bebida usando o DAO da Room
                    dao.insert(Drink(nome = nome, descricao = descricao, categoria = tipoSelecionado))
                }
                // Volta para o ecrã anterior
                navController.popBackStack()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar")
        }
    }
}

