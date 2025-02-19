# HtmlAnalyzer

Programa desenvolvido em Java, com a finalidade de analisar o conteúdo HTML de uma URL, e retornar o texto no nível mais profundo da estrutura.

## Funciona como um detetive de estrutura HTML:

- Baixa o HTML da URL.

- Analisa cada linha para classificar tags e texto.

- Rastreia a profundidade usando uma pilha.

- Valida erros e retorna o resultado.


## Requisitos

- JDK 17 instalado.

### Instalando JDK via terminal Linux(Ubuntu/Debian):
- Para instalar, executar no terminal:
bash
    sudo apt-update
    sudo apt install default-jdk

Verifique utilizando: 
bash
    java --version


## Como Usar

Navegue até a pasta onde está o arquivo:

cd /caminho/para/o/projeto

Compile o programa:
1. Compilar o programa no terminal:
bash
   javac HtmlAnalyzer.java

2. Executar passando uma URL:(exemplos)
bash
   java HtmlAnalyzer http://hiring.axreng.com/internship/example1.html
# Saída: "Este é o título."

java HtmlAnalyzer https://www.lncc.br/~borges/php/testar.html
# Saída: "malformed HTML"

3. O programa deve retornar os seguintes exemplos de saídas, de acordo com as respectivas analises:

## HTML bem formado:
    Este é o título.

## HTML Malformado:
    malformed HTML

## Erro de Conexão:
    URL connection error

🔍 Funcionalidades
Identificação de Texto Profundo: Encontra o texto no nível mais aninhado do HTML.

Validação de Tags: Detecta tags desbalanceadas, com atributos ou mal escritas.

Tratamento de Erros: Retorna mensagens claras para HTML malformado ou falhas de conexão.

# 🛠️ Estrutura do Código
HtmlAnalyzer.java: Classe principal com lógica de download e análise do HTML.
Responsável por duas coisas principais: primeiro, baixar o conteúdo HTML da URL que o usuário fornece, e segundo, analisar esse HTML linha por linha para encontrar o texto mais profundo e verificar se o HTML está bem formado. É como o 'cérebro' do programa, que coordena todas as operações e garante que tudo funcione corretamente.

HtmlParsingResult: Classe interna criada para guardar tudo o que o programa descobre durante a análise do HTML. Ela armazena o texto mais profundo encontrado, a profundidade máxima e também se o HTML está malformado. É como uma 'caixinha' que organiza todas as informações importantes que o programa precisa retornar."

# Métodos Chave:

- fetchHtmlFromUrl(): Baixa o conteúdo HTML.

- parseHtml(): Analisa o HTML linha a linha.

- isOpeningTag(), isClosingTag(): Valida tags via regex.

# 📝 Notas Técnicas
Pilha (Deque): Usada para rastrear tags abertas e calcular a profundidade.

Regex: Garante que tags sigam o formato <tag> sem atributos.



