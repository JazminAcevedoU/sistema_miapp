/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

/**
 *
 * @author JAZMIN
 */
public class Decreto {
    private int id;
    private Usuario id_usuario;
    private SubTipoPublicacion id_stipo;

    public Decreto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Usuario getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Usuario id_usuario) {
        this.id_usuario = id_usuario;
    }

    public SubTipoPublicacion getId_stipo() {
        return id_stipo;
    }

    public void setId_stipo(SubTipoPublicacion id_stipo) {
        this.id_stipo = id_stipo;
    }

    
    
    
}
