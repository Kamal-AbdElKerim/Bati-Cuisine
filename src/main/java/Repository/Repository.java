package Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public interface Repository<T> {

    HashMap<Integer, T> findAll();

    T findById(int id);

    T findByName(String name);

    int save( T entity);

    T mapRow(ResultSet rs) throws SQLException;
}
