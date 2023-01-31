package nl.aim.regterschotracing.domain.exceptionmappers;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import nl.aim.regterschotracing.domain.exceptions.DatabaseException;

@Provider
public class DatabaseExceptionMapper implements ExceptionMapper<DatabaseException> {
    /**
     * Sends Response object with status 403 when method is called.
     * @param databaseException is the exception that is thrown.
     * @return Response object that sends status 403 plus message.
     */
    @Override
    public Response toResponse(DatabaseException databaseException) {
        return Response.status(403).entity(databaseException.getMessage()).type("text/plain").build();
    }
}
