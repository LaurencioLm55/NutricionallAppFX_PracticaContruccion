/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nutricionalappfx.dominio.pojo;

/**
 *
 * @author lopez
 */
public class ColaboradorEstatus {
    
    private int idEstatus;
    private String Estatus;

    public ColaboradorEstatus() {
    }

    public ColaboradorEstatus(int idEstatus, String Estatus) {
        this.idEstatus = idEstatus;
        this.Estatus = Estatus;
    }

    public int getIdEstatus() {
        return idEstatus;
    }

    public void setIdEstatus(int idEstatus) {
        this.idEstatus = idEstatus;
    }

    public String getEstatus() {
        return Estatus;
    }

    public void setEstatus(String Estatus) {
        this.Estatus = Estatus;
    }
    
    
}
