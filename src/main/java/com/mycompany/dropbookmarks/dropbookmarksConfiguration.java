package com.mycompany.dropbookmarks;

import io.dropwizard.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.db.DataSourceFactory;
import javax.validation.Valid;
import org.hibernate.validator.constraints.*;
import javax.validation.constraints.*;

public class dropbookmarksConfiguration extends Configuration {

    @NotEmpty
    private String password;
    
    @NotNull
    @Valid
    private DataSourceFactory dataSourceFactory
            = new DataSourceFactory();

    @JsonProperty("database")
    public DataSourceFactory getDataSourceFactory() {
        return dataSourceFactory;
    }    
    
    @JsonProperty
    public String getPassword() {
        return password;
    }
    
    
}
