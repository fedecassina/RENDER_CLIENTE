<!-- Añade este código JavaScript al final de tu archivo HTML -->

document.addEventListener('DOMContentLoaded', () => {
    const botonCrear = document.querySelector('.boton');

    botonCrear.addEventListener('click', () => {
    const nombre = document.querySelector('input[type="nombre"]').value;
    const descripcion = document.querySelector('input[type="descripcion"]').value;

    // Crear un objeto con los datos
    const data = {
    nombre: nombre,
    descripcion: descripcion
};

    // Enviar una solicitud POST al controlador
    fetch('/entidades/crear', {
    method: 'POST',
    headers: {
    'Content-Type': 'application/json'
},
    body: JSON.stringify(data)
})
    .then(response => response.json())
    .then(data => {
    console.log('Respuesta del servidor:', data);
    // Aquí puedes realizar otras acciones después de guardar los datos
})
    .catch(error => console.error('Error:', error));
});
});

