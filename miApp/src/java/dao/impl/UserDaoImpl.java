package dao.impl;

import dao.UserDao;
import db.ConnectDB;
import dto.Rol;
import dto.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class UserDaoImpl implements UserDao {

    private final ConnectDB db;
    private final StringBuilder sb;

    public UserDaoImpl() {
        this.db = new ConnectDB();
        this.sb = new StringBuilder();
    }

    
    @Override
    public List<Usuario> getUsuarios() {
        sb.delete(0, sb.length()).append(""
                + "SELECT u.in_id, u.vc_username, r.in_id, r.vc_name "
                + "FROM db_app.t_user u "
                + "INNER JOIN db_app.t_rol r "
                + "ON r.in_id = u.in_id_rol "
                + "WHERE u.bl_enable = 1");

        try (
                Connection cn = db.getConnection()) {

            PreparedStatement ps = cn.prepareStatement(sb.toString());

            ResultSet rs = ps.executeQuery();

            List<Usuario> listUser = new LinkedList<>();
            while (rs.next()) {
Usuario user = new Usuario();
                user.setId(rs.getInt("u.in_id"));
                user.setUsuario(rs.getString("u.vc_username"));
                user.setPass("????????");
                Rol rol = new Rol();
                rol.setId(rs.getInt("r.in_id"));
                rol.setNombre(rs.getString("r.vc_name"));
                user.setRol(rol);
                listUser.add(user);
            }
            return listUser;

        } catch (Exception e) {
            throw new Error(e.getMessage());
        }

    }
    
    @Override
    public boolean createUser(Usuario usuario) {
        sb.delete(0, sb.length()).append(""
                + "INSERT INTO t_user "
                + "(vc_username, vc_password, in_id_rol) "
                + "VALUES (?, ?, ?)");
        boolean ok = true;
        try (Connection cn = db.getConnection();
                PreparedStatement ps = cn.prepareStatement(sb.toString());) {
            ps.setString(1, usuario.getUsuario());
            ps.setString(2, usuario.getPass());
            ps.setInt(3, usuario.getRol().getId());
            int ctos = ps.executeUpdate();
            if (ctos == 0) {
                return false;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
        return ok;
    }

    @Override
    public Usuario getUser(int id) {
        sb.delete(0, sb.length()).append(""
                + "SELECT u.in_id, u.vc_username, r.in_id, r.vc_name "
                + "FROM db_app.t_user u "
                + "INNER JOIN db_app.t_rol r "
                + "ON r.in_id = u.in_id_rol "
                + "WHERE u.bl_enable = 1 AND u.in_id = ?");

        try (Connection cn = db.getConnection();
                PreparedStatement ps = cn.prepareStatement(sb.toString());) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Usuario user = new Usuario();
                    user.setId(rs.getInt("u.in_id"));
                    user.setUsuario(rs.getString("u.vc_username"));
                    user.setPass("????????");
                    Rol rol = new Rol();
                    rol.setId(rs.getInt("r.in_id"));
                    rol.setNombre(rs.getString("r.vc_name"));
                    user.setRol(rol);
                    return user;
                } else {
                    return null;
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
                return null;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public boolean updateUser(Usuario user) {
     sb.delete(0, sb.length()).append(""
                + "UPDATE db_app.t_user "
                + "SET vc_username = ?, vc_password = ?, in_id_rol = ? "
                + "WHERE in_id = ?");
        boolean ok = true;
        try (Connection cn = db.getConnection();
                PreparedStatement ps = cn.prepareStatement(sb.toString());) {
            ps.setString(1, user.getUsuario());
            ps.setString(2, user.getPass());
            ps.setInt(3, user.getRol().getId());
            ps.setInt(4, user.getId());
            int ctos = ps.executeUpdate();
            if (ctos == 0) {
                return false;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean removeUsers(ArrayList<Integer> ids) {
     sb.delete(0, sb.length()).append(""
                + "UPDATE db_app.t_user "
                + "SET bl_enable = 0 "
                + "WHERE in_id = ?");

        try (Connection cn = db.getConnection();
                PreparedStatement ps = cn.prepareStatement(sb.toString());) {
            boolean ok = true;
            cn.setAutoCommit(false);
            for (Integer id : ids) {
                int ctos = ps.executeUpdate();
                if (ctos == 0) {
                    ok = false;
                }
            }
            if (ok) {
                cn.setAutoCommit(true);
                return true;
            } else {
                cn.rollback();
                return false;
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
    }
}
