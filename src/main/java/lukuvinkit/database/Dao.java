/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lukuvinkit.database;

import java.sql.SQLException;

/**
 *
 * @author strajama
 */
public interface Dao <T>{

    T save(T t) throws SQLException;
    
}
