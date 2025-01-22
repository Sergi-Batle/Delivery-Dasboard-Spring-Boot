package dev.danvega.jte.classes;

public class ItemFactura {
    private int id;
    private String nombre;
    private int unidades;

    public ItemFactura(int id, String nombre, int unidades) {
        this.id = id;
        this.nombre = nombre;
        this.unidades = unidades;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setUnidades(int unidades) {
        this.unidades = unidades;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getUnidades() {
        return unidades;
    }

    
}
