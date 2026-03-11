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
import peluposbd.Usuario;
import peluposbd.UsuarioJpaController;

@Path("usuarios")
public class ServiceRestUsuario {
    
    private static final String PERSISTENCE_UNIT = "ApiRestPeluPosPU";
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllController() {
        HashMap<String, String> mensaje = new HashMap<>();
        Response response;
        Response.Status statusResul;
        List<Usuario> lista;
        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT)) {

            // **************************************************************
            // Obtener datos con CONTROLLER
            UsuarioJpaController dao = new UsuarioJpaController(emf);
            lista = dao.findUsuarioEntities();
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
    public Response post(Usuario usuario) {
        HashMap<String, String> mensaje = new HashMap<>();
        Response response;
        Response.Status statusResul;
        try (EntityManagerFactory emf
                = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);) {
            UsuarioJpaController dao = new UsuarioJpaController(emf);
            Usuario usuarioFound = null;
            if ( (usuario.getIdUsuario() != null) ) {
                usuarioFound = dao.findUsuario(usuario.getIdUsuario());
            }
            if (usuarioFound != null) {
                statusResul = Response.Status.FOUND;
                mensaje.put("mensaje", "Ya existe usuario con id " + usuario.getIdUsuario());
                response = Response
                        .status(statusResul)
                        .entity(mensaje)
                        .build();
            } else {
                dao.create(usuario);
                statusResul = Response.Status.CREATED;
                mensaje.put("mensaje", "Usuario " + usuario.getUsuario() + " grabado");
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
    public Response put(Usuario usuario) {
        HashMap<String, String> mensaje = new HashMap<>();
        Response response;
        Response.Status statusResul;
        try (EntityManagerFactory emf
                = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);) {
            UsuarioJpaController dao = new UsuarioJpaController(emf);
            Usuario usuarioFound = dao.findUsuario(usuario.getIdUsuario());
            if (usuarioFound == null) {
                statusResul = Response.Status.NOT_FOUND;
                mensaje.put("mensaje", "No existe usuario con id " + usuario.getIdUsuario());
                response = Response
                        .status(statusResul)
                        .entity(mensaje)
                        .build();
            } else {
                // Actualizar campos del objeto encontrado
                usuarioFound.setUsuario(usuario.getUsuario());
                usuarioFound.setContrasena(usuario.getContrasena());
                usuarioFound.setIdEmpleado(usuario.getIdEmpleado());

                // Grabar los cambios
                dao.edit(usuarioFound);
                statusResul = Response.Status.OK;
                mensaje.put("mensaje", "Usuario con id " + usuario.getIdUsuario() + " actualizado");
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
    @Path("{idUsuario}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("idUsuario") Long id) {
        HashMap<String, String> mensaje = new HashMap<>();
        Response response;
        Response.Status statusResul;
        try (EntityManagerFactory emf
                = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);) {
            UsuarioJpaController dao = new UsuarioJpaController(emf);
            Usuario usuarioFound = dao.findUsuario(id);
            if (usuarioFound  == null) {
                statusResul = Response.Status.NOT_FOUND;
                mensaje.put("mensaje", "No existe usuario con id " + id);
                response = Response
                        .status(statusResul)
                        .entity(mensaje)
                        .build();
            } else {
                dao.destroy(id);
                statusResul = Response.Status.OK;
                mensaje.put("mensaje", "Usuario con id " + id + " eliminado");
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
