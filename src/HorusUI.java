package src;

import java.util.Scanner;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Interface de linha de comando para o Tradutor de Código Horus.
 * <p>
 * Esta classe implementa a interface com o usuário do sistema, seguindo o padrão MVC,
 * onde esta classe é a View/Controller, e HorusCodeTranslator é o Model.
 * </p>
 * 
 * @author Bypass
 * @version 1.0
 */
public class HorusUI {
    
    /** Logger para registrar mensagens e erros */
    private static final Logger LOGGER = Logger.getLogger(HorusUI.class.getName());
    
    /** Tradutor de Código Horus (Model) */
    private final HorusCodeTranslator translator;
    
    /** Scanner para entrada do usuário */
    private final Scanner scanner;
    
    /**
     * Construtor que inicializa o tradutor e o scanner de entrada.
     */
    public HorusUI() {
        this.translator = new HorusCodeTranslator();
        this.scanner = new Scanner(System.in, StandardCharsets.UTF_8);
    }

    /**
     * Exibe o menu principal e processa as opções do usuário.
     */
    public void showMainMenu() {
        System.out.println("Bem-vindo ao Tradutor Código Horus!");

        while (true) {
            System.out.println("\nEscolha uma opção:");
            System.out.println("1. Codificar Texto para Código Horus");
            System.out.println("2. Decodificar Código Horus para Texto");
            System.out.println("3. Carregar texto de arquivo");
            System.out.println("4. Carregar código Horus de arquivo");
            System.out.println("5. Sair");
            System.out.print("Opção: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    handleEncodeText();
                    break;
                case "2":
                    handleDecodeHorusCode();
                    break;
                case "3":
                    handleLoadTextFile();
                    break;
                case "4":
                    handleLoadHorusCodeFile();
                    break;
                case "5":
                    System.out.println("Saindo...");
                    scanner.close();
                    return; // Termina o programa
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }
    
    /**
     * Processa a opção de codificar texto para Código Horus.
     */
    private void handleEncodeText() {
        System.out.print("Digite o texto para codificar: ");
        String textToEncode = scanner.nextLine();
        try {
            String encoded = translator.encode(textToEncode);
            System.out.println("Código Horus: " + encoded);
            handleResultOptions(encoded);
        } catch (HorusCodeTranslator.HorusCodeException e) {
            System.out.println("Erro ao codificar: " + e.getMessage());
            LOGGER.log(Level.SEVERE, "Erro de codificação", e);
        }
    }
    
    /**
     * Processa a opção de decodificar Código Horus para texto.
     */
    private void handleDecodeHorusCode() {
        System.out.print("Digite o Código Horus para decodificar (use '/' entre letras/números e '//' entre palavras): ");
        String codeToDecode = scanner.nextLine();
        try {
            String decoded = translator.decode(codeToDecode);
            System.out.println("Texto: " + decoded);
            handleResultOptions(decoded);
        } catch (HorusCodeTranslator.HorusCodeException e) {
            System.out.println("Erro ao decodificar: " + e.getMessage());
            LOGGER.log(Level.SEVERE, "Erro de decodificação", e);
        }
    }
    
    /**
     * Processa a opção de carregar texto de um arquivo e opcionalmente codificá-lo.
     */
    private void handleLoadTextFile() {
        System.out.print("Digite o caminho do arquivo com texto para carregar: ");
        String textFilePath = scanner.nextLine();
        try {
            String loadedText = HorusUtils.loadFromFile(textFilePath);
            System.out.println("Texto carregado: " + loadedText);
            System.out.print("\nDeseja codificar este texto? (S/N): ");
            String encodeChoice = scanner.nextLine();
            if (encodeChoice.equalsIgnoreCase("S")) {
                String encoded = translator.encode(loadedText);
                System.out.println("Código Horus: " + encoded);
                handleResultOptions(encoded);
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar arquivo: " + e.getMessage());
            LOGGER.log(Level.SEVERE, "Erro ao carregar arquivo", e);
        } catch (HorusCodeTranslator.HorusCodeException e) {
            System.out.println("Erro ao codificar texto do arquivo: " + e.getMessage());
            LOGGER.log(Level.SEVERE, "Erro de codificação de arquivo", e);
        }
    }
    
    /**
     * Processa a opção de carregar código Horus de um arquivo e opcionalmente decodificá-lo.
     */
    private void handleLoadHorusCodeFile() {
        System.out.print("Digite o caminho do arquivo com código Horus para carregar: ");
        String codeFilePath = scanner.nextLine();
        try {
            String loadedCode = HorusUtils.loadFromFile(codeFilePath);
            System.out.println("Código Horus carregado: " + loadedCode);
            System.out.print("\nDeseja decodificar este código? (S/N): ");
            String decodeChoice = scanner.nextLine();
            if (decodeChoice.equalsIgnoreCase("S")) {
                String decoded = translator.decode(loadedCode);
                System.out.println("Texto: " + decoded);
                handleResultOptions(decoded);
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar arquivo: " + e.getMessage());
            LOGGER.log(Level.SEVERE, "Erro ao carregar arquivo", e);
        } catch (HorusCodeTranslator.HorusCodeException e) {
            System.out.println("Erro ao decodificar código do arquivo: " + e.getMessage());
            LOGGER.log(Level.SEVERE, "Erro de decodificação de arquivo", e);
        }
    }
    
    /**
     * Exibe o menu de operações relacionadas ao resultado e processa a escolha do usuário.
     * 
     * @param result O resultado (texto ou código Horus) a ser manipulado
     */
    private void handleResultOptions(String result) {
        if (result == null || result.isEmpty()) {
            System.out.println("Não há resultado para manipular.");
            return;
        }

        while (true) {
            System.out.println("\nO que você deseja fazer com o resultado?");
            System.out.println("1. Copiar para a área de transferência");
            System.out.println("2. Salvar em arquivo");
            System.out.println("3. Voltar ao menu principal");
            System.out.print("Opção: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    try {
                        HorusUtils.copyToClipboard(result);
                        System.out.println("Resultado copiado para a área de transferência!");
                    } catch (Exception e) {
                        System.out.println("Erro ao copiar para a área de transferência: " + e.getMessage());
                        LOGGER.log(Level.SEVERE, "Erro ao copiar para clipboard", e);
                    }
                    break;
                case "2":
                    System.out.print("Digite o caminho do arquivo para salvar: ");
                    String savePath = scanner.nextLine();
                    try {
                        HorusUtils.saveToFile(result, savePath);
                        System.out.println("Resultado salvo com sucesso em: " + savePath);
                    } catch (IOException e) {
                        System.out.println("Erro ao salvar arquivo: " + e.getMessage());
                        LOGGER.log(Level.SEVERE, "Erro ao salvar arquivo", e);
                    }
                    break;
                case "3":
                    return; // Volta ao menu principal
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    /**
     * Método principal que inicia a aplicação.
     * 
     * @param args Argumentos de linha de comando (não utilizados)
     */
    public static void main(String[] args) {
        HorusUI ui = new HorusUI();
        ui.showMainMenu();
    }
}