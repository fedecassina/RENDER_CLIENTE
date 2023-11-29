package domain.models.entities.validadorDeContrasenia;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.io.IOException;

public class ValidadorDeContrasenia {

    // final: no se puede cambiar el valor de la variable despues de la inicializacion
    private static final int LONG_MINIMA = 8; // Longitud mínima de 8 caracteres
    private static final int LONG_MAXIMA = 64; // Longitud máxima de 64 caracteres.
    private static final String PATRON_LETRAS_MAYUSCULAS = ".*[A-Z].*"; // Al menos una letra mayúscula.
    private static final String PATRON_LETRAS_MINUSCULAS = ".*[a-z].*"; // Al menos una letra minúscula.
    private static final String PATRON_NUMEROS = ".*[0-9].*"; // Al menos un número.
    private static final String PATRON_CARACTERES_ESPECIALES = ".*[@#$%^&+=].*"; // Al menos un carácter especial.

    private static final String TOP_10000_PEORES_CONTRASENAS = "src\\main\\java\\domain\\validadorDeContrasenia\\Top-10000-Worst-Passwords.txt";
    private static final List<String> PEORES_CONTRASENAS; // Lista de las 10.000 peores contraseñas.

    static {
        try {
            PEORES_CONTRASENAS = Files.readAllLines(Paths.get(TOP_10000_PEORES_CONTRASENAS));
        } catch (IOException e) {
            throw new RuntimeException("No se pudo cargar la lista de las peores contraseñas.", e);
        }
    }
    public static boolean validarContrasenia(String contrasenia) {
        if (contrasenia.length() < LONG_MINIMA || contrasenia.length() > LONG_MAXIMA) {
            return false;
        }
        if (!contrasenia.matches(PATRON_LETRAS_MAYUSCULAS)) {
            return false;
        }
        if (!contrasenia.matches(PATRON_LETRAS_MINUSCULAS)) {
            return false;
        }
        if (!contrasenia.matches(PATRON_NUMEROS)) {
            return false;
        }
        if (!contrasenia.matches(PATRON_CARACTERES_ESPECIALES)) {
            return false;
        }
        if (PEORES_CONTRASENAS.contains(contrasenia)) {
            return false;
        }
        return true;
    }

}


