
package modelo;

public class Libro {
    
    private long isbn;
    private String nombre;
    private int cantidad;
    private String autor;
    private float calificacion;
    private int n_editorial;

    public long getIsbn() {
        return isbn;
    }

    public void setIsbn(long isbn) {
        this.isbn = isbn;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public float getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(float calificacion) {
        this.calificacion = calificacion;
    }

    public int getN_editorial() {
        return n_editorial;
    }

    public void setN_editorial(int n_editorial) {
        this.n_editorial = n_editorial;
    }   
}
