package dao.impl;

import dao.RolDao;
import db.ConnectDB;
import dto.Rol;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


public class RolDaoImpl implements RolDao {

    private final ConnectDB db;
    private final StringBuilder sb;

    public RolDaoImpl() {
        this.db = new ConnectDB();
        this.sb = new StringBuilder();
    }

    @Override
    public List<Rol> getRoles() {
        sb.delete(0, sb.length()).append(""
                + "SELECT in_id, vc_name FROM t_rol");

        try (
                Connection cn = db.getConnection()) {
            //contener query
            PreparedStatement ps = cn.prepareStatement(sb.toString());
            // ejecutar la conexión consulta
            ResultSet rs = ps.executeQuery();

            List<Rol> listRol = new LinkedList<>();
            while (rs.next()) {
                //next se corta cuando ya no hay objeto
                Rol rol = new Rol();
                rol.setId(rs.getInt("in_id")); //puede ser el numero del index (1)
                rol.setNombre(rs.getString("vc_name"));

                listRol.add(rol);
            }
            return listRol;
            //excepcion -> error general, sqlexcepcion -> especifica el error en la query
        } catch (SQLException e) {
            //sout
            throw new Error(e.getMessage());
            // return null

        }

    }

}
