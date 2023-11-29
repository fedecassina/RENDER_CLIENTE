$(document).ready(function () {
    Handlebars.registerHelper('formatDate', function(date) {
        const options = { day: 'numeric', month: 'numeric', year: 'numeric' };
        return new Date(date).toLocaleDateString(undefined, options);
    });
    // Maneja el clic en "Abiertos"
    $(".filtro a:contains('Abiertos')").click(function () {
        // Cambia el color del botón
        $(".filtro a").removeClass("activo").addClass("inactivo");
        $(this).addClass("activo");

        // Oculta todos los incidentes
        $(".texto-incidente").parent().hide();
        // Muestra solo los incidentes con estado "Abierto"
        $(".estado:contains('Abierto')").parent().parent().show();
    });

    // Maneja el clic en "Cerrados"
    $(".filtro a:contains('Cerrados')").click(function () {
        // Cambia el color del botón
        $(".filtro a").removeClass("activo").addClass("inactivo");
        $(this).addClass("activo");

        // Oculta todos los incidentes
        $(".texto-incidente").parent().hide();
        // Muestra solo los incidentes con estado "Cerrado"
        $(".estado:contains('Cerrado')").parent().parent().show();
    });

    // Maneja el clic en "Todos"
    $(".filtro a:contains('Todos')").click(function () {
        // Cambia el color del botón
        $(".filtro a").removeClass("activo").addClass("inactivo");
        $(this).addClass("activo");

        // Muestra todos los incidentes
        $(".texto-incidente").parent().show();
    });
});

document.addEventListener("DOMContentLoaded", function () {
    const boton = document.querySelector(".sombra-boton");

    boton.addEventListener("click", function () {
        boton.classList.add("sombra-clic");

        setTimeout(function () {
            boton.classList.remove("sombra-clic");
        }, 150); // Tiempo de transicion
    });
});