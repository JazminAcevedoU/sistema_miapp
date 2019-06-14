/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.impl;

import dao.DecretoDao;
import db.ConnectDB;
import dto.Decreto;
import dto.SubTipoPublicacion;
import dto.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author JAZMIN
 */
public class DecretoDaoImpl implements DecretoDao {

    private final ConnectDB db;
    private final StringBuilder sb;

    public DecretoDaoImpl() {
        this.db = new ConnectDB();
        this.sb = new StringBuilder();
    }

    @Override
    public List<Decreto> getDecretos() {
        sb.delete(0, sb.length()).append(""
                + "SELECT d.id_decretos, tu.t_user, sp.id_stipo_pb "
                + "FROM decreto d "
                + "INNER JOIN t_user tu "
                + "ON d.in_id=tu.in_id "
                + "INNER JOIN subtipo_publicacion sp "
                + "ON d.id_stipo_pb=sp.id_stipo_pb"
        );

        try (
                Connection cn = db.getConnection()) {

            PreparedStatement ps = cn.prepareStatement(sb.toString());

            ResultSet rs = ps.executeQuery();

            List<Decreto> listDecreto = new LinkedList<>();
            while (rs.next()) {
                Decreto decreto = new Decreto();
                decreto.setId(rs.getInt("d.id_decretos"));
                SubTipoPublicacion subTipoPublicacion = new SubTipoPublicacion();
                subTipoPublicacion.setNombre_stipo(rs.getString("sp.id_stipo_pb"));
                decreto.setId_stipo(subTipoPublicacion);
                Usuario usuario = new Usuario();
                usuario.setUsuario(rs.getString("tu.t_user"));
                decreto.setId_usuario(usuario);

                listDecreto.add(decreto);
            }
            return listDecreto;

        } catch (Exception e) {
            throw new Error(e.getMessage());
        }
    }

    @Override
    public Decreto getDecreto(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean createDecreto(Decreto decreto) {
        sb.delete(0, sb.length()).append(""
                + "INSERT INTO decreto "
                + "(in_id, id_stipo_pb) "
                + "VALUES (?, ?)");
        boolean ok = true;
//        try (Connection cn = db.getConnection();
//                PreparedStatement ps = cn.prepareStatement(sb.toString());) {
//            ps.setString(1, Integer.parseInt(decreto.getId_usuario()));
//            ps.setInt(2, decreto.getId_stipo());
//            ps.setInt(3, usuario.getRol().getId());
//            int ctos = ps.executeUpdate();
//            if (ctos == 0) {
//                return false;
//            }
//        } catch (SQLException e) {
//            System.err.println(e.getMessage());
//            return false;
//        }
//        return ok;
    }

    @Override
    public boolean updateDecreto(Decreto decreto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean removeDecreto(ArrayList<Integer> ids) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
