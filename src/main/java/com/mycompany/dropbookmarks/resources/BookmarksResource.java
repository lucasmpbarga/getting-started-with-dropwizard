package com.mycompany.dropbookmarks.resources;

import com.google.common.base.Optional;
import com.mycompany.dropbookmarks.core.Bookmark;
import com.mycompany.dropbookmarks.core.User;
import com.mycompany.dropbookmarks.db.BookmarkDAO;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/bookmarks")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookmarksResource {

    private BookmarkDAO dao;

    public BookmarksResource(BookmarkDAO dao) {
        this.dao = dao;
    }

    @GET
    @UnitOfWork
    public List<Bookmark> getBookmarks(@Auth User user) {
        return dao.findForUser(user.getId());
    }

    private Optional<Bookmark> findIfAuthorized(long bookmarkId, long userId) {
        Optional<Bookmark> result
                = dao.findById(bookmarkId);
        if (result.isPresent()
                && userId != result.get()
                        .getUser().getId()) {
            throw new ForbiddenException("You are not authorized to see this resource.");
        }
        return result;
    }

    @GET
    @Path("/{id}")
    @UnitOfWork
    public Optional<Bookmark> getBookmark(@PathParam("id") LongParam id, @Auth User user) {
        return findIfAuthorized(id.get(), user.getId());
    }

    @DELETE
    @Path("/{id}")
    @UnitOfWork
    public Optional<Bookmark> delete(@PathParam("id") LongParam id, @Auth User user) {
        Optional<Bookmark> bookmark
                = findIfAuthorized(id.get(), user.getId());
        if (bookmark.isPresent()) {
            dao.delete(bookmark.get());
        }
        return bookmark;
    }

    @POST
    @UnitOfWork
    public Bookmark saveBookmark(Bookmark bookmark, @Auth User user) {
        bookmark.setUser(user);
        return dao.save(bookmark);
    }

    @PUT
    @Path("/{id}")
    @UnitOfWork
    public Bookmark update(@PathParam("id") LongParam id, Bookmark bookmark, @Auth User user) {
        bookmark.setId(id.get());
        bookmark.setUser(user);
        return dao.save(bookmark);
    }
}
