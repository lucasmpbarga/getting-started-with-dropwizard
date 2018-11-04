package com.mycompany.dropbookmarks.db;

import com.google.common.base.Optional;
import com.mycompany.dropbookmarks.core.Bookmark;
import io.dropwizard.hibernate.AbstractDAO;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

/**
 * A data access object to manipulate bookmarks.
 *
 * @author Dmitry Noranovich
 */
public class BookmarkDAO extends AbstractDAO<Bookmark> {

    public BookmarkDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    /**
     * Find all bookmarks for the user with specified id.
     *
     * @param id user id
     * @return list of bookmarks for the user with specified id.
     */
    public List<Bookmark> findForUser(long id) {
        return list(
                namedQuery("com.mycompany.dropbookmarks.core.Bookmark.findForUser")
                        .setParameter("id", id)
        );
    }

    /**
     * Finds a bookmark by its id.
     *
     * @param id id of a bookmark
     * @return a bookmark with specified id
     */
    public Optional<Bookmark> findById(long id) {
        return Optional.fromNullable(get(id));
    }

    /**
     * Create or Update a bookmark.
     *
     * @param bookmark a bookmark to be saved
     * @return the saved bookmark with all auto-generated fields filled.
     */
    public Bookmark save(Bookmark bookmark) {
        try{
             return persist(bookmark);
        }catch(HibernateException e){
            return  null;
        }       
    }

    /**
     * Removes a bookmark from the database.
     *
     * @param bookmark a bookmark to be removed.
     */
    public void delete(Bookmark bookmark) {
        namedQuery("com.mycompany.dropbookmarks.core.Bookmark.remove")
                .setParameter("id", bookmark.getId())
                .executeUpdate();
    }
}
