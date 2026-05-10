# Sistema de Triagem Hospitalar - EDA2

## Objetivo do Projeto
O objetivo deste projeto é implementar um sistema de triagem de pacientes para atendimento hospitalar de emergência. O sistema utiliza uma **Fila de Prioridade estruturada em MaxHeap** para garantir que os casos mais graves sejam atendidos primeiro, simulando a tomada de decisão clínica em tempo real com base em uma taxonomia de riscos e sintomas.

## Características dos Dados e Arquitetura

* **Score de Prioridade Dinâmico:** A ordem de atendimento não é por ordem de chegada, mas sim pelo risco de morte. O Score é calculado matematicamente somando três domínios clínicos: Sintomas Agudos (visita atual), Fatores de Risco e Histórico Clínico (banco de dados). Pacientes mais graves sobem para a raiz da árvore (posição 0).
* **Fila de Espera (MaxHeap):** Estrutura de dados com complexidade $O(\log N)$ para inserção (Shift-Up) e remoção (Shift-Down), garantindo altíssima performance mesmo com uma sala de espera lotada.
* **Cadastro e Busca Rápida:** O banco de dados fixo do hospital (cadastro de pacientes) é ordenado pelo CPF utilizando **Insertion Sort**. Isso permite que o sistema identifique os dados do paciente instantaneamente na recepção utilizando **Busca Binária** com complexidade $O(\log N)$.
* **Persistência em Arquivo:** Os dados fixos são salvos e carregados de um arquivo `pacientes.csv` utilizando um serviço próprio de manipulação de Strings e conversão de Enums.

## Divisão de Responsabilidades

Conforme a evolução do projeto e a arquitetura adotada, o desenvolvimento foi fatiado da seguinte maneira:

### Davi
* Estruturação e algoritmo do motor de Fila de Prioridade (**MaxHeap**).
* Taxonomia clínica em domínios isolados (`FatorRisco`, `HistoricoClinico`, `SintomaAgudo`).

### Mateus
* Idealização do sistema de pesos e gravidade clínica (Inspirado no Protocolo de Manchester).
* Implementação dos algoritmos utilitários do pacote de estruturas (**Busca Binária** e **Insertion Sort**).
* Implementação do motor de persistência de dados (`PersistenciaService`).

### Responsabilidade Compartilhada
* Refatoração contínua, aplicação de princípios de Clean Code (como SRP - Single Responsibility Principle) 
* Integração dos serviços do backend com a interface gráfica.
* Construção da interface gráfica de Triagem em Java Swing.


## 💻 Como Executar - EM BREVE

A aplicação foi desenhada para rodar de forma leve e direta, gerenciando seu próprio banco de dados em tempo de execução.

**Pré-requisitos:** JDK 25 ou superior.

1. Clone o repositório e abra o projeto na sua IDE de preferência (IntelliJ IDEA, Eclipse, VS Code).
2. Certifique-se de que a pasta `src/` está marcada como o diretório de fontes (Sources Root).
3. Verifique se o arquivo `pacientes.csv` está localizado na **raiz do projeto** (junto com a pasta `src/`). Se o arquivo não existir, o sistema criará uma base vazia automaticamente ao rodar.
4. Navegue até o arquivo `src/Main.java`.
5. Execute a classe `Main`. O sistema carregará a massa de dados para a memória ordenando os CPFs automaticamente, e inicializará a Interface Gráfica de Atendimento.

## 🎥 Demonstração Visual - EM BREVE

**Assista ao nosso vídeo explicativo no YouTube:** [![Vídeo de Demonstração](https://img.youtube.com/vi/---/maxresdefault.jpg)](https://youtu.be/---)

### Capturas de Tela do Sistema - EM BREVE
....

## Equipe de Desenvolvimento

| <img src="docs/assets/fotos/Davi-UnB.png" width="120px;" alt="Davi Freitas"/><br />**Davi Freitas** | <img src="docs/assets/fotos/Mateus0xC.png" width="120px;" alt="Mateus Barreto"/><br />**Mateus Barreto** |
| :---: | :---: |
| Matrícula: **241011018** | Matrícula: **241011466** |
| <img src="https://github.com/Davi-UnB.png" width="16px;"/> [`@Davi-UnB`](https://github.com/Davi-UnB) | <img src="https://github.com/Mateus0xC.png" width="16px;"/> [`@Mateus0xC`](https://github.com/Mateus0xC) <img src="https://github.com/gecko1205.png" width="16px;"/> [`@gecko1205`](https://github.com/gecko1205) |