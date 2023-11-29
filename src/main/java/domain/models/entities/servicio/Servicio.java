package domain.models.entities.servicio;

import domain.models.entities.usuario.Persona;
import domain.models.entities.persistente.Persistente;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "servicios")
public class Servicio extends Persistente {

    @Column(name = "nombre")
    public String nombre;

    @Column(name = "habilitado")
    public Boolean habilitado;

    @ManyToMany
    public List<Persona> lista_interesados;

    @Column(name = "descripcion")
    public String descripcion;

    @ManyToOne
    @JoinColumn(name= "establecimiento_id",referencedColumnName = "id")
    public Establecimiento establecimiento;

    public Servicio() {
    lista_interesados = new ArrayList<>();
    this.habilitado = true;
    }
}
