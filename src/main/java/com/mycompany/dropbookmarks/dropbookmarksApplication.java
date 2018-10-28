package com.mycompany.dropbookmarks;

import com.mycompany.dropbooks.auth.HelloAuthenticator;
import com.mycompany.dropbooks.core.User;
import com.mycompany.dropbooks.resources.HelloResource;
import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.auth.AuthFactory;
import io.dropwizard.auth.basic.BasicAuthFactory;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class dropbookmarksApplication extends Application<dropbookmarksConfiguration> {

    public static void main(final String[] args) throws Exception {
        new dropbookmarksApplication().run(args);
    }

    @Override
    public String getName() {
        return "dropbookmarks";
    }
    
    @Override
    public void initialize(final Bootstrap<dropbookmarksConfiguration> bootstrap) {
      bootstrap.addBundle(new MigrationsBundle<dropbookmarksConfiguration>() {
          @Override
          public DataSourceFactory getDataSourceFactory(dropbookmarksConfiguration t) {
              return t.getDataSourceFactory();
          }
      });
    }

    @Override
    public void run(final dropbookmarksConfiguration configuration,
                    final Environment environment) {
        environment.jersey().register(
                new HelloResource()
        );
        environment.jersey().register(
                AuthFactory.binder(
                        new BasicAuthFactory<>(
                                new HelloAuthenticator(configuration.getPassword()),
                                "SECURITY REALM",
                                User.class
                        )
                )
        );
    }

}
