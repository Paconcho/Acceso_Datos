package com.carballeira.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class App44 {
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		System.out.println("¿Cuántos alumnos quieres introducir?: ");
			int cantidad = Integer.parseInt(sc.nextLine());
		
		String url = "jdbc:mariadb://10.10.32.6:3306/academia";
		String usuario = "root";
		String password = "abc123.";
		
		String sql = """
				INSERT INTO alumnos (nombre, apellidos, nota)
				VALUES (?, ?, ?)
				""";
		
//		try(Connection con = DriverManager.getConnection(url, usuario, password);
//				PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
//			
//			ps.setString(1, "Carlos");
//			ps.setString(2, "Martinez López");
//			ps.setDouble(3, 8.5);
//			
//			int filas = ps.executeUpdate();
//			
//			System.out.println("Filas insertadas: " + filas);
//			
//			try(ResultSet rs = ps.getGeneratedKeys()){
//				if(rs.next()) {
//					int idGenerado = rs.getInt(1);
//					System.out.println("ID generado:" + idGenerado);
//				}
//			}
//		
//		}catch(SQLException e) {
//			System.out.println("Error: " + e.getMessage());
//		}
		
		
		try(Connection con = DriverManager.getConnection(url, usuario, password);
				PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
			
			int totalInsertados = 0;
			for(int i = 0; i <= cantidad; i++) {
				System.out.println("\nAlumno " + i);
				System.out.print("Nombre: ");
					String nombre = sc.nextLine();
				System.out.print("Apellidos: ");
					String apellidos = sc.nextLine();
				System.out.print("Nota: ");
					double nota = Double.parseDouble(sc.nextLine().replace(',', '.'));
				ps.setString(1, nombre);
				ps.setString(2, apellidos);
				ps.setDouble(3, nota);
				int filas = ps.executeUpdate();
				totalInsertados += filas;
				
				try(ResultSet rs = ps.getGeneratedKeys()){
					if(rs.next()) {
						int id = rs.getInt(1);
						System.out.println("ID generado: " + id);
					}
				}
			}
			System.out.println("Total alumnos insertados: " + totalInsertados);	
		}catch(SQLException e) {
			System.out.println("Error: " + e.getMessage());
		}	
		sc.close();
	}
}
