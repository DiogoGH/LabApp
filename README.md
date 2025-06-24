# LabDoCopo - App de Bebidas

## Descrição

O **LabDoCopo** é uma aplicação de bebidas onde os utilizadores podem criar as suas próprias bebidas, visualizar as bebidas existentes, e até editar ou apagar as suas criações. A app utiliza o **Room Database** para persistência local de dados e permite autenticação simples com **login por nome e password**.

### Funcionalidades
- **Registo de utilizador**: Registar novos utilizadores com nome e password.
- **Login**: Login feito com nome e password.
- **Criação de bebidas**: O utilizador pode criar bebidas personalizadas, com nome, descrição e tipo (ex: Shot, Sumo, Cocktail).
- **Edição e Apagamento de Bebidas**: Permite editar ou apagar as bebidas existentes.
- **Filtro por categoria**: Exibe as bebidas filtradas por tipo (Shot, Sumo, Cocktail, etc).

---

## Tecnologias Utilizadas

- **Kotlin**: Linguagem de programação principal.
- **Jetpack Compose**: Biblioteca para construir a interface do utilizador.
- **Room Database**: Persistência de dados local para armazenar as bebidas e utilizadores.
- **Coroutines**: Para realizar operações assíncronas com Room.

---

## Requisitos

- Android Studio
- JDK 11+
- Dispositivo Android ou Emulador

---

## Como Executar o Projeto

1. **Clone o repositório**:

```bash
git clone https://github.com/teu-usuario/LabDoCopo.git
