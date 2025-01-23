@import dev.sergi.jte.classes.Repartidor
@param java.util.List<Repartidor> repartidores

@template.layout.main(content = @`
    <div class="bg-white shadow rounded-lg p-6">
        <h1 class="text-2xl font-bold mb-4">Lista de Repartidores</h1>
        <ul class="list-disc list-inside">
            @for(Repartidor repartidor : repartidores)
                <li class="text-gray-600">${repartidor.getNombre()}</li>
            @endfor
        </ul>
    </div>
`)