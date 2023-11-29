package domain.controllers;

import domain.models.entities.lectorCSV.CSVParserEntidad;
import domain.models.entities.lectorCSV.EntidadPrestadora;
import domain.models.repositories.RepositorioDeCargaEntidades;
import domain.server.utils.ICrudViewsHandler;
import io.javalin.http.Context;

import java.io.InputStream;
import java.util.List;

public class CargaMasivaEntidadController extends Controller implements ICrudViewsHandler  {

    private RepositorioDeCargaEntidades repositorioDeCargaEntidades;

    public CargaMasivaEntidadController(RepositorioDeCargaEntidades  repositorioDeCargaEntidades) {
        this.repositorioDeCargaEntidades = repositorioDeCargaEntidades;
    }

    @Override
    public void index(Context context) {

    }

    @Override
    public void show(Context context) {
    }

    @Override
    public void create(Context context) {

        // Procesa el archivo CSV según tus necesidades
        InputStream fileInputStream = context.uploadedFile("file").content();
         List<EntidadPrestadora> entidades = CSVParserEntidad.parseCSV(fileInputStream);
        for (EntidadPrestadora entidad : entidades) {
            repositorioDeCargaEntidades.guardar(entidad);

        }
        context.redirect("/perfil");
    }

    @Override
    public void save(Context context) {

    }
    @Override
    public void edit(Context context) {

    }
    @Override
    public void update(Context context) {

    }
    @Override
    public void delete(Context context) {

    }
}
