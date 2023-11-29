document.addEventListener("DOMContentLoaded", function () {
    const boton = document.querySelector(".sombra-boton");

    boton.addEventListener("click", function () {
        boton.classList.add("sombra-clic");

        setTimeout(function () {
            boton.classList.remove("sombra-clic");
        }, 150); // Tiempo de transición

        // Acceso al archivo seleccionado
        const fileInput = document.getElementById("archivo");
        const file = fileInput.files[0];

        if (file) {
            const reader = new FileReader();
            reader.onload = function (e) {
                const csvString = e.target.result; // Contenido del CSV en formato string
                // Llamar al método recibirCSV de la clase Controller

            };
            reader.readAsText(file);
        }
    });
});


