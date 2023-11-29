Handlebars.registerHelper('contienePermiso', function(permisos, permisoBuscado, options) {
    if (permisos && permisos.some(permiso => permiso.nombreInterno === permisoBuscado)) {
        return options.fn(this);
    } else {
        return options.inverse(this);
    }
});
