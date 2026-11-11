package nutricionalappfx.dominio.pojo;

import nutricionalappfx.dominio.contraro.IAutenticacionRepository;
import nutricionalappfx.utilidades.Utilidades;

/**
 *
 * @author lopez
 */
public class AutenticacionDM {

    private final IAutenticacionRepository repositorio;

    public AutenticacionDM(IAutenticacionRepository repositorio) {
        this.repositorio = repositorio;
    }

    public Autenticacion verificarCredencialesColaborador(String noPersonal,
            String password) throws RuntimeException {
        Autenticacion respuesta = new Autenticacion();

        if (noPersonal == null || password == null || noPersonal.isEmpty() || password.isEmpty()) {
            respuesta.setCredencialesCorrectas(false);
            respuesta.setMensaje("Los campos no están en el formato solicitado.");
            return respuesta;
        }

        String passwordCifrado = Utilidades.generarCifradoSHA256(password);
        int idColaborador = repositorio.verificarCredenciales(noPersonal, passwordCifrado);

        if (idColaborador > 0) {
            ColaboradorEstatus estatus = repositorio.verificarStaturColaborador(idColaborador);
            if (estatus.getEstatus().equalsIgnoreCase("Activo")) {
                respuesta.setCredencialesCorrectas(true);
                respuesta.setActivo(true);
                respuesta.setColaborador(repositorio.obtenerInformacionColaborador(idColaborador));
                respuesta.setMensaje("Bienvenido(a) al sistema.");
            } else if (estatus.getEstatus().equalsIgnoreCase("Suspendido")
                    || estatus.getIdEstatus() == 3) {
                respuesta.setCredencialesCorrectas(true);
                respuesta.setActivo(false);
                respuesta.setMensaje("Lo sentimos, tu cuenta ha sido suspendida indefinidamente."
                        + "\nContacta a un administrador al correo: soporte@nutriapp.com");
            } else {
                respuesta.setCredencialesCorrectas(false);
                respuesta.setMensaje("El colaborador no está activo en el sistema.");
            }
        } else {
            respuesta.setCredencialesCorrectas(false);
            respuesta.setMensaje("Número de personal o contraseña incorrectos.");
        }
        return respuesta;
    }

}
