package src;

import java.util.HashMap;
import java.util.Map;
import java.text.Normalizer;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * HorusCodeTranslator - Classe responsável por codificar e decodificar texto usando o Código Horus.
 * <p>
 * O Código Horus é uma linguagem de codificação similar ao Código Morse, mas que utiliza
 * sequências de 0s e 1s para representar letras, números e sinais de pontuação.
 * </p>
 * <p>
 * Características principais:
 * <ul>
 *   <li>Utiliza apenas os símbolos '0' e '1' para codificação</li>
 *   <li>Usa '/' como separador entre caracteres e '//' como separador entre palavras</li>
 *   <li>Suporta letras, números e símbolos de pontuação básicos</li>
 *   <li>Normaliza caracteres acentuados para sua forma básica (ex: 'á' -> 'A')</li>
 * </ul>
 * </p>
 * 
 * @author Bypass
 * @version 1.0
 */
public class HorusCodeTranslator {

    /** Logger para registrar mensagens e erros */
    private static final Logger LOGGER = Logger.getLogger(HorusCodeTranslator.class.getName());
    
    /** Mapa para tradução de texto para código Horus */
    private static final Map<Character, String> textToHorusMap = new HashMap<>();
    
    /** Mapa para tradução de código Horus para texto */
    private static final Map<String, Character> horusToTextMap = new HashMap<>();
    
    /** Separador utilizado entre caracteres */
    private static final String LETTER_SEPARATOR = "/";
    
    /** Separador utilizado entre palavras */
    private static final String WORD_SEPARATOR = "//"; // Usando '//' para separar palavras

    // Bloco estático para inicializar os mapas de tradução
    static {
        // Letras
        textToHorusMap.put('A', "01");
        textToHorusMap.put('B', "1000");
        textToHorusMap.put('C', "1010");
        textToHorusMap.put('D', "100");
        textToHorusMap.put('E', "0");
        textToHorusMap.put('F', "0010");
        textToHorusMap.put('G', "110");
        textToHorusMap.put('H', "0000");
        textToHorusMap.put('I', "00");
        textToHorusMap.put('J', "0111");
        textToHorusMap.put('K', "101");
        textToHorusMap.put('L', "0100");
        textToHorusMap.put('M', "11");
        textToHorusMap.put('N', "10");
        textToHorusMap.put('O', "111");
        textToHorusMap.put('P', "0110");
        textToHorusMap.put('Q', "1101");
        textToHorusMap.put('R', "010");
        textToHorusMap.put('S', "000");
        textToHorusMap.put('T', "1");
        textToHorusMap.put('U', "001");
        textToHorusMap.put('V', "0001");
        textToHorusMap.put('W', "011");
        textToHorusMap.put('X', "1001");
        textToHorusMap.put('Y', "1011");
        textToHorusMap.put('Z', "1100");

        // Números
        textToHorusMap.put('0', "11111");
        textToHorusMap.put('1', "01111");
        textToHorusMap.put('2', "00111");
        textToHorusMap.put('3', "00011");
        textToHorusMap.put('4', "00001");
        textToHorusMap.put('5', "00000");
        textToHorusMap.put('6', "10000");
        textToHorusMap.put('7', "11000");
        textToHorusMap.put('8', "11100");
        textToHorusMap.put('9', "11110");

        // Pontuação básica (pode ser expandida)
        textToHorusMap.put('.', "010101");
        textToHorusMap.put(',', "110011");
        textToHorusMap.put('?', "001100");
        textToHorusMap.put('!', "101011");
        textToHorusMap.put('(', "10110");
        textToHorusMap.put(')', "101101");
        textToHorusMap.put('"', "010010");
        textToHorusMap.put('\'', "011110");
        textToHorusMap.put('-', "100001");
        textToHorusMap.put('_', "001101");
        textToHorusMap.put(' ', WORD_SEPARATOR); // Espaço mapeia diretamente para o separador de palavras

        // Preenche o mapa reverso para decodificação
        for (Map.Entry<Character, String> entry : textToHorusMap.entrySet()) {
            // Não adiciona o espaço ao mapa reverso, pois ele é tratado pela separação
            if (entry.getKey() != ' ') {
                horusToTextMap.put(entry.getValue(), entry.getKey());
            }
        }
    }

    /**
     * Exceção personalizada para erros de codificação/decodificação de Código Horus.
     */
    public static class HorusCodeException extends Exception {
        public HorusCodeException(String message) {
            super(message);
        }
        
        public HorusCodeException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    /**
     * Valida se uma string contém apenas caracteres de Código Horus válidos (0, 1 e separadores).
     * 
     * @param horusCode O código a ser validado
     * @throws HorusCodeException Se o código contém caracteres inválidos
     */
    public void validateHorusCode(String horusCode) throws HorusCodeException {
        if (horusCode == null) {
            throw new HorusCodeException("O código Horus não pode ser nulo");
        }
        
        if (!horusCode.matches("^[01/]*$")) {
            throw new HorusCodeException("O código Horus contém caracteres inválidos. Apenas '0', '1' e '/' são permitidos");
        }
    }

    /**
     * Codifica um texto em linguagem natural para o Código Horus.
     * <p>
     * O método realiza as seguintes operações:
     * <ol>
     *   <li>Converte o texto para maiúsculas</li>
     *   <li>Normaliza caracteres acentuados (removendo os acentos)</li>
     *   <li>Substitui caracteres não ASCII como 'Ç' por suas versões ASCII ('C')</li>
     *   <li>Codifica cada caractere usando o mapa de codificação</li>
     *   <li>Separa caracteres e palavras com os separadores apropriados</li>
     * </ol>
     * </p>
     * 
     * @param text O texto a ser codificado
     * @return Uma string contendo o texto codificado em Código Horus
     * @throws HorusCodeException Se o texto for nulo ou se ocorrer um erro durante a codificação
     */
    public String encode(String text) throws HorusCodeException {
        if (text == null) {
            throw new HorusCodeException("O texto de entrada não pode ser nulo");
        }
        
        if (text.isEmpty()) {
            return "";
        }

        try {
            // Normalização usando Normalizer
            String normalizedText = text.toUpperCase(); // Primeiro para maiúscula
            normalizedText = Normalizer.normalize(normalizedText, Normalizer.Form.NFD); // Decompõe acentos
            normalizedText = normalizedText.replaceAll("\\p{M}", ""); // Remove diacríticos (acentos)
            normalizedText = normalizedText.replaceAll("Ç", "C"); // Garante o Ç -> C

            StringBuilder horusCode = new StringBuilder();
            String[] words = normalizedText.split("\\s+"); // Separa por um ou mais espaços

            boolean firstWordEncoded = false; // Flag para controlar o WORD_SEPARATOR
            StringBuilder unmappedChars = new StringBuilder(); // Registra caracteres não mapeados

            for (String word : words) {
                StringBuilder wordHorusCode = new StringBuilder(); // Código para a palavra atual
                int validCharsInWord = 0; // Contador de caracteres válidos na palavra

                for (int j = 0; j < word.length(); j++) {
                    char character = word.charAt(j);
                    String code = textToHorusMap.get(character);
                    if (code != null) {
                        if (validCharsInWord > 0) { // Adiciona separador ANTES da letra (exceto a primeira)
                            wordHorusCode.append(LETTER_SEPARATOR);
                        }
                        wordHorusCode.append(code);
                        validCharsInWord++;
                    } else {
                        // Registra o caractere não mapeado
                        unmappedChars.append(character);
                    }
                }

                if (wordHorusCode.length() > 0) { // Se a palavra resultou em algum código Horus
                     if (firstWordEncoded) { // Adiciona separador ANTES da palavra atual (exceto a primeira)
                         horusCode.append(WORD_SEPARATOR);
                     }
                     horusCode.append(wordHorusCode);
                     firstWordEncoded = true; // Marca que já codificamos a primeira palavra válida
                }
            }
            
            // Se encontrou caracteres não mapeados, loga um aviso mas continua
            if (unmappedChars.length() > 0) {
                LOGGER.warning("Caracteres não mapeados ignorados durante a codificação: " + unmappedChars.toString());
            }

            return horusCode.toString();
        } catch (Exception e) {
            throw new HorusCodeException("Erro ao codificar texto para Código Horus", e);
        }
    }

    /**
     * Decodifica uma string em Código Horus para texto em linguagem natural.
     * <p>
     * O método realiza as seguintes operações:
     * <ol>
     *   <li>Separa o código em palavras usando o separador '//'</li>
     *   <li>Separa cada palavra em caracteres usando o separador '/'</li>
     *   <li>Decodifica cada sequência de caracteres usando o mapa de decodificação</li>
     *   <li>Reconstrói o texto original juntando os caracteres e palavras decodificados</li>
     * </ol>
     * </p>
     * 
     * @param horusCode O código Horus a ser decodificado
     * @return Uma string contendo o texto decodificado
     * @throws HorusCodeException Se o código for nulo, inválido ou se ocorrer um erro durante a decodificação
     */
    public String decode(String horusCode) throws HorusCodeException {
        if (horusCode == null) {
            throw new HorusCodeException("O código Horus de entrada não pode ser nulo");
        }
        
        if (horusCode.isEmpty()) {
            return "";
        }
        
        // Valida se o código contém apenas caracteres válidos (0, 1 e /)
        validateHorusCode(horusCode);

        try {
            StringBuilder text = new StringBuilder();
            // Separa por palavras usando WORD_SEPARATOR
            String[] words = horusCode.split(WORD_SEPARATOR); // Pode gerar strings vazias
            
            boolean firstValidWordDecoded = false; // Flag para controlar o espaço entre palavras
            StringBuilder unmappedCodes = new StringBuilder(); // Registra códigos não mapeados

            for (String word : words) {
                if (word.isEmpty()) continue; // Ignora strings vazias (podem surgir por causa do split)
                
                StringBuilder wordText = new StringBuilder(); // Texto para a palavra atual
                // Separa os caracteres da palavra atual usando LETTER_SEPARATOR
                String[] horusChars = word.split(LETTER_SEPARATOR); // Pode gerar strings vazias
                
                for (String horusChar : horusChars) {
                    if (horusChar.isEmpty()) continue; // Ignora strings vazias
                    
                    Character decodedChar = horusToTextMap.get(horusChar);
                    if (decodedChar != null) {
                        wordText.append(decodedChar);
                    } else {
                        // Registra o código não mapeado
                        unmappedCodes.append(horusChar).append(' ');
                    }
                }
                
                if (wordText.length() > 0) { // Se pelo menos um caractere foi decodificado
                    if (firstValidWordDecoded) { // Adiciona espaço ANTES da palavra (exceto a primeira)
                        text.append(' ');
                    }
                    text.append(wordText);
                    firstValidWordDecoded = true; // Marca que já decodificamos a primeira palavra válida
                }
            }
            
            // Se encontrou códigos não mapeados, loga um aviso mas continua
            if (unmappedCodes.length() > 0) {
                LOGGER.warning("Códigos Horus não mapeados ignorados durante a decodificação: " + unmappedCodes.toString());
            }
            
            return text.toString();
        } catch (Exception e) {
            throw new HorusCodeException("Erro ao decodificar Código Horus para texto", e);
        }
    }
    
    /**
     * Retorna o separador de letras utilizado no Código Horus.
     * 
     * @return O separador de letras
     */
    public String getLetterSeparator() {
        return LETTER_SEPARATOR;
    }
    
    /**
     * Retorna o separador de palavras utilizado no Código Horus.
     * 
     * @return O separador de palavras
     */
    public String getWordSeparator() {
        return WORD_SEPARATOR;
    }
}