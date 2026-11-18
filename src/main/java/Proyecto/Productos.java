package Proyecto;

public class Productos {
	
	public int id;
    public String nombre;
    public double precio;
    public int stock;
    public String categoria_id;

    public Productos() {
    }

    public Productos(int id, String nombre, double precio, int stock, String categoria_id) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.categoria_id = categoria_id;
    }
    
   
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getCategoria_id() {
        return categoria_id;
    }

    public void setCategoria_id(String categoria_id) {
        this.categoria_id = categoria_id;
    }

    @Override
    public String toString() {
        return "ID: " + id + " | Producto: " + nombre + " | Precio: " + precio + " € | Stock: " + stock + " | Categoría: " + categoria_id;
    }
}
