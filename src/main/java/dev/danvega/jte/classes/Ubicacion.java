package dev.danvega.jte.classes;


public class Ubicacion {
    private String direccion;
    private double latitud;
    private double longitud;

    

    public Ubicacion(double latitud, double longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public Ubicacion(String direccion, double latitud, double longitud) {
        this.direccion = direccion; 
        this.latitud = latitud;
        this.longitud = longitud;
    }



    public String getDireccion() {
        return direccion;
    }



    public double getLatitud() {
        return latitud;
    }



    public double getLongitud() {
        return longitud;
    }


   
}
