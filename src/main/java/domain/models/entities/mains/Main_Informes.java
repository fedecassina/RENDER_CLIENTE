package domain.models.entities.mains;
import domain.models.entities.incidentes.Incidente;
import domain.models.entities.informe.Informe;
import domain.models.entities.ranking.Ranking;
import domain.models.entities.servicio.Entidad;
import domain.models.entities.servicio.Establecimiento;
import domain.models.entities.lectorCSV.EntidadPrestadora;

import java.time.LocalDateTime;
import java.util.List;

public class Main_Informes {
    public static void main(String[] args) {
        LocalDateTime now;
        now = LocalDateTime.now();
        LocalDateTime horaEnUnaHora = now.plusHours(1);
        LocalDateTime horaEnDosHoras = now.plusHours(2);
        LocalDateTime horaEnTresHoras = now.plusHours(3);
        LocalDateTime horaEnCuatroHoras = now.plusHours(4);

        Incidente incidente1 = new Incidente("Baño hombres roto",null,null,null);
        Incidente incidente2 = new Incidente("Baño mujeres roto",null,null,null);
        Incidente incidente3 = new Incidente("Escalera piso 1 rota",null,null,null);
        Incidente incidente4 = new Incidente("Ascensor roto",null,null,null);
        Incidente incidente5 = new Incidente("Escalera piso 2 rota",null,null,null);
        Incidente incidente6 = new Incidente("Escalera piso 3 rota",null,null,null);
    /*
        incidente1.setFechaCierre(horaEnUnaHora);
        incidente2.setFechaCierre(horaEnDosHoras);
        incidente3.setFechaCierre(horaEnTresHoras);
        incidente4.setFechaCierre(horaEnCuatroHoras);
        incidente5.setFechaCierre(horaEnTresHoras);
        incidente6.setFechaCierre(horaEnDosHoras);
*/
        EntidadPrestadora bancoCentral = new EntidadPrestadora("Banco", "Reconquista 266");

        Entidad bancoSantander = new Entidad("Santander Rio");
        Entidad bancoFrances = new Entidad("BBVA Frances");
        Entidad bancoItau = new Entidad("Itau");

        Establecimiento santanderArgentina = new Establecimiento("Santander Argentina", null, null);
        Establecimiento francesArgentina = new Establecimiento("Frances Argentina", null, null);
        Establecimiento itauArgentina = new Establecimiento("Itau Argentina", null, null);

        santanderArgentina.setLista_incidentes(List.of(incidente1,incidente5)); //2
        santanderArgentina.setLista_incidentes(List.of(incidente1,incidente2,incidente3,incidente4)); //2.5
        santanderArgentina.setLista_incidentes(List.of(incidente6,incidente1,incidente3)); //1.5

        bancoSantander.setSucursales(List.of(santanderArgentina));
        bancoFrances.setSucursales(List.of(francesArgentina));
        bancoItau.setSucursales(List.of(itauArgentina));

        bancoCentral.setEntidades(List.of(bancoSantander,bancoFrances,bancoItau));

        Ranking rankeador = new Ranking();

        String ranking1 = rankeador.mayorPromedioDeCierres(bancoCentral);
        String ranking2 = rankeador.mayorCantidadDeIncidentes(bancoCentral);

        Informe informe = new Informe();

        informe.exportarAPDF(ranking1, "src/main/java/domain/Informe/promedio.pdf");
        informe.exportarAPDF(ranking2, "src/main/java/domain/Informe/cantidad.pdf");

    }
}
