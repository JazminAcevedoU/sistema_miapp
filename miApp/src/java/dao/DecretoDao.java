
package dao;

import dto.Decreto;
import java.util.ArrayList;
import java.util.List;

public interface DecretoDao {
    //read
     List<Decreto> getDecretos();
    Decreto getDecreto(int id);
    
    //Write
    boolean createDecreto(Decreto decreto);
    boolean updateDecreto(Decreto decreto);
    boolean removeDecreto(ArrayList<Integer> ids);
}
