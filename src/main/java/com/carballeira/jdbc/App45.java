package com.carballeira.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class App45 {
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Nombre: ");
			String nombre = sc.nextLine();
		System.out.println("Apellidos: ");
			String apellidos = sc.nextLine();
		
		System.out.println("Nota: ");
			double nota = Double.parseDouble(sc.nextLine().replace(",", "."));
		System.out.print("¿En cuántas asignaturas quieres matricular al alumno? ");
			int cantidad = Integer.parseInt(sc.nextLine());
		
			String url = "jdbc:mariadb://10.10.32.6:3306/academia";
			String usuario = "root";
			String password = "abc123.";
			
			String sql = """
					INSERT INTO alumnos(nombre, apellidos, nota)
					VALUES (?, ?, ?)
					""";
			String sqlMatricula = """
					INSERT INTO matriculas (id_alumno, asignatura) 
					VALUES (?, ?)
					 """;
			
			try(Connection con = DriverManager.getConnection(url, usuario, password);
					PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
				ps.setString(1, nombre);
				ps.setString(2, apellidos);
				ps.setDouble(3, nota);
				
				int filas = ps.executeUpdate();
				
				if(filas > 0) {
					try(ResultSet rs = ps.getGeneratedKeys()){
						if(rs.next()) {
							int idAlumno = rs.getInt(1);
							System.out.println("Alumno insertado.");
							System.out.println("ID del alumno: " + idAlumno);
							try(PreparedStatement psMatricula = con.prepareStatement(sqlMatricula)){
								int totalMatriculas = 0;
								for(int i = 1; i <= cantidad; i++) {
									System.out.println("Asignatura " + i + ":");
										String asignatura = sc.nextLine();
									psMatricula.setInt(1, idAlumno);
									psMatricula.setString(2, asignatura);
									int filasMatriculas = psMatricula.executeUpdate();
									totalMatriculas += filasMatriculas;
								}
								System.out.println("Matrículas insertadas: " + totalMatriculas);
							}
						}
					}
				}
			}catch(SQLException e) {
				System.out.println("Error: " + e.getMessage()) ;
			}
			sc.close();
	}
}
