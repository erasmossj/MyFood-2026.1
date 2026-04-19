# MyFood-2026.1

## Arquitetura do Projeto MyFood2026.1

### Visão Geral

O projeto MyFood2026.1 é uma aplicação de gerenciamento de restaurantes, produtos e pedidos desenvolvida em Java. A arquitetura segue padrões de boas práticas de desenvolvimento, separando responsabilidades em camadas distintas e bem definidas.

---

### Estrutura de Pastas e Justificativas

#### 📁 `exceptions/`

Separa todas as exceções em uma pasta dedicada.

**Justificativa:**
- Centralizar o tratamento de erros, para facilitar a manutenção e visualização de todos os possíveis erros do sistema.
- Cada exceção é específica para um erro do negócio, como `CpfInvalidoException`, `EmpresaJaExisteException`, `UsuarioNaoPodeFazerPedidoException`.
- Facilita a reutilização das exceções em múltiplas classes sem repetição de trechos do código.
- Melhora a legibilidade ao centralizar toda a estratégia de tratamento de erros.

**Exemplos de Exceções:**
- Validação: `CpfInvalidoException`, `NomeInvalidoException`, `EmailInvalidoException`
- Negócio: `EmpresaJaExisteException`, `UsuarioNaoPodeFazerPedidoException`, `NaoPermitidoDoisPedidosException`
- Persistência: `ArquivoNaoEncontradoException`, `FalhaAoSalvarException`

---

#### 📁 `models/`

Modelagem das classes do negócio de forma separada da lógica do serviço.

**Justificativa:**
- `models/` contém as classes que representam as entidades principais do sistema: `Usuario`, `Empresa`, `Produto`, `Pedido`, `Cliente`, `DonoEmpresa`.
- Estas classes encapsulam os dados e a estrutura do negócio.

**Justificativa da Classe `Usuario` ser Abstrata:**

A classe `Usuario` é declarada como abstrata por alguns motivos relacionados a regra de negócio:

1. Existem dois tipos de usuários distintos com comportamentos e operações diferentes:
    - `Cliente`: pode fazer pedidos e buscar produtos, possui cpf.
    - `DonoEmpresa`: pode criar e gerenciar empresas e produtos, não possui cpf.

2. Faz parte do modelo de negócio, além de ser uma boa prática de padrão de projeto, pois evita a criação de instâncias diretas de `Usuario`, uma vez que não faz sentido ter um "usuário genérico" no sistema, já que eles possuem operações e serviços distintos entre si. Dessa forma, o sistema sempre vai lidar com tipos específicos de usuários.

---

#### 📁 `repository/`

Abstrai a persistência de dados em uma camada de repositório.

**Justificativa:**
- `repository/` é responsável pela interação com a persistência de dados.
- Embora este projeto utilize arquivos XML para persistência (não um banco de dados tradicional), o padrão Repository encapsula essa responsabilidade.
- Se for realizado alguma expansão exigiria apenas mudanças nesta camada, facilitando a escalabilidade.

---

#### 📁 `services/`

Centraliza toda a lógica de operações e regras de negócio em serviços.

**Justificativa:**
- `services/` contém as classes responsáveis por orquestrar as operações do sistema:
    - `UsuarioService`: gerencia criação, busca e validação de usuários
    - `EmpresaService`: gerencia criação e consulta de empresas
    - `ProdutoService`: gerencia produtos e seus atributos
    - `PedidoService`: gerencia ciclo de vida dos pedidos

**Por que não usar um único Manager com todos os serviços do sistema?**
- Um único Manager se tornaria uma classe gigante, difícil de manter e entender.
- Torna o código confuso e propenso a erros.
- Dificulta a escalabilidade, pois qualquer mudança em um único serviço poderia afetar todos os outros na classe.

---

#### 📁 `utils/`

Centraliza as funções e utilitários reutilizáveis em um único lugar.

**Justificativa:**
- `utils/` contém classes com métodos que seriam repetidos em múltiplos lugares, evitando trechos de código repetidos.
- No projeto atual, a classe `Validador` é responsável por todas as validações.
- Segue o DRY (Don't Repeat Yourself).
- Melhor manutenção e legibilidade.

---

#### 📁 `data/`

Mantém os arquivos de dados separados do código-fonte.

**Justificativa:**
- Centraliza o local onde os arquivos de persistência são armazenados.
- Facilita a organização e evita confusão entre código e dados.
- Permite fácil acesso e backup dos dados sem interferir no código-fonte.

---

## Padrões de Projeto Utilizados

### **Facade Pattern**
- Fornece uma interface simplificada para o cliente (EasyAccept).
- Reduz complexidade ao fatorar a coordenação entre múltiplos services.

### **Repository Pattern**
- Abstrai persistência de dados.
- Facilita escalabidade e manutenção do armazenamento.

### **Service Layer Pattern**
- Centraliza lógica de negócio.
- Promove reutilização, escalabilidade e testabilidade.

### **Guard Clauses**
- Validações e verificações no início dos métodos.
- Evita aninhamento profundo de condicionais, tornando o código mais limpo e legível.
---

## Fluxo de Execução do Projeto

```
Main.java
  ↓
Facade.java
  ↓
Services (UsuarioService, EmpresaService, ProdutoService, PedidoService)
  ↓
Models (Usuario, Empresa, Produto, Pedido, etc.)
  ↓
Repository (Persistência em XML)
  ↓
Exceptions (Tratamento de erros)
```

---

## ⚠️ Importante Para a Execução do Projeto

### Use a SDK ou JDK compatível

- Por favor, não use a `openjdk20/openjdk25` sugerida automaticamente pelas IDEs (o projeto não vai executar, eu testei em vários computadores) e não execute fora da raiz do projeto.

### Possíveis erros se a SDK/JDK correta não for utilizada, ou se o projeto for executado fora da pasta raiz:
- Falha de compilação
- Falha de execução

---

Cada camada tem um propósito claro e bem definido, visando facilitar a legibilidade, manutenção e escalabilidade do sistema para as entregas das próximas Milestones.

