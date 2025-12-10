<div align="center">

# 🎬 Java-Swing-Modern-Cinema

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Swing](https://img.shields.io/badge/Swing-GUI-blue?style=for-the-badge)
![SQLite](https://img.shields.io/badge/SQLite-07405E?style=for-the-badge&logo=sqlite&logoColor=white)
![Status](https://img.shields.io/badge/Status-Concluído-success?style=for-the-badge)

<p align="center">
  <b>Um sistema PDV (Ponto de Venda) completo para cinemas com interface moderna.</b><br>
  Demonstração de UI/UX avançada utilizando Java Swing puro sem dependências gráficas pesadas.
</p>

</div>

---

## 📖 Sobre o Projeto

**Java-Swing-Modern-Cinema** é um sistema de gestão de cinema e bilheteria desenvolvido para demonstrar que o Java Swing pode ser **moderno, responsivo e bonito**.

O diferencial deste projeto é a implementação de uma **Interface de Usuário (UI) Personalizada** (Look and Feel). Fugimos do cinzento padrão do Java para criar uma experiência visual "Flat/Material Dark", com foco na usabilidade (UX).

## ✨ Funcionalidades Principais

* **🎨 UI/UX Design Moderno:**
    * Tema *Dark Material* 100% personalizado.
    * Componentes customizados: `RoundedButton`, `RoundedTextField`, `Cards` e `Tables`.
* **🎫 Gestão de Bilheteria & Mapa de Assentos:**
    * Seleção visual de assentos interativa.
    * Indicação de status: *Livre, Selecionado, Vendido*.
* **👥 Gestão de Clientes:**
    * Cadastro e visualização de clientes com interface intuitiva.
* **🍿 Bomboniere (Snack Bar):**
    * Carrinho de compras unificado (Ingressos + Produtos).
    * Controle de estoque em tempo real.
* **🖨️ Emissão de Recibos:**
    * Geração automática de PDF usando a biblioteca **iText/OpenPDF**.
    * Layout formatado para impressoras térmicas (cupom não fiscal).
* **📈 Dashboard & Business Intelligence:**
    * Gráficos desenhados nativamente com `Graphics2D`.
    * Relatórios de vendas por período e método de pagamento.
* **💾 Dados:**
    * Banco de dados **SQLite** (portátil e sem necessidade de servidor complexo).

---

## 📸 Galeria de Imagens

<div align="center">

| **Login & Segurança** | **Seleção de Assentos** |
|:---:|:---:|
| <img src="https://github.com/user-attachments/assets/2b2c3c86-3afc-4495-a49d-6a19b39048e2" width="400" alt="Tela de Login"> | <img src="https://github.com/user-attachments/assets/373bd0d5-4004-40f7-929c-df579602fa26" width="400" alt="Mapa de Assentos"> |

**Gestão de Clientes**
<br>
<img src="https://github.com/user-attachments/assets/868339cf-d221-4dda-b32c-3c86345ba283" width="800" alt="Gestão de Clientes">
<br><br>

| **Catálogo de Filmes** | **Bomboniere** |
|:---:|:---:|
| <img src="https://github.com/user-attachments/assets/a13faa8b-5641-4d38-b622-09f50ccfdcda" width="400" alt="Filmes"> | <img src="https://github.com/user-attachments/assets/d79ea682-ab63-4a12-95ae-3fa1f2185dd2" width="400" alt="Produtos"> |

| **Caixa (PDV)** | **Recibo Gerado** |
|:---:|:---:|
| <img src="https://github.com/user-attachments/assets/4b9aa352-7597-4c74-8601-9cd22eeb9da3" width="400" alt="PDV"> | <img src="https://github.com/user-attachments/assets/fe251b8c-ab67-4493-8485-faccd5374e4a" width="400" alt="Recibo PDF"> |

| **Relatórios e Gráficos** | **Dashboard** |
|:---:|:---:|
| <img src="https://github.com/user-attachments/assets/7773ec48-fa74-4b13-937c-2b898c8cb3b0" width="400" alt="Relatórios"> | <img src="https://github.com/user-attachments/assets/c87d3804-f2dc-4918-805f-07929afe4e17" width="400" alt="Dash"> |

</div>

---

## 🚀 Como Executar o Projeto

### Pré-requisitos
* **Java JDK 8** ou superior instalado.
* Uma IDE Java (IntelliJ IDEA, Eclipse ou NetBeans).

### Passos para Instalação

1.  **Clone o repositório:**
    ```bash
    git clone [https://github.com/brunnodev50/Java-Swing-Modern-Cinema.git](https://github.com/brunnodev50/Java-Swing-Modern-Cinema.git)
    ```

2.  **Importe o projeto na sua IDE:**
    * Abra a pasta do projeto clonado.

3.  **Configuração de Dependências (Bibliotecas):**
    * Certifique-se de adicionar os seguintes `.jar` ao **Build Path/Classpath** do projeto (geralmente localizados na pasta `lib` ou via Maven):
        * `sqlite-jdbc.jar` (Driver de Conexão SQLite)
        * `itextpdf.jar` ou `openpdf.jar` (Gerador de PDF)

4.  **Execute a Aplicação:**
    * Localize a classe principal: `src/.../CinemaMasterJava.java` (ou similar).
    * Execute o arquivo (Run).

> **Nota:** O sistema criará automaticamente o arquivo do banco de dados `cinema.db` na raiz do projeto na primeira execução.

### 🔑 Acesso Inicial (Padrão)

Use estas credenciais para o primeiro login:

| Campo | Valor |
| :--- | :--- |
| **Usuário** | `ADMIN` |
| **Senha** | `ADMIN` |

---

## 🛠️ Tecnologias e Ferramentas

* **Linguagem:** [Java](https://www.java.com/) (JDK 17 recomendado)
* **Interface Gráfica:** Java Swing & AWT (Graphics2D)
* **Banco de Dados:** [SQLite](https://www.sqlite.org/)
* **Relatórios:** iText / OpenPDF
* **Arquitetura:** MVC (Model-View-Controller) simplificado.

---

## 👤 Autor

Desenvolvido com 💙 por **Brunno**

[![GitHub](https://img.shields.io/badge/GitHub-brunnodev50-181717?style=for-the-badge&logo=github)](https://github.com/brunnodev50)

---
*Projeto desenvolvido para fins educacionais e de portfólio, focado em Clean Code e UI Design.*
