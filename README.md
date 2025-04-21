# Código Horus

![Olho de Horus](https://www.oftalmoandrejorge.com.br/Imagens/dicas/2021/olho_de_horus_poder_e_protecao/Site_01.png)

## Sobre o Projeto

O **Código Horus** é uma linguagem de codificação simples, inspirada no Código Morse, que utiliza os símbolos binários `0` e `1` para representar letras, números e alguns sinais de pontuação. Foi desenvolvido como um projeto para explorar a criação de linguagens de codificação e tradutores em Java.

## Características Principais

- Utiliza apenas os símbolos `0` e `1` para codificação
- Usa `/` como separador entre caracteres e `//` entre palavras
- Suporta letras, números e símbolos de pontuação básicos
- Normaliza caracteres acentuados para sua forma básica (ex: 'á' -> 'A')
- Inclui um tradutor bidirecional (texto para código e código para texto)

## Estrutura do Projeto

O projeto segue o padrão MVC (Model-View-Controller) e está organizado da seguinte forma:

- `src/HorusCodeTranslator.java`: Classe principal (Model) que implementa a lógica de codificação e decodificação
- `src/HorusUI.java`: Interface de linha de comando (View/Controller) para interação com o usuário
- `src/HorusUtils.java`: Classe utilitária com funções para operações de I/O e manipulação de área de transferência

## Como Usar

### Requisitos

- Java Development Kit (JDK) 8 ou superior

### Compilação

```bash
javac src/*.java
```

### Execução

```bash
java src.HorusUI
```

### Funcionalidades

O tradutor oferece as seguintes funcionalidades através de um menu interativo:

1. **Codificar Texto para Código Horus**: Converte texto comum em código Horus
2. **Decodificar Código Horus para Texto**: Converte código Horus de volta para texto
3. **Carregar texto de arquivo**: Lê texto de um arquivo e opcionalmente o codifica
4. **Carregar código Horus de arquivo**: Lê código Horus de um arquivo e opcionalmente o decodifica

Após cada operação, você pode:
- Copiar o resultado para a área de transferência
- Salvar o resultado em um arquivo
- Voltar ao menu principal

## Tabela de Mapeamento

A tabela a seguir mostra os mapeamentos de caracteres para Código Horus:

| Caractere | Código Horus | Caractere | Código Horus |
|-----------|--------------|-----------|--------------|
| A | 01 | N | 10 |
| B | 1000 | O | 111 |
| C | 1010 | P | 0110 |
| D | 100 | Q | 1101 |
| E | 0 | R | 010 |
| F | 0010 | S | 000 |
| G | 110 | T | 1 |
| H | 0000 | U | 001 |
| I | 00 | V | 0001 |
| J | 0111 | W | 011 |
| K | 101 | X | 1001 |
| L | 0100 | Y | 1011 |
| M | 11 | Z | 1100 |

| Caractere | Código Horus | Caractere | Código Horus |
|-----------|--------------|-----------|--------------|
| 0 | 11111 | 5 | 00000 |
| 1 | 01111 | 6 | 10000 |
| 2 | 00111 | 7 | 11000 |
| 3 | 00011 | 8 | 11100 |
| 4 | 00001 | 9 | 11110 |

| Caractere | Código Horus | Caractere | Código Horus |
|-----------|--------------|-----------|--------------|
| . | 010101 | ( | 10110 |
| , | 110011 | ) | 101101 |
| ? | 001100 | " | 010010 |
| ! | 101011 | ' | 011110 |
| - | 100001 | _ | 001101 |
| (espaço) | // (separador de palavra) |  |  |

## Exemplos

### Texto Original:
```
Olá Mundo!
```

### Codificação:
```
111/0100/01//11/001/10/100/111/101011
```

### Decodificação:
```
OLA MUNDO!
```

## Licença

Este projeto está licenciado sob a licença MIT - veja o arquivo [LICENSE](LICENSE) para detalhes.

## Autor

Desenvolvido por Bypass, 2023.