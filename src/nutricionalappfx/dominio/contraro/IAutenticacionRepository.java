package nutricionalappfx.dominio.contraro;

import nutricionalappfx.dominio.pojo.Colaborador;
import nutricionalappfx.dominio.pojo.ColaboradorEstatus;

/**
 *
 * @author lopez
 */
public interface IAutenticacionRepository {

    /**
     * @return idColaborador si las credenciales son correctas, -1 si no lo son.
     */
    public int verificarCredenciales(String noPersonal, String contraseña) throws RuntimeException;

    public ColaboradorEstatus verificarStaturColaborador(int idColaborador) throws RuntimeException;

    public Colaborador obtenerInformacionColaborador(int idColaborador) throws RuntimeException;
}
