package dev.sergi.jte.classes;

public class Cliente {
    public int id;
    public String nombre;
    public String direccion;


    public Cliente() {}

    public Cliente(String nombre) {
        this.nombre = nombre;
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


    public String getDireccion() {
        return direccion;
    }


    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    
}
