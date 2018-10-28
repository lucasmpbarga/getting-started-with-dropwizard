package com.mycompany.dropbooks.auth;

import com.google.common.base.Optional;
import com.mycompany.dropbooks.core.User;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;

public class HelloAuthenticator
        implements Authenticator<BasicCredentials, User> {
        private String password;
        
        public HelloAuthenticator(String password){
            this.password = password;
        }
    
    @Override
    public Optional<User> authenticate(BasicCredentials c) throws AuthenticationException {
        if (password.equals(c.getPassword())) {
            return Optional.of(new User());
        } else {
            return Optional.absent();
        }
    }

}
