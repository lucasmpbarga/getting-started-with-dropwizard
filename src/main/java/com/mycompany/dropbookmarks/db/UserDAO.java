/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dropbookmarks.db;

import com.google.common.base.Optional;
import com.mycompany.dropbookmarks.core.User;
import io.dropwizard.hibernate.AbstractDAO;
import java.util.List;
import org.hibernate.SessionFactory;

/**
 *
 * @author computer
 */
public class UserDAO extends AbstractDAO<User>{
    
    public UserDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
    
    public List<User> findAll(){
        return list(
                namedQuery(
                        "com.mycompany.dropbookmarks.core.User.findAll"
                )
        );
    }
    
    public Optional<User> findByUsernamePassword(
            String username,
            String password
    ){
        return Optional.fromNullable(
                uniqueResult(
                        namedQuery("com.mycompany.dropbookmarks.core.User.findByUsernamePassword")
                        .setParameter("username", username)
                        .setParameter("password", password)                        
                )
        );
    }
    
}
