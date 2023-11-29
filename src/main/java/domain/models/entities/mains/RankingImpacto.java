package domain.models.entities.mains;

import domain.models.entities.incidentes.Incidente;
import domain.models.entities.informe.Informe;
import domain.models.entities.ranking.Ranking;
import domain.models.entities.servicio.Entidad;
import domain.models.entities.servicio.Establecimiento;
import domain.models.entities.usuario.Comunidad;
import domain.models.entities.usuario.Persona;
import domain.models.entities.lectorCSV.EntidadPrestadora;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RankingImpacto {
    public static void main(String[] args) {
        // Obtener la fecha actual
        Calendar calendario = Calendar.getInstance();
        Date fechaActual = calendario.getTime();

        // Obtener fechas dentro de 1, 2, 3 y 4 días
        Calendar calendario1Dia = Calendar.getInstance();
        calendario1Dia.setTime(fechaActual);
        calendario1Dia.add(Calendar.DAY_OF_YEAR, 1);
        Date fechaDentroDe1Dia = calendario1Dia.getTime();

        Calendar calendario2Dias = Calendar.getInstance();
        calendario2Dias.setTime(fechaActual);
        calendario2Dias.add(Calendar.DAY_OF_YEAR, 2);
        Date fechaDentroDe2Dias = calendario2Dias.getTime();

        Calendar calendario3Dias = Calendar.getInstance();
        calendario3Dias.setTime(fechaActual);
        calendario3Dias.add(Calendar.DAY_OF_YEAR, 3);
        Date fechaDentroDe3Dias = calendario3Dias.getTime();

        Calendar calendario4Dias = Calendar.getInstance();
        calendario4Dias.setTime(fechaActual);
        calendario4Dias.add(Calendar.DAY_OF_YEAR, 4);
        Date fechaDentroDe4Dias = calendario4Dias.getTime();

        Comunidad comunidad1 = new Comunidad("comunidad1");
        Comunidad comunidad2 = new Comunidad("comunidad2");

        Persona federico = new Persona("Federico", Boolean.TRUE);
        Persona martina = new Persona("martina", Boolean.TRUE);
        Persona facundo = new Persona("facundo", Boolean.TRUE);
        Persona pedro = new Persona("pedro", Boolean.TRUE);
        Persona tomas = new Persona("tomas", Boolean.TRUE);

        comunidad1.agregarMiembros(federico,martina,tomas);
        comunidad2.agregarMiembros(facundo,pedro);

        federico.agregarComunidad(comunidad1);
        martina.agregarComunidad(comunidad1);
        tomas.agregarComunidad(comunidad1);
        pedro.agregarComunidad(comunidad2);
        facundo.agregarComunidad(comunidad1);

        Entidad bancoSantander = new Entidad("Santander Rio");
        Entidad bancoFrances = new Entidad("BBVA Frances");
        Entidad bancoItau = new Entidad("Itau");

        Establecimiento santanderArgentina = new Establecimiento("Santander Argentina", null, null);
        Establecimiento francesArgentina = new Establecimiento("Frances Argentina", null, null);
        Establecimiento itauArgentina = new Establecimiento("Itau Argentina", null, null);

        Incidente incidente1 = new Incidente("Baño hombres roto",federico,null,santanderArgentina);
        Incidente incidente2 = new Incidente("Baño mujeres roto",martina,null,santanderArgentina);
        Incidente incidente3 = new Incidente("Escalera piso 1 rota",facundo,null,santanderArgentina);
        Incidente incidente4 = new Incidente("Ascensor roto",pedro,null,francesArgentina);
        Incidente incidente5 = new Incidente("Escalera piso 2 rota",tomas,null,francesArgentina);
        Incidente incidente6 = new Incidente("Escalera piso 3 rota",federico,null,itauArgentina);

        incidente2.cerrar(fechaDentroDe2Dias);
        incidente3.cerrar(fechaDentroDe3Dias);
        incidente4.cerrar(fechaDentroDe4Dias);
        incidente5.cerrar(fechaDentroDe3Dias);
        incidente6.cerrar(fechaDentroDe2Dias);

        EntidadPrestadora bancoCentral = new EntidadPrestadora("Banco", "Reconquista 266");

        santanderArgentina.setLista_incidentes(List.of(incidente1,incidente2,incidente3)); //2
        francesArgentina.setLista_incidentes(List.of(incidente5,incidente4)); //2.5
        itauArgentina.setLista_incidentes(List.of(incidente6)); //1.5

        bancoSantander.setSucursales(List.of(santanderArgentina));
        bancoFrances.setSucursales(List.of(francesArgentina));
        bancoItau.setSucursales(List.of(itauArgentina));

        bancoCentral.setEntidades(List.of(bancoSantander,bancoFrances,bancoItau));

        Ranking rankeador = new Ranking();

        String ranking1 = rankeador.mayorImpactoDeLasProblematicas(bancoCentral);

        Informe informe = new Informe();

        informe.exportarAPDF(ranking1, "src/main/java/domain/Informe/problematicas.pdf");

    }
}
