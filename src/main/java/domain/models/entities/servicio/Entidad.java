package domain.models.entities.servicio;

import domain.models.entities.incidentes.Incidente;
import domain.models.entities.JSONconverter.JSONconverter;
import domain.models.entities.clienteAPI.ClienteRest;
import domain.models.entities.lectorCSV.EntidadPrestadora;
import domain.models.entities.usuario.Persona;
import domain.models.entities.persistente.Persistente;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "entidades")
@Getter
@Setter
public class Entidad extends Persistente {

    @Column
    private String nombre;

    @Column
    private String descripcion;

    @OneToMany
    @JoinColumn(name = "establecimiento_id", referencedColumnName = "id")
    private List<Establecimiento> sucursales;

    @ManyToMany
    private List<Persona> lista_interesados;

    @ManyToOne
    @JoinColumn(name = "entidad_prestadora_id", referencedColumnName = "id")
    private EntidadPrestadora entidadPrestadora;

    public Entidad (String nombre) {
        this.nombre = nombre;
    }

    public Entidad() {
    lista_interesados = new ArrayList<>();
    }

    public Float promedioDeCierre(){
        Float promedio;
        Long sumaTiempos = sucursales.stream()
                .map(Establecimiento::obtenerTiemposIncidentes)
                .flatMap(List::stream)
                .mapToLong(Long::valueOf)
                .sum();
        int cantidadSucursales = sucursales.size();
        promedio = sumaTiempos.floatValue() / cantidadSucursales;
        return promedio;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Integer incidentesSemanales() {
        // TODO
        return 0;
    }

    public double obtenerNumeroRanking () throws IOException {
        ClienteRest cliente = new ClienteRest();
        double numero = cliente.obtenerNumeroRanking(this);
        numero = numero * this.calcularTotalPersonasAfectadas();
        return numero; //VER ESTO TODO
    }

    public String incidentesEntidad() throws IOException {
        List<Incidente> todosLosIncidentes = new ArrayList<>();
        JSONconverter convertidor = new JSONconverter();

        for (Establecimiento sucursal : sucursales) {
            List<Incidente> incidentes = sucursal.getLista_incidentes();
            todosLosIncidentes.addAll(incidentes);
        }

        String incidentesJson = convertidor.toJson(todosLosIncidentes);

        return incidentesJson;
    }
    public void agregarInteresado(Persona... personas){
        lista_interesados.addAll(List.of(personas));
    }

    public int calcularTotalPersonasAfectadas() {
        int totalPersonasAfectadas = 0;

        // Recorremos la lista de establecimientos
        for (Establecimiento establecimiento : sucursales) {
            // Sumamos la cantidad de personas afectadas de cada establecimiento
            totalPersonasAfectadas += establecimiento.devolverNumeroAfectados();
        }

        return totalPersonasAfectadas;
    }

    public void sacarInteresado(Persona persona){
        lista_interesados.remove(persona);
    }
}




