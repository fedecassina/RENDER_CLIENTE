package domain.controllers;

import domain.models.entities.lectorCSV.CSVParserOrganismo;
import domain.models.entities.lectorCSV.OrganismoDeControl;
import domain.models.repositories.RepositorioDeCargaOrganismos;
import domain.server.utils.ICrudViewsHandler;
import io.javalin.http.Context;

import java.io.InputStream;
import java.util.List;


public class CargaMasivaOrganismoController extends Controller implements ICrudViewsHandler {

    private RepositorioDeCargaOrganismos repositorioDeCargasMasivasDeDatos;

    public CargaMasivaOrganismoController(RepositorioDeCargaOrganismos repositorioDeCargaOrganismos) {
        this.repositorioDeCargasMasivasDeDatos = repositorioDeCargaOrganismos;
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

           List<OrganismoDeControl> organismos = CSVParserOrganismo.parseCSV(fileInputStream);
           for (OrganismoDeControl organismo : organismos) {
           repositorioDeCargasMasivasDeDatos.guardar(organismo);
                   }

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
