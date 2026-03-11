
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
import peluposbd.Servicio;
import peluposbd.ServicioJpaController;

@Path("servicios")
public class ServiceRestServicios {
    private static final String PERSISTENCE_UNIT = "ApiRestPeluPosPU";
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllController() {
        HashMap<String, String> mensaje = new HashMap<>();
        Response response;
        Response.Status statusResul;
        List<Servicio> lista;
        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT)) {

            // **************************************************************
            // Obtener datos con CONTROLLER
            ServicioJpaController dao = new ServicioJpaController(emf);
            lista = dao.findServicioEntities();
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
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response post(Servicio servicio) {
        HashMap<String, String> mensaje = new HashMap<>();
        Response response;
        Response.Status statusResul;
        try (EntityManagerFactory emf
                = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);) {
            ServicioJpaController dao = new ServicioJpaController(emf);
            Servicio servicioFound = null;
            if ((servicio.getIdServicio() != null)) {
                servicioFound = dao.findServicio(servicio.getIdServicio());
            }
            if (servicioFound != null) {
                statusResul = Response.Status.FOUND;
                mensaje.put("mensaje", "Ya existe servicio con id " + servicio.getIdServicio());
                response = Response
                        .status(statusResul)
                        .entity(mensaje)
                        .build();
            } else {
                dao.create(servicio);
                statusResul = Response.Status.CREATED;
                mensaje.put("mensaje", "Servicio " + servicio.getNombre() + " grabado");
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
    public Response put(Servicio servicio) {
        HashMap<String, String> mensaje = new HashMap<>();
        Response response;
        Response.Status statusResul;
        try (EntityManagerFactory emf
                = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);) {
            ServicioJpaController dao = new ServicioJpaController(emf);
            Servicio servicioFound = dao.findServicio(servicio.getIdServicio());
            if (servicioFound == null) {
                statusResul = Response.Status.NOT_FOUND;
                mensaje.put("mensaje", "No existe servicio con id " + servicio.getIdServicio());
                response = Response
                        .status(statusResul)
                        .entity(mensaje)
                        .build();
            } else {
                // Actualizar campos del objeto encontrado
                servicioFound.setNombre(servicio.getNombre());
                servicioFound.setDescripcion(servicio.getDescripcion());
                servicioFound.setPrecio(servicio.getPrecio());
                servicioFound.setFacturaServicioCollection(servicio.getFacturaServicioCollection());
                servicioFound.setIdEmpleado(servicio.getIdEmpleado());

                // Grabar los cambios
                dao.edit(servicioFound);
                statusResul = Response.Status.OK;
                mensaje.put("mensaje", "servicio con id " + servicio.getIdServicio() + " actualizado");
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
    @Path("{idServicio}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("idServicio") Long id) {
        HashMap<String, String> mensaje = new HashMap<>();
        Response response;
        Response.Status statusResul;
        try (EntityManagerFactory emf
                = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);) {
            ServicioJpaController dao = new ServicioJpaController(emf);
            Servicio servicioFound = dao.findServicio(id);
            if (servicioFound == null) {
                statusResul = Response.Status.NOT_FOUND;
                mensaje.put("mensaje", "No existe servicio con id " + id);
                response = Response
                        .status(statusResul)
                        .entity(mensaje)
                        .build();
            } else {
                dao.destroy(id);
                statusResul = Response.Status.OK;
                mensaje.put("mensaje", "Servicio con id " + id + " eliminado");
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
