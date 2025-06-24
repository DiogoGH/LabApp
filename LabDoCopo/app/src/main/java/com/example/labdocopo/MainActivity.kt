// Pacote principal da aplicação
package com.example.labdocopo

// Importações necessárias para Compose, Navegação e UI
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.labdocopo.screens.*

// Classe selada com os ecrãs da navegação e as suas rotas
sealed class Screen(val route: String) {
    object Start : Screen("start")           // Ecrã inicial
    object Register : Screen("register")     // Ecrã de registo
    object Login : Screen("login")           // Ecrã de login
    object MenuUser : Screen("menu_user")    // Menu do utilizador
    object MenuAdmin : Screen("menu_admin")  // Menu do admin
}

// Classe principal da aplicação
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Define o conteúdo da UI com Compose
        setContent {
            val navController = rememberNavController() // Controlador de navegação

            // Navegação entre ecrãs
            NavHost(navController = navController, startDestination = Screen.Start.route) {
                composable(Screen.Start.route) { StartScreen(navController) }
                composable(Screen.Register.route) { RegisterScreen(navController) }
                composable(Screen.Login.route) { LoginScreen(navController) }
                composable(Screen.MenuUser.route) { UserMenuScreen(navController) }
                composable(Screen.MenuAdmin.route) { AdminMenuScreen(navController) }
                composable("create_drink") { CreateDrinkScreen(navController) }
                composable("my_drinks") { MyDrinksScreen(navController) }

                // Ecrã de edição de bebida com passagem de parâmetro (id)
                composable("edit_drink/{id}") { backStackEntry ->
                    val id = backStackEntry.arguments?.getString("id")?.toIntOrNull() ?: 0
                    EditDrinkScreen(navController, id)
                }

                // Ecrã de categorias
                composable("category") { CategoryScreen("Categoria", navController) }
            }
        }
    }
}

// Função Composable que define o ecrã inicial da aplicação
@Composable
fun StartScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()                   // Ocupa o ecrã inteiro
            .background(Color.DarkGray)     // Fundo escuro
            .padding(16.dp),                // Espaçamento interno
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_logo), // Logotipo
            contentDescription = null,
            modifier = Modifier.size(100.dp)
        )
        Spacer(modifier = Modifier.height(16.dp)) // Espaço vertical
        Text("LABORATÓRIO DO COPO", color = Color.White, fontSize = 20.sp) // Título
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = { navController.navigate(Screen.Login.route) }, // Vai para login
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("LOGIN")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { navController.navigate(Screen.Register.route) }, // Vai para registo
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("CRIAR CONTA")
        }
    }
}
