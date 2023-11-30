package domain.server.init;

import domain.models.entities.roles.Permiso;
import domain.models.entities.roles.Rol;
import domain.models.entities.roles.TipoRol;
import domain.server.Server;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

public class Initializer implements WithSimplePersistenceUnit {

     static EntityManager entityManager = Server.createEntityManager();


    public static void init() {
        new Initializer()
                .iniciarTransaccion()
                .permisos()
                .roles()
                .commitearTransaccion();

    }

    private Initializer iniciarTransaccion() {
        entityManager.getTransaction().begin();
        return this;
    }

    private Initializer commitearTransaccion() {
        entityManager.getTransaction().commit();
        return this;
    }

    private Initializer permisos() {
        String[][] permisos = {
                { "Administrar usuarios", "administrar_usuarios" },
                { "Crear comunidades", "crear_comunidades" },
                { "Carga masiva de datos", "carga_masiva_de_datos" },
                { "Ver rankings", "ver_rankings" },
                { "Crear entidades", "crear_entidades"},
                { "Crear servicios", "crear_servicios"},
                { "Cargar establecimientos", "cargar_establecimientos"},
                // ACÁ HAY QUE VER QUE OTROS PERMISOS PODEMOS NECESITAR
        };
        
        for(String[] unPermiso: permisos) {
            if (this.buscarPermisoPorNombre(unPermiso[1]) == null) {
                Permiso permiso = new Permiso();
                permiso.setNombre(unPermiso[0]);
                permiso.setNombreInterno(unPermiso[1]);
                entityManager.persist(permiso);
            }
        }

        return this;
    }

    /*private interface BuscadorDePermisos extends WithSimplePersistenceUnit {
        default Permiso buscarPermisoPorNombre(String nombre) {
            try {
                return (Permiso) entityManager
                        .createQuery("from " + Permiso.class.getName() + " where nombreInterno = :nombre")
                        .setParameter("nombre", nombre)
                        .getSingleResult();
            } catch (NoResultException e) {
                   return null;
            }
        }
    }*/

    /* public Permiso buscarPermisoPorNombre(String nombre) {
        try {
            return (Permiso) entityManager
                    .createQuery("from " + Permiso.class.getName() + " where nombreInterno = :nombre")
                    .setParameter("nombre", nombre)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    } */

    public Permiso buscarPermisoPorNombre(String nombre) {
        try {
            Query query = entityManager.createQuery("from Permiso where nombreInterno = :nombre");
            query.setParameter("nombre", nombre);
            return (Permiso) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public static Rol buscarRolPorNombre(String nombre) {
        try {
            Query query = entityManager.createQuery("from Rol where nombre = :nombre");
            query.setParameter("nombre", nombre);
            return (Rol) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /* public Rol buscarRolPorNombre(String nombre) {
        try {
            return (Rol) entityManager
                    .createQuery("from " + Rol.class.getName() + " where nombre = :nombre")
                    .setParameter("nombre", nombre)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    } */

    /*private interface BuscadorDeRoles extends WithSimplePersistenceUnit {
        default Rol buscarRolPorNombre(String nombre) {
            try {
                return (Rol) entityManager
                        .createQuery("from " + Rol.class.getName() + " where nombre = :nombre")
                        .setParameter("nombre", nombre)
                        .getSingleResult();
            } catch (NoResultException e) {
                return null;
            }
        }
    }*/
    private Initializer roles() {
        /*BuscadorDePermisos buscadorDePermisos = new BuscadorDePermisos() {};
        BuscadorDeRoles buscadorDeRoles = new BuscadorDeRoles() {};*/

        if (this.buscarRolPorNombre("Administrador") == null) {
            Rol administrador = new Rol();
            administrador.setNombre("Administrador");
            administrador.setTipo(TipoRol.ADMINISTRADOR);
            administrador.agregarPermisos(
                    this.buscarPermisoPorNombre("administrar_usuarios"),
                    this.buscarPermisoPorNombre("crear_comunidades"),
                    this.buscarPermisoPorNombre("ver_rankings"),
                    this.buscarPermisoPorNombre("crear_entidades"),
                    this.buscarPermisoPorNombre("crear_servicios"),
                    this.buscarPermisoPorNombre("carga_masiva_de_datos"),
                    this.buscarPermisoPorNombre("cargar_establecimientos")
            );
            entityManager.persist(administrador);
        }

        if (this.buscarRolPorNombre("Comun") == null) {
            Rol consumidor = new Rol();
            consumidor.setNombre("Comun");
            consumidor.setTipo(TipoRol.NORMAL);
            consumidor.agregarPermisos(
            );
            entityManager.persist(consumidor);
        }

        if (this.buscarRolPorNombre("Prestador") == null) {
            Rol prestador = new Rol();
            prestador.setNombre("Prestador");
            prestador.setTipo(TipoRol.NORMAL);
            prestador.agregarPermisos(
                    this.buscarPermisoPorNombre("ver_rankings"),
                    this.buscarPermisoPorNombre("crear_entidades"),
                    this.buscarPermisoPorNombre("crear_servicios"),
                    this.buscarPermisoPorNombre("cargar_establecimientos")
            );
            entityManager.persist(prestador);
        }

        return this;
    }
}