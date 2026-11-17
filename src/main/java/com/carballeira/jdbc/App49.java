package com.carballeira.jdbc;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class App49 {
	public static void main(String[] args) {

		Gson gson = new Gson();

		try (FileReader lector = new FileReader("alumnos.json")) {

			ArrayList<Alumno2> alumnos = gson.fromJson(lector, new TypeToken<ArrayList<Alumno2>>() {
			}.getType());

			System.out.println("Fichero JSON abierto correctamente.");
			System.out.println("Número de alumnos recuperados: " + alumnos.size());
			
//			System.out.println("\nLISTADO DE ALUMNOS: ");
//			
//			for(Alumno2 a : alumnos) {
//    		    System.out.println(
//		        "DNI: " + a.getDni() +
//		        " | Nombre: " + a.getNombre() +
//		        " | Nota final: " + a.getNotaFinal()
//		    );
//		}
//		// Mostrar únicamente los alumnos aprobados

			System.out.println("\nALUMNOS APROBADOS:");

			for (Alumno2 a : alumnos) {

				if (a.getNotaFinal() >= 9) {

					System.out.println(a.getNombre() + " - Nota final: " + a.getNotaFinal());
				}
			}
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}

	}
}
