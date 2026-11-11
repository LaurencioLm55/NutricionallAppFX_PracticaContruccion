package nutricionalappfx.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import nutricionalappfx.dominio.contraro.IAutenticacionRepository;
import nutricionalappfx.dominio.pojo.Colaborador;
import nutricionalappfx.dominio.pojo.ColaboradorEstatus;
import nutricionalappfx.modelo.ConexionBD;

/**
 *
 * @author lopez
 */
public class AutenticacionDAO implements IAutenticacionRepository {

    @Override
    public int verificarCredenciales(String noPersonal, String contraseña) throws RuntimeException {
        String consulta = "SELECT idColaborador FROM colaboradores WHERE noPersonal = ? AND password = ?";
        try (Connection conexion = ConexionBD.obtenerConexion();
                PreparedStatement ps = conexion.prepareStatement(consulta)) {
            ps.setString(1, noPersonal);
            ps.setString(2, contraseña);
            try (ResultSet resultado = ps.executeQuery()) {
                return resultado.next() ? resultado.getInt("idColaborador") : -1;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error de conexión con el almacenamiento");
        }
    }

    @Override
    public ColaboradorEstatus verificarStaturColaborador(int idColaborador) throws RuntimeException {
        String consulta = "SELECT c.idEstatus, e.estatus "
                + "FROM colaboradores c "
                + "INNER JOIN colaboradoresEstatus e ON e.idEstatus = c.idEstatus "
                + "WHERE c.idColaborador = ?";
        try (Connection conexion = ConexionBD.obtenerConexion();
                PreparedStatement ps = conexion.prepareStatement(consulta)) {
            ps.setInt(1, idColaborador);
            try (ResultSet resultado = ps.executeQuery()) {
                if (resultado.next()) {
                    ColaboradorEstatus estatus = new ColaboradorEstatus();
                    estatus.setIdEstatus(resultado.getInt("idEstatus"));
                    estatus.setEstatus(resultado.getString("estatus"));
                    return estatus;
                }
                throw new RuntimeException("Error en la integridad de los datos, inténtelo más tarde");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error de conexión con el almacenamiento");
        }
    }

    @Override
    public Colaborador obtenerInformacionColaborador(int idColaborador) throws RuntimeException {
        String consulta = "SELECT c.idColaborador, c.nombre, c.apellidoPaterno, c.apellidoMaterno, "
                + "c.noPersonal, c.fechaContratacion, c.idRol, r.rol, c.idEstatus, e.estatus "
                + "FROM colaboradores c "
                + "INNER JOIN colaboradoresEstatus e ON e.idEstatus = c.idEstatus "
                + "INNER JOIN roles r ON r.idRol = c.idRol "
                + "WHERE c.idColaborador = ?";
        try (Connection conexion = ConexionBD.obtenerConexion();
                PreparedStatement ps = conexion.prepareStatement(consulta)) {
            ps.setInt(1, idColaborador);
            try (ResultSet resultado = ps.executeQuery()) {
                if (resultado.next()) {
                    Colaborador colaborador = new Colaborador();
                    colaborador.setIdColaborador(resultado.getInt("idColaborador"));
                    colaborador.setNoPersonal(resultado.getString("noPersonal"));
                    colaborador.setNombre(resultado.getString("nombre"));
                    colaborador.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                    String materno = resultado.getString("apellidoMaterno");
                    colaborador.setApellidoMaterno(materno != null ? materno : "");
                    colaborador.setFechaContratacion(resultado.getString("fechaContratacion"));
                    colaborador.setIdRol(resultado.getInt("idRol"));
                    colaborador.setRol(resultado.getString("rol"));
                    colaborador.setIdEstatus(resultado.getInt("idEstatus"));
                    colaborador.setEstatus(resultado.getString("estatus"));
                    return colaborador;
                }
                throw new RuntimeException("Error en la integridad de los datos, inténtelo más tarde");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error de conexión con el almacenamiento");
        }
    }

}
