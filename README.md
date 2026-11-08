### Universidad del Valle de Guatemala

### Programación Orientada a Objetos

### Mario Alejandro Pellecer Ramírez - 26282

# Ejercicio No. 6: Polimorfismo vía Interfaces

# Creación de Dashboard con Widgets


Widgets a utilizar
- Lista de tareas pendientes (Interactuable, Renderable)
Se puede dar completar para que se elimine la primera de la lista o agregar + el encabezado de la nueva tarea para insertarla en la lista
- Visor del clima de Ciudad de Guatemala ( Renderable, Actualizable) 
Cambia cada vez que uno le da en actualizar clima o refrescar actualizables

- Reloj digital (Actualizable, Renderable)
Funciona de la mano de la hora exacta con actualización en tiempo real sin necesidad de ninguna acción externa

- Cronómetro (Interactuable, Renderable, Actualizable)
Funciona completamente como cronómetro pudiendo iniciar, pausar o reiniciar

- Post-it para notas rápidas (Interactuable, Renderable)
Puede almacenar un mensaje a la vez

- Notificaciones de alerta (Renderable)
Estática y solo muestra un mensaje predefinido

- Contador de vasos de Agua (Interactuable, Renderable)
Tiene un límite o meta preestablecido y se puede incrementar uno a la vez o reiniciar

- Reproductor de música (Interactuable,Renderable)
Puede pausar, reproducir o cambiar a siguiente canción, simulando solamente con una lista predefinida de títulos de canción

- Monitor de batería (Interactuable,Renderable)    
Baja 1% cada vez que uno le da refrescar actualizables

- Frase del día (Renderizable)
Estática y solamente una que se dibuja


Interfaces
- Renderable (todos)
- Interactuable (widgets que reaccionan a acciones directas del usuario)
- Actualizable (actualización en tiempo real)

Botones
- Busqueda y botón buscar (como enter): se puede buscar los widgets por id o nombre (con equalIgnoreCase para evitar errores inesperados si se escribe mayúsculas o minúsculas)
- Ordenar por Memoria...: aplica el Compare To y ordena los widgets por memoria consumida de forma ascendente
- Limpiar: elimina los filtros de widgets activos y vuelve a mostrar todos y también limpia cualquier búsqueda realizada con anterioridad
- Interactuar: abre un cuadro de diálogo con opciones de texto o comandos preestablecidos a usar por cada widget; no se rompe el programa si se meten datos incorrectos pero tampoco muestra mensaje de error. Simplemente no cambia nada.
- Actualizar clima, botón específico para que el clima se actualice por botón de forma inmediata
- Refrescar actualizables: sirve para refrescar los widgets que deben actualizarse cada cierto tiempo como el clima
- Mostrar: muestra lista para filtrar solo widgets activos o mostrar la lisat completa de 10 precargados
