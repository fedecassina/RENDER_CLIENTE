package domain.models.entities.mains;

import domain.models.entities.usuario.Usuario;
import domain.models.entities.validadorDeContrasenia.ValidadorDeContrasenia;

import java.util.Scanner;
public class Main_Validador_Contrasenia {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el nombre de usuario: ");
        String username = scanner.nextLine();
        System.out.print("Ingrese la contraseña: ");
        String password = scanner.nextLine();

        if (ValidadorDeContrasenia.validarContrasenia(password)) {
            Usuario usuario = new Usuario(username, password);
            System.out.println("Usuario registrado exitosamente.");
        } else {
            System.out.println("La contraseña no cumple con los requisitos de seguridad.");
        }
    }
}
