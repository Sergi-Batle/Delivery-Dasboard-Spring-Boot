package dev.danvega.jte.classes;

public class Repartidor {
    public int id;
    public String nombre;
    public String telefono;
    public String color;

    public Repartidor() {}

    public Repartidor(int id, String nombre, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
    }

    public Repartidor(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Repartidor(String nombre, String color) {
        this.nombre = nombre;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public String getColor() {
        return color;
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
}
