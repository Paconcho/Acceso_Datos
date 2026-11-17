package com.carballeira.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class App51 {
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		int opcion;
		
		do {
			System.out.println("\n===== GESTION DE ALUMNOS =====");
			System.out.println("1. Mostrar alumnos");
			System.out.println("2. Buscar alumno por DNI");
			System.out.println("3. Insertar alumno");
			System.out.println("4. Modificar alumno");
			System.out.println("5. Eliminar alumno");
			System.out.println("0. Salir");
			System.out.println("Elige opcion: ");
			
			if(sc.hasNextInt()) {
				opcion = sc.nextInt();
				sc.nextLine();
			}else {
				System.out.println("Introduce un numero valido: ");
				sc.next();
				opcion = -1;
			}
			
			switch (opcion) {
			case 1 -> {
				System.out.println("Mostrar alumnos");
				mostrarAlumnos();
			}
			case 2 -> System.out.println("Buscar alumno");
			case 3 -> System.out.println("Insertar alumno");
			case 4 -> System.out.println("Modificar alumno");
			case 5 -> System.out.println("Eliminar alumno");
			case 0 -> System.out.println("Saliendo...");
			default -> System.out.println("Opcion incorrecta");
			}
		
		}while(opcion != 0);
		
		sc.close();
	
	}
	
	public static void mostrarAlumnos() {
		String sql = "SELECT * FROM alumnos";
		
		try(Connection conexion = DriverManager.getConnection("jdbc:mariadb://10.10.32.6:3306/academia", "root", "abc123.");
				PreparedStatement ps =conexion.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()){
			
			System.out.println(
					rs.getInt("id") + " - " +
					rs.getString("nombre") + " " + 
					rs.getString("apellidos") + " - Nota: " + 
					rs.getDouble("nota")
					);
			
		}catch(SQLException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
}
