package src;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Classe de utilidades para o sistema Código Horus.
 * <p>
 * Fornece funções para operações de I/O, manipulação da área de transferência
 * e outros utilitários relacionados.
 * </p>
 * 
 * @author Bypass
 * @version 1.0
 */
public class HorusUtils {
    
    /** Logger para registrar mensagens e erros */
    private static final Logger LOGGER = Logger.getLogger(HorusUtils.class.getName());

    /**
     * Copia um texto para a área de transferência do sistema.
     * 
     * @param text O texto a ser copiado para a área de transferência
     */
    public static void copyToClipboard(String text) {
        StringSelection stringSelection = new StringSelection(text);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
        LOGGER.info("Texto copiado para a área de transferência");
    }

    /**
     * Salva um texto em um arquivo.
     * 
     * @param text O texto a ser salvo
     * @param filePath O caminho do arquivo onde o texto será salvo
     * @throws IOException Se ocorrer um erro ao salvar o arquivo
     */
    public static void saveToFile(String text, String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(text);
            LOGGER.info("Texto salvo com sucesso em: " + filePath);
        }
    }

    /**
     * Carrega um texto de um arquivo.
     * 
     * @param filePath O caminho do arquivo a ser lido
     * @return O conteúdo do arquivo como uma string
     * @throws IOException Se ocorrer um erro ao ler o arquivo
     */
    public static String loadFromFile(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            LOGGER.info("Texto carregado com sucesso de: " + filePath);
        }
        // Remove o último \n, se houver
        if (content.length() > 0 && content.charAt(content.length() - 1) == '\n') {
            content.deleteCharAt(content.length() - 1);
        }
        return content.toString();
    }
}