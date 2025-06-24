// Pacote do ecrã de login
package com.example.labdocopo.screens

// Importações para UI, estado, navegação e base de dados
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.labdocopo.data.LabDoCopoUserDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(navController: NavController) {
    // Estados para nome, password e mensagem de erro
    var nome by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var erro by remember { mutableStateOf("") }

    // Obter contexto atual e DAO da base de dados de utilizadores
    val context = LocalContext.current
    val dao = LabDoCopoUserDatabase.getDatabase(context).userDao()

    // Escopo para lançar corrotinas na UI
    val scope = rememberCoroutineScope()

    // Interface do ecrã de login
    Column(
        modifier = Modifier
            .fillMaxSize()    // Ocupa o ecrã todo
            .padding(24.dp),  // Margem interna
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Login", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(24.dp))

        // Campo de nome de utilizador
        OutlinedTextField(
            value = nome,
            onValueChange = { nome = it },
            label = { Text("Nome") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        // Campo de password (com ocultação visual)
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(Modifier.height(24.dp))

        // Botão de login
        Button(
            onClick = {
                scope.launch {
                    // Tenta autenticar o utilizador com nome e password
                    val user = dao.login(nome, password)
                    if (user != null) {
                        // Se for admin, vai para o menu admin
                        if (user.nome.lowercase() == "admin") {
                            navController.navigate("menu_admin")
                        } else {
                            // Caso contrário, vai para o menu do utilizador
                            navController.navigate("menu_user")
                        }
                    } else {
                        // Se não encontrou o utilizador, mostra erro
                        erro = "Credenciais inválidas"
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }

        Spacer(Modifier.height(8.dp))

        // Botão para ir criar conta
        OutlinedButton(
            onClick = { navController.navigate("register") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Criar Conta")
        }

        // Mostra mensagem de erro se existir
        if (erro.isNotEmpty()) {
            Spacer(Modifier.height(16.dp))
            Text(erro, color = MaterialTheme.colorScheme.error)
        }
    }
}
