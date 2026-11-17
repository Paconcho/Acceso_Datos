package com.carballeira.jdbc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class App46 {
	public static void main(String[] args) {

		String archivo = "alumnos.csv";

		String url = "jdbc:mariadb://10.10.32.6:3306/academia";
		String usuario = "root";
		String password = "abc123.";

		String sql = "INSERT INTO alumnos (nombre, apellidos, nota) " + "VALUES (?, ?, ?)";

		try (BufferedReader br = new BufferedReader(new FileReader(archivo));

				Connection con = DriverManager.getConnection(url, usuario, password);

				PreparedStatement ps = con.prepareStatement(sql)) {

			String linea;

			br.readLine();

			int contador = 0;
			int rechazados = 0;

			while ((linea = br.readLine()) != null) {

				String[] datos = linea.split(";", -1);


				if (datos.length != 3) {
					System.out.println("Línea incorrecta: " + linea);
					rechazados++;
					continue;
				}

				if (datos[0].isBlank() || datos[1].isBlank() || datos[2].isBlank()) {
					System.out.println("Faltan datos: " + linea);
					rechazados++;
					continue;
				}

				String nombre = datos[0].trim();
				String apellidos = datos[1].trim();

				double nota;

				try {
					nota = Double.parseDouble(datos[2].trim());
				} catch (NumberFormatException e) {
					System.out.println("Nota incorrecta: " + linea);
					rechazados++;
					continue;
				}

//	                String nombre = datos[0];
//	                String apellidos = datos[1];
//	                double nota = Double.parseDouble(datos[2]);
				//
				ps.setString(1, nombre);
				ps.setString(2, apellidos);
				ps.setDouble(3, nota);

////	                ps.executeUpdate();
				int filas = ps.executeUpdate();
				contador += filas;

				System.out.println("Alumno insertado: " + nombre + " " + apellidos);
			}
			System.out.println("-------------------------");
			System.out.println("Total alumnos importados: " + contador);

			System.out.println("Registros rechazados: " + rechazados);
			System.out.println("Total procesados: " + (contador + rechazados));

//	                System.out.println("Nombre: " + nombre);
//	                System.out.println("Apellidos: " + apellidos);
//	                System.out.println("Nota: " + nota);
//	                System.out.println("-------------------");
//	            

		} catch(IOException | SQLException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
}
