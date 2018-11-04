package com.mycompany.dropbookmarks;

import com.mycompany.dropbookmarks.auth.DBAuthenticator;
import com.mycompany.dropbookmarks.core.Bookmark;
import com.mycompany.dropbookmarks.core.User;
import com.mycompany.dropbookmarks.db.BookmarkDAO;
import com.mycompany.dropbookmarks.db.UserDAO;
import com.mycompany.dropbookmarks.resources.BookmarksResource;
import com.mycompany.dropbookmarks.resources.HelloResource;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthFactory;
import io.dropwizard.auth.basic.BasicAuthFactory;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class dropbookmarksApplication extends Application<dropbookmarksConfiguration> {

    private final HibernateBundle<dropbookmarksConfiguration> hibernateBundle
            = new HibernateBundle<dropbookmarksConfiguration>(User.class,
                    Bookmark.class) {
        @Override
        public DataSourceFactory getDataSourceFactory(dropbookmarksConfiguration configuration) {
            return configuration.getDataSourceFactory();
        }
    };

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
        bootstrap.addBundle(hibernateBundle);
    }

    @Override
    public void run(final dropbookmarksConfiguration configuration,
            final Environment environment) {
        final UserDAO userDAO
                = new UserDAO(hibernateBundle.getSessionFactory());
        
        final BookmarkDAO bookmarkDAO
                = new BookmarkDAO(hibernateBundle
                .getSessionFactory());
        environment.jersey().register(
                new BookmarksResource(bookmarkDAO)
        );
        
        environment.jersey().register(
                new HelloResource()
        );
        environment.jersey().register(
                AuthFactory.binder(
                        new BasicAuthFactory<>(
                                new DBAuthenticator(userDAO),
                                "SECURITY REALM",
                                User.class
                        )
                )
        );
    }

}
