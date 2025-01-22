@import dev.danvega.jte.classes.Envio
@import java.util.List

@param String today
@param List<Envio> envios

@template.layout.main(content = @`
    <div class="bg-white shadow rounded-lg p-6 border border-amber-600">
        <h1 class="text-2xl font-bold mb-4">${today}</h1>

        <div id="map" class="border border-amber-600"></div>

        <table id="tablaEnvios" class="w-full">
            <thead>
                <tr>
                    <th>Dirección</th>
                    <th>Cliente</th>
                    <th>Repartidor</th>
                    <th>Estado</th>
                </tr>
            </thead>
            <tbody>
                @for(Envio envio : envios)
                    <tr>
                        <td class="text-center">${envio.cliente.nombre}</td>
                        <td class="text-center">${envio.direccion}</td>
                        <td class="text-center">${envio.repartidor.nombre}</td>
                        <td class="text-center">${envio.estado}</td>
                    </tr>
                        
                @endfor
            </tbody>
        </table>

        <script>
            var markers = []
            var palmaLat = 39.5696;
            var palmaLon = 2.6502;

            var map = L.map('map').setView([palmaLat, palmaLon], 13);
            L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
            attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
            }).addTo(map);

            console.log(envios[0])

            @for(Envio envio : envios)

                marker = new khtml.maplib.overlay.Marker({
                position: new khtml.maplib.LatLng(0, 0),
                icon: {
                    url: "http://maps.gstatic.com/intl/de_de/mapfiles/ms/micons/red-pushpin.png",
                    size: {width: 26, height: 32},
                    origin: {x: 0, y: 0},
                    anchor: {
                        x: "-10px",
                        y: "-32px"
                    }
                },
                shadow: {
                    url: "http://maps.gstatic.com/intl/de_de/mapfiles/ms/micons/pushpin_shadow.png",
                    size: {
                        width: "40px",
                        height: "32px"
                    },
                    origin: {x: 0, y: 0},
                    anchor: {x: 0, y: -32 }
                },
                draggable: true,
                title: "moveable marker"
                });

                var infobox = new khtml.maplib.overlay.InfoWindow({content: "This is a marker-infobox!"});

                marker.attachEvent( 'click', function() {
                        infobox.open(map, this);
                });

                markers.push(marker);


                
            @endfor


        </script>
    </div>
`)