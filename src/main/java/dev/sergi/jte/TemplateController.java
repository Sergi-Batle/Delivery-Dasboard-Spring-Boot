package dev.sergi.jte;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import dev.sergi.jte.classes.Envio;
import dev.sergi.jte.classes.Ubicacion;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import org.json.JSONArray;
import org.json.JSONObject;

@Controller
public class TemplateController {
    private final ConnectionDB connectionDB;
    private static List<Envio> envios;
    private static List<Ubicacion> ubicaciones;
    private List<Objects> tableRows;
    private static List<Envio> envios_hoy;
    private final String api_key = "e716d43fefb84f54a82583177dbba332";

    @Autowired
    public TemplateController(ConnectionDB connectionDB) {
        this.connectionDB = connectionDB;
    }

    @GetMapping("/")
    public String home(Model model) {
        String fecha = getDate();
        model.addAttribute("today", fecha);
        model.addAttribute("api_key", api_key);

        // new Thread(() -> setHomepage(model)).start();
        setHomepage(model);
        return "pages/home";
    }

    public void setHomepage(Model model) {
        setEnvios();

        // for (Envio envio : envios) {
        //     String direccion = envio.getDireccion();
        //     // actualizarUbicaciones(direccion);
        //     System.out.println(direccion);
        // }
        // setUbicaciones(model);

        // for (Ubicacion ubicacion : ubicaciones) {
        //     System.out.println(ubicacion.getDireccion() + " " + ubicacion.getLatitud()  + " " + ubicacion.getLongitud());
        // }

        envios_hoy = connectionDB.getTabla();
        model.addAttribute("envios", envios_hoy);
        for (Envio envio : envios_hoy) {
            String direccion_envio = envio.getDireccion();
            String nombre_cliente = envio.getCliente().getNombre();
            String nombre_repartidor = envio.getRepartidor().getNombre();
            String color_repartidor = envio.getRepartidor().getColor();
            double latitud = envio.getUbicacion().getLatitud();
            double longitud = envio.getUbicacion().getLongitud();
            
            System.out.println(direccion_envio + " " + nombre_cliente + " " + nombre_repartidor + " " + color_repartidor + " " + latitud + " " + longitud);
        }
    }


    private void setUbicaciones(Model model) {
        ubicaciones = connectionDB.getUbicaciones();
        model.addAttribute("ubicaciones", ubicaciones);
    }

    private Boolean actualizarUbicaciones(String direccion) {
        boolean existe = connectionDB.existeCalle(direccion);

        if (existe) {
            System.out.println("La calle ya existe en la base de datos.");
            return true;
        } else {
            List<Double> coordenadas = getCalleCoordenada(direccion);
            if (coordenadas != null) {
                float latitud = coordenadas.get(0).floatValue();
                float longitud = coordenadas.get(1).floatValue();
                System.out.println("Nueva calle insertada");
                return connectionDB.insertCoordenadas(direccion, latitud, longitud);
            } else {
                return false;
            }
        }
    }

    public List<Double> getCalleCoordenada(String direccion) {
        // URL de la API con la clave API
        direccion = direccion.replace(" ", "%20").replace(",", "%2C").replace("ñ", "%C3%B1");
        String urlString = "https://api.geoapify.com/v1/geocode/search?text=" + direccion
                + "&apiKey=" + api_key;
        try {
            // Crear un objeto URL a partir de la cadena URL
            URI uri = new URI(urlString);
            URL url = uri.toURL();
            // Abrir una conexión HTTP a la URL
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // Verificar si la solicitud fue exitosa
            int responseCode = conn.getResponseCode();
            if (responseCode == (HttpURLConnection.HTTP_OK)) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                JSONObject jsonResponse = new JSONObject(response.toString());
                // Obtener la matriz de "features"
                JSONArray features = jsonResponse.getJSONArray("features");

                if (features.length() > 0) {
                    // Obtener el primer objeto de "features"
                    JSONObject feature = features.getJSONObject(0);
                    JSONObject properties = feature.getJSONObject("properties");

                    // Extraer "lat" y "lon"
                    double latitude = properties.getDouble("lat");
                    double longitude = properties.getDouble("lon");

                    // Imprimir las coordenadas
                    System.out.println("Latitud: " + latitude);
                    System.out.println("Longitud: " + longitude);

                    ArrayList<Double> coordenadas = new ArrayList<>();
                    coordenadas.add(latitude);
                    coordenadas.add(longitude);
                    return  coordenadas;
                } else {
                    System.out.println("No se encontraron resultados.");
                }

            } else {
                // Manejar error si la solicitud no fue exitosa
                System.out.println("Error al hacer la solicitud: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    private void setEnvios() {
        envios = connectionDB.getEnvios();
    }

    private String getDate() {
        LocalDate fechaActual = LocalDate.now();
        DayOfWeek diaDeLaSemana = fechaActual.getDayOfWeek();
        String nombreDia = diaDeLaSemana.getDisplayName(TextStyle.FULL, Locale.of("es", "ES"));
        nombreDia = nombreDia.substring(0, 1).toUpperCase() + nombreDia.substring(1);

        DateTimeFormatter formatoDia = DateTimeFormatter.ofPattern("dd");
        DateTimeFormatter formatoMes = DateTimeFormatter.ofPattern("MM");
        DateTimeFormatter formatoAnyo = DateTimeFormatter.ofPattern("yyyy");
        String dia = fechaActual.format(formatoDia);
        String mes = fechaActual.format(formatoMes);
        Integer mesInt = Integer.parseInt(mes);
        Month mesEnum = Month.of(mesInt);
        mes = mesEnum.getDisplayName(TextStyle.FULL, Locale.of("es", "ES"));
        mes = mes.substring(0, 1).toUpperCase() + mes.substring(1);
        String anyo = fechaActual.format(formatoAnyo);
        String fecha = nombreDia + ", " + dia + " de " + mes + " del " + anyo;

        return fecha;
    }
}
