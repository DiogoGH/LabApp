// Pacote do ecrã de edição
package com.example.labdocopo.screens

// Importações necessárias para UI, estado, navegação e base de dados
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditDrinkScreen(navController: NavController, id: Int) {
    // Obtem contexto atual e acesso ao DAO da base de dados
    val context = LocalContext.current
    val dao = LabDoCopoDatabase.getDatabase(context).drinkDao()

    // Obtem todas as bebidas como um State (flow → state)
    val drinks by dao.getAll().collectAsState(initial = emptyList())

    // Procura a bebida com o ID passado por parâmetro
    val drink = drinks.find { it.id == id }

    // Estados para os campos do formulário
    var nome by remember { mutableStateOf("") }
    var descricao by remember { mutableStateOf("") }
    val tipos = listOf("Shot", "Sumo", "Cocktail", "Água", "Outro")
    var tipoSelecionado by remember { mutableStateOf(tipos[0]) }
    var expanded by remember { mutableStateOf(false) }

    // Quando o `drink` for carregado, atualiza os estados locais com os valores atuais da bebida
    LaunchedEffect(drink) {
        drink?.let {
            nome = it.nome
            descricao = it.descricao
            tipoSelecionado = it.categoria
        }
    }

    // Interface do utilizador
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Editar Bebida", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(24.dp))

        // Campo de texto para o nome
        OutlinedTextField(
            value = nome,
            onValueChange = { nome = it },
            label = { Text("Nome") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        // Campo de texto para a descrição
        OutlinedTextField(
            value = descricao,
            onValueChange = { descricao = it },
            label = { Text("Descrição") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        // Dropdown para escolher o tipo da bebida
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            TextField(
                value = tipoSelecionado,
                onValueChange = {},
                readOnly = true,
                label = { Text("Tipo de bebida") },
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

        Spacer(Modifier.height(24.dp))

        // Botão para guardar as alterações
        Button(
            onClick = {
                drink?.let {
                    // Cria uma nova cópia da bebida com os valores atualizados
                    val updated = it.copy(
                        nome = nome,
                        descricao = descricao,
                        categoria = tipoSelecionado
                    )

                    // Guarda a bebida na base de dados (insert com mesmo ID faz update em Room)
                    CoroutineScope(Dispatchers.IO).launch {
                        dao.insert(updated)
                    }

                    // Volta para o ecrã anterior
                    navController.popBackStack()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar")
        }
    }
}
