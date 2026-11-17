package Proyecto;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Menu {

	private static final String URL = "jdbc:mariadb://10.10.32.12:3306/Inventario";
	private static final String USUARIO = "root";
	private static final String PASSWORD = "abc123.";

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		int opcion;

		do {

			System.out.println("\n--- CONTROL DE INVENTARIO ---");
			System.out.println("1.- Listar productos(Inventario)");
			System.out.println("2.- Crear producto");
			System.out.println("3.- Modificar inventario");
			System.out.println("4.- Exportar productos a archivo de texto (.txt)");
			System.out.println("5.- Importar productos desde archivo de texto");
			System.out.println("6.- Exportar a JSON");
			System.out.println("7.- Guardar en binario");
			System.out.println("0.- Salir");

			if (sc.hasNextInt()) {
				opcion = sc.nextInt();
				sc.nextLine();
			} else {
				System.out.println("Introduce un numero valido: ");
				sc.next();
				opcion = -1;
			}

			switch (opcion) {
			case 1: {
				System.out.println("\n ->Listando los productos que hay en su inventario<-\n");
				listado();
				break;
			}
			case 2: {
				System.out.println("\n ->Se añadira un producto nuevo a su inventario<-\n");
				crearProducto(sc);
				break;
			}
			case 3: {
				System.out.println("\n ->Se modificara un producto de su inventario<-\n");
				modificar(sc);
				break;
			}
			case 4: {
				exportartxt();
				break;
			}
			case 5: {
				importardesdetxt();
				break;
			}
			case 6: {
				exportarJSON();
				break;
			}
			case 7: {
				guardarBinario();
				break;
			}
			case 0: {
				System.out.println("Saliendo");
			}
			}// switch

		} while (opcion != 0);

	}

	private static void guardarBinario() {

	}// guardarBinarioº

	private static void exportarJSON() {

	}// exportarJSON

	private static void importardesdetxt() {

	}// importardesdetxt

	private static void exportartxt() {

	}// exportartxt

	private static void modificar(Scanner sc) {
		String sql = """
				UPDATE productos SET nombre = ?, precio = ?, stock = ?, categoria_id = ? WHERE id = ?
				""";

		System.out.println("Introduce el id del producto que quiere modificar: ");
		int id = sc.nextInt();
		sc.nextLine();

		System.out.println("Introduce el nombre nuevo de la fruta que desea añadir: ");
		String nombre = sc.nextLine();
		System.out.println("Introduce el precio nuevo que tendrá la fruta: ");
		Double precio = sc.nextDouble();
		System.out.println("Introduce el numero de unidades que tiene de la fruta nueva(stock): ");
		int stock = sc.nextInt();
		System.out
				.println("Introduce la nueva categoria de la fruta (1: Tropicales, 2: Verano, 3: Frutos del bosque): ");
		int categoria = sc.nextInt();

		try (Connection con = DriverManager.getConnection(URL, USUARIO, PASSWORD);
				PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, nombre);
			ps.setDouble(2, precio);
			ps.setInt(3, stock);
			ps.setInt(4, categoria);
			ps.setInt(5, id);

			int modificaciones = ps.executeUpdate();

			if (modificaciones > 0) {
				System.out.println("Se ha realizado la modificacion correctamente");
			} else {
				System.out.println("No se pudo realizar la modificacion correctamente");
			}

		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		} // try-catch

	}// modificar

	private static void crearProducto(Scanner sc) {

		String sql = """
				INSERT INTO productos(nombre, precio, stock, categoria_id) VALUES (?, ?, ?, ?)
				""";

		System.out.println("Introduce el nombre de la fruta que desea añadir: ");
		String nombre = sc.nextLine();
		System.out.println("Introduce el precio que tendrá la fruta: ");
		Double precio = sc.nextDouble();
		System.out.println("Introduce el numero de unidades que tiene de la fruta(stock): ");
		int stock = sc.nextInt();
		System.out.println("Introduce la categoria de la fruta (1: Tropicales, 2: Verano, 3: Frutos del bosque): ");
		int categoria = sc.nextInt();

		try (Connection con = DriverManager.getConnection(URL, USUARIO, PASSWORD);
				PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, nombre);
			ps.setDouble(2, precio);
			ps.setInt(3, stock);
			ps.setInt(4, categoria);

			int afectadas = ps.executeUpdate();

			if (afectadas > 0) {
				System.out.println("Se añadió la fruta correctamente");
			}

		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		} // try-catch

	}// crearProducto

	private static void listado() {
		String sql = """
				SELECT p.id, p.nombre, p.precio, p.stock, c.nombre AS categoria
				      FROM productos p
				      JOIN categorias c ON p.categoria_id = c.id
				  """;

		try (Connection con = DriverManager.getConnection(URL, USUARIO, PASSWORD);
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				int id = rs.getInt("id");
				String nombre = rs.getString("nombre");
				double precio = rs.getDouble("precio");
				int stock = rs.getInt("stock");
				String categoria = rs.getString("categoria");

				System.out.printf("ID: %d | Producto: %s | Precio: %.2f € | Stock: %d | Categoría: %s%n", id, nombre,
						precio, stock, categoria);
			}

		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		} // try-catch

	}// listado
}
