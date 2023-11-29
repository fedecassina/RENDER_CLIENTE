document.addEventListener("DOMContentLoaded", function () {
  // Registra el helper 'toJson'
  Handlebars.registerHelper('toJson', function (context) {
    return JSON.stringify(context);
  });
  Handlebars.registerHelper('formatDate', function(date) {
    const options = { day: 'numeric', month: 'numeric', year: 'numeric' };
    return new Date(date).toLocaleDateString(undefined, options);
  });

  const boton = document.querySelector(".sombra-boton");
  const fechaInicioElement = document.getElementById("fecha-inicio");

  boton.addEventListener("click", function () {
    boton.classList.add("sombra-clic");

    setTimeout(function () {
      boton.classList.remove("sombra-clic");
    }, 150); // Tiempo de transición

    // Accede al objeto 'incidente' directamente en el script
    const incidente = JSON.parse(document.getElementById("incidente-model").textContent);

    // Agrega Moment.js para formatear fechas
    const incidenteFechaInicio = moment(incidente.fechaInicio).format('dddd, D [de] MMMM [de] YYYY');

    // Actualiza el contenido de la etiqueta con la fecha formateada
    fechaInicioElement.textContent = incidenteFechaInicio;
  });

  var button = document.getElementById("close-button");

  button.addEventListener("click", function () {
    cerrarIncidente();
  });

  // Nueva función para cerrar el incidente
  function cerrarIncidente() {
    // Puedes obtener el ID del incidente de alguna manera
    const incidenteId = obtenerIdDelIncidente(); // Ajusta esto según la estructura real de tu objeto incidente
    console.log("Obtengo ID");

    if (incidenteId) {
      // Construye la URL con el parámetro dinámico
      const currentUrl = new URL(window.location.href);
      currentUrl.pathname = "/incidentes/cerrar/" + incidenteId;
      window.location.href = currentUrl.href;
      console.log("Clic en el botón");
    } else {
      console.error("No se pudo obtener el ID del incidente.");
      // Puedes manejar el error de alguna manera apropiada
    }

  }
  function obtenerIdDelIncidente() {
    const incidenteModel = JSON.parse(document.getElementById("incidente-model").textContent);
    return incidenteModel.id; // Ajusta esto según la estructura real de tu objeto incidente
  }
});
