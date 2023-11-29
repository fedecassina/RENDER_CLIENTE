document.addEventListener("DOMContentLoaded", function () {
  const boton = document.querySelector(".sombra-boton");

  boton.addEventListener("click", function () {
    boton.classList.add("sombra-clic");

    setTimeout(function () {
      boton.classList.remove("sombra-clic");
    }, 150); // Tiempo de transicion
  });
});
