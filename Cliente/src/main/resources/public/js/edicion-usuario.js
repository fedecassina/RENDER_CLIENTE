function eliminarUsuario() {
    const usuarioId = obtenerIdUsuario();

    if(usuarioId) {
        const currentUrl = new URL(window.location.href);
        currentUrl.pathname = "/usuarios/eliminar/" + usuarioId;
        window.location.href = currentUrl.href;
    } else {
        console.error("No se pudo obtener el ID del incidente.");
    }

    console.log("Usuario eliminado"); // Ejemplo: Mostrar un mensaje en la consola
}

function obtenerIdUsuario() {
    const usuarioModel = JSON.parse(document.getElementById("usuario-model").textContent);
    return usuarioModel.id;
}

// Espera a que el documento esté completamente cargado
document.addEventListener("DOMContentLoaded", function () {
    const eliminarUsuarioBtn = document.getElementById("boton-eliminar-usuario");

    eliminarUsuarioBtn.addEventListener("click", function () {
        eliminarUsuario();
    });
});


