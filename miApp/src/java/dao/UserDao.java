
package dao;
 
import dto.Usuario;
import java.util.ArrayList;
import java.util.List;



public interface UserDao {
     //Read
    List<Usuario> getUsuarios();
    Usuario getUser(int id);
    //Write
    boolean createUser(Usuario user);
    boolean updateUser(Usuario user);
    boolean removeUsers(ArrayList<Integer> ids);
}
