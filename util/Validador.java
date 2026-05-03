
package util;

public class Validador {
    
    // Solo números
    public static boolean esNumero(String input) {
        return input.matches("\\d+");
    }

    // Solo letras (incluye acentos y ñ)
    public static boolean esTexto(String input) {
        return input.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+");
    }

    //  Alfanumérico (letras y números, sin símbolos)
    public static boolean esAlfanumerico(String input) {
        return input.matches("[a-zA-Z0-9]+");
    }

    //  Número entero positivo
    public static boolean esEnteroPositivo(String input) {
        try {
            int num = Integer.parseInt(input);
            return num > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean contrasenaValida(String input) {
        // mínimo 6 caracteres, al menos una letra y un número
        return input.matches("(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}");
    }
    
}
