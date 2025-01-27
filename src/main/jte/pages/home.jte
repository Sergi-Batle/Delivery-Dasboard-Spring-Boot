@import dev.sergi.jte.classes.Envio
@import java.util.List

@param String today
@param String api_key
@param List<Envio> envios

@template.layout.main(content = @`
    <div class="bg-white shadow rounded-lg p-6 border border-amber-600">
        <h1 class="text-2xl font-bold mb-4">${today}</h1>

        <div id="map" class="border border-amber-600"></div>

        <table id="tablaEnvios" class="rounded-sm p-1 mt-3 w-full bg-gradient-to-r from-orange-500 via-Amber-500 to-yellow-200">
            <thead>
                <tr>
                    <th class="p-3">Dirección</th>
                    <th class="p-3">Cliente</th>
                    <th class="p-3">Repartidor</th>
                    <th class="p-3">Estado</th>
                </tr>
            </thead>
            <tbody>
                @for(Envio envio : envios)
                    <tr class="bg-gray-900/20">
                        <td class="text-center p-2">${envio.direccion}</td>
                        <td class="text-center p-2">${envio.cliente.nombre}</td>
                        <td class="text-center p-2 bg-gray-800/20">${envio.repartidor.nombre}<span id="color" class="bg-${envio.repartidor.color}-500"></span></td>
                        <td class="text-center p-2">${envio.estado}</td>
                    </tr>
                @endfor
            </tbody>
        </table>

        <script src="https://unpkg.com/leaflet@1.7.1/dist/leaflet.js"></script>
        <script>
            var markers = [];
            var palmaLat = 39.5696;
            var palmaLon = 2.6502;

            var map = L.map('map').setView([palmaLat, palmaLon], 13);
            L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
                attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
            }).addTo(map);

            function setMarker(latidud, longitud, color, nombre_cliente, envio_direccion) {
                const markerIcon = L.icon({
                    iconUrl: 'https://api.geoapify.com/v1/icon/?type=awesome&color=' + color + '&size=small&iconType=material&iconSize=small&textSize=small&noWhiteCircle&scaleFactor=2&apiKey=${api_key}',
                    iconSize: [31, 46], // size of the icon
                    iconAnchor: [15.5, 42], // point of the icon which will correspond to marker's location
                    popupAnchor: [0, -45] // point from which the popup should open relative to the iconAnchor
                });
                const zooMarkerPopup = L.popup().setContent(nombre_cliente + "<br>" + envio_direccion);
                const zooMarker = L.marker([latidud, longitud], {
                icon: markerIcon
                }).bindPopup(zooMarkerPopup).addTo(map);
            }
            
            @for(Envio envio : envios)
                setMarker(${envio.ubicacion.latitud}, ${envio.ubicacion.longitud}, "${envio.repartidor.color}", "${envio.cliente.nombre}", "${envio.direccion}");
            @endfor

            
        </script>
    </div>
`)