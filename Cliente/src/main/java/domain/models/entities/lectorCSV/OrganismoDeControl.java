package domain.models.entities.lectorCSV;

import domain.models.entities.persistente.Persistente;
import domain.models.entities.usuario.Persona;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "organismos_controladores")
@Getter
@Setter

public class OrganismoDeControl extends Persistente {

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "direccion")
    private String direccion;

    @OneToOne
    @JoinColumn(name ="persona_id",referencedColumnName = "id")
    private Persona representante;

    @OneToMany
    @JoinColumn(name ="organismoDeControl_id",referencedColumnName = "id")
    private List<EntidadPrestadora> lista_entidades_prestadoras;

    public OrganismoDeControl(String nombre, String direccion) {
        this.nombre = nombre;
        this.direccion = direccion;
    }

    public OrganismoDeControl() {

    }
}
