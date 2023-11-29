package domain.models.entities.lectorCSV;

import domain.models.entities.persistente.Persistente;
import domain.models.entities.servicio.Entidad;
import domain.models.entities.usuario.Persona;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "entidades_prestadoras")
@Getter
@Setter
public class EntidadPrestadora extends Persistente {

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "direccion")
    private String direccion;

    @OneToMany(mappedBy = "entidadPrestadora")
    private List<Entidad> entidades;

    @OneToOne
    @JoinColumn(name ="persona_id",referencedColumnName = "id")
    private Persona representante;

    public EntidadPrestadora(String nombre, String direccion) {
        this.nombre = nombre;
        this.direccion = direccion;
    }

    public EntidadPrestadora() {

    }
    public void agregarEntidad (Entidad entidad){
        entidades.add(entidad);
    }
    public String getNombre(){
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public List<Entidad> getEntidades() {
        return this.entidades;
    }
}
