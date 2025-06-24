// Pacote do ecrã de registo
package com.example.labdocopo.screens

// Importações para UI, navegação, base de dados, estado e corrotinas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.labdocopo.data.LabDoCopoUserDatabase
import com.example.labdocopo.data.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.compose.ui.Alignment

@Composable
fun RegisterScreen(navController: NavController) {
    // Estados para os campos de registo
    var nome by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var erro by remember { mutableStateOf("") }

    // Acesso ao contexto e ao DAO da base de dados de utilizadores
    val context = LocalContext.current
    val dao = LabDoCopoUserDatabase.getDatabase(context).userDao()

    // Corrotina para chamadas assíncronas
    val scope = rememberCoroutineScope()

    // Interface gráfica do ecrã de registo
    Column(
        modifier = Modifier
            .fillMaxSize()    // Ocupa todo o ecrã
            .padding(24.dp),  // Espaçamento interior
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Criar Conta", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(24.dp))

        // Campo para o nome
        OutlinedTextField(
            value = nome,
            onValueChange = { nome = it },
            label = { Text("Nome") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        // Campo para o email
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        // Campo para a password (ocultada)
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(Modifier.height(24.dp))

        // Botão para criar conta
        Button(
            onClick = {
                scope.launch {
                    try {
                        // Tenta registar novo utilizador
                        dao.register(User(nome = nome, email = email, password = password))
                        navController.navigate("login") // Vai para o login se for bem-sucedido
                    } catch (e: Exception) {
                        // Mostra erro caso o registo falhe (ex: email já existente)
                        erro = "Email já registado!"
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Criar Conta")
        }

        Spacer(Modifier.height(8.dp))

        // Botão para cancelar e voltar atrás
        OutlinedButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cancelar")
        }

        // Mostra mensagem de erro, se existir
        if (erro.isNotEmpty()) {
            Spacer(Modifier.height(16.dp))
            Text(erro, color = MaterialTheme.colorScheme.error)
        }
    }
}
