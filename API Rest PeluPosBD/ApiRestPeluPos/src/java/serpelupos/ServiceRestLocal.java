
package serpelupos;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import peluposbd.Local;
import peluposbd.LocalJpaController;

@Path("locales")
public class ServiceRestLocal {
    
    private static final String PERSISTENCE_UNIT = "ApiRestPeluPosPU";
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllController() {
        HashMap<String, String> mensaje = new HashMap<>();
        Response response;
        Response.Status statusResul;
        List<Local> lista;
        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT)) {

            // **************************************************************
            // Obtener datos con CONTROLLER
            LocalJpaController dao = new LocalJpaController(emf);
            lista = dao.findLocalEntities();
            // **************************************************************

            if (lista == null) {
                statusResul = Response.Status.NO_CONTENT;
                response = Response
                        .status(statusResul)
                        .build();
            } else {
                statusResul = Response.Status.OK;
                response = Response
                        .status(statusResul)
                        .entity(lista)
                        .build();
            }
        } catch (Exception ex) {
            statusResul = Response.Status.BAD_REQUEST;
            mensaje.put("mensaje", "Error al procesar la petición");
            response = Response
                    .status(statusResul)
                    .entity(mensaje)
                    .build();
        }
        return response;
    }
    @GET
    @Path("{idLocal}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOneController(@PathParam("idLocal") Long id) {
        HashMap<String, String> mensaje = new HashMap<>();
        Response response;
        Response.Status statusResul;
        Local local;
        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT)) {

            // **************************************************************
            // Obtener datos con CONTROLLER
            LocalJpaController dao = new LocalJpaController(emf);
            local = dao.findLocal(id);
            // **************************************************************

            if (local == null) {
                statusResul = Response.Status.NOT_FOUND;
                mensaje.put("mensaje", "No existe local con id " + id);
                response = Response
                        .status(statusResul)
                        .entity(mensaje)
                        .build();
            } else {
                statusResul = Response.Status.OK;
                response = Response
                        .status(statusResul)
                        .entity(local)
                        .build();
            }
        } catch (Exception ex) {
            statusResul = Response.Status.BAD_REQUEST;
            mensaje.put("mensaje", "Error al procesar la petición");
            response = Response
                    .status(statusResul)
                    .entity(mensaje)
                    .build();
        }
        return response;
    }
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response post(Local local) {
        HashMap<String, String> mensaje = new HashMap<>();
        Response response;
        Response.Status statusResul;
        try (EntityManagerFactory emf
                = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);) {
            LocalJpaController dao = new LocalJpaController(emf);
            Local localFound = null;
            if ( (local.getIdLocal() != null) ) {
                localFound = dao.findLocal(local.getIdLocal());
            }
            if (localFound != null) {
                statusResul = Response.Status.FOUND;
                mensaje.put("mensaje", "Ya existe local con id " + local.getIdLocal());
                response = Response
                        .status(statusResul)
                        .entity(mensaje)
                        .build();
            } else {
                dao.create(local);
                statusResul = Response.Status.CREATED;
                mensaje.put("mensaje", "Local " + local.getNombre() + " grabado");
                response = Response
                        .status(statusResul)
                        .entity(mensaje)
                        .build();
            }
        } catch (Exception ex) {
            statusResul = Response.Status.BAD_REQUEST;
            mensaje.put("mensaje", "Error al procesar la petición");
            response = Response
                    .status(statusResul)
                    .entity(mensaje)
                    .build();
        }
        return response;
    }
    @DELETE
    @Path("{idLocal}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("idLocal") Long id) {
        HashMap<String, String> mensaje = new HashMap<>();
        Response response;
        Response.Status statusResul;
        try (EntityManagerFactory emf
                = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);) {
            LocalJpaController dao = new LocalJpaController(emf);
            Local localFound = dao.findLocal(id);
            if (localFound == null) {
                statusResul = Response.Status.NOT_FOUND;
                mensaje.put("mensaje", "No existe local con id " + id);
                response = Response
                        .status(statusResul)
                        .entity(mensaje)
                        .build();
            } else {
                dao.destroy(id);
                statusResul = Response.Status.OK;
                mensaje.put("mensaje", "Local con id " + id + " eliminado");
                response = Response
                        .status(statusResul)
                        .entity(mensaje)
                        .build();
            }
        } catch (Exception ex) {
            statusResul = Response.Status.BAD_REQUEST;
            mensaje.put("mensaje", "Error al procesar la petición");
            response = Response
                    .status(statusResul)
                    .entity(mensaje)
                    .build();
        }
        return response;
    }
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response put(Local local) {
        HashMap<String, String> mensaje = new HashMap<>();
        Response response;
        Response.Status statusResul;
        try (EntityManagerFactory emf
                = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);) {
            LocalJpaController dao = new LocalJpaController(emf);
            Local localFound = dao.findLocal(local.getIdLocal());
            if (localFound == null) {
                statusResul = Response.Status.NOT_FOUND;
                mensaje.put("mensaje", "No existe local con id " + local.getIdLocal());
                response = Response
                        .status(statusResul)
                        .entity(mensaje)
                        .build();
            } else {
                // Actualizar campos del objeto encontrado
                localFound.setNombre(local.getNombre());
                localFound.setDireccion(local.getDireccion());

                // Grabar los cambios
                dao.edit(localFound);
                statusResul = Response.Status.OK;
                mensaje.put("mensaje", "local con id " + local.getIdLocal() + " actualizado");
                response = Response
                        .status(statusResul)
                        .entity(mensaje)
                        .build();
            }
        } catch (Exception ex) {
            statusResul = Response.Status.BAD_REQUEST;
            mensaje.put("mensaje", "Error al procesar la petición");
            response = Response
                    .status(statusResul)
                    .entity(mensaje)
                    .build();
        }
        return response;
    }
}
