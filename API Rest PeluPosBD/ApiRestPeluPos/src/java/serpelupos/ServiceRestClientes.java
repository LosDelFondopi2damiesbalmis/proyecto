
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
import peluposbd.Cliente;
import peluposbd.ClienteJpaController;

@Path("clientes")
public class ServiceRestClientes {
    private static final String PERSISTENCE_UNIT = "ApiRestPeluPosPU";
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllController() {
        HashMap<String, String> mensaje = new HashMap<>();
        Response response;
        Response.Status statusResul;
        List<Cliente> lista;
        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT)) {

            // **************************************************************
            // Obtener datos con CONTROLLER
            ClienteJpaController dao = new ClienteJpaController(emf);
            lista = dao.findClienteEntities();
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
    @Path("{idCliente}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOneController(@PathParam("idCliente") Long id) {
        HashMap<String, String> mensaje = new HashMap<>();
        Response response;
        Response.Status statusResul;
        Cliente cliente;
        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT)) {

            // **************************************************************
            // Obtener datos con CONTROLLER
            ClienteJpaController dao = new ClienteJpaController(emf);
            cliente = dao.findCliente(id);
            // **************************************************************

            if (cliente == null) {
                statusResul = Response.Status.NOT_FOUND;
                mensaje.put("mensaje", "No existe cliente con id " + id);
                response = Response
                        .status(statusResul)
                        .entity(mensaje)
                        .build();
            } else {
                statusResul = Response.Status.OK;
                response = Response
                        .status(statusResul)
                        .entity(cliente)
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
    public Response post(Cliente cliente) {
        HashMap<String, String> mensaje = new HashMap<>();
        Response response;
        Response.Status statusResul;
        try (EntityManagerFactory emf
                = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);) {
            ClienteJpaController dao = new ClienteJpaController(emf);
            Cliente clienteFound = null;
            if ( (cliente.getIdCliente() != null) ) {
                clienteFound = dao.findCliente(cliente.getIdCliente());
            }
            if (clienteFound != null) {
                statusResul = Response.Status.FOUND;
                mensaje.put("mensaje", "Ya existe cliente con id " + cliente.getIdCliente());
                response = Response
                        .status(statusResul)
                        .entity(mensaje)
                        .build();
            } else {
                dao.create(cliente);
                statusResul = Response.Status.CREATED;
                mensaje.put("mensaje", "Cliente " + cliente.getNombre() + " grabado");
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
    public Response put(Cliente cliente) {
        HashMap<String, String> mensaje = new HashMap<>();
        Response response;
        Response.Status statusResul;
        try (EntityManagerFactory emf
                = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);) {
            ClienteJpaController dao = new ClienteJpaController(emf);
            Cliente clienteFound = dao.findCliente(cliente.getIdCliente());
            if (clienteFound == null) {
                statusResul = Response.Status.NOT_FOUND;
                mensaje.put("mensaje", "No existe cliente con id " + clienteFound.getIdCliente());
                response = Response
                        .status(statusResul)
                        .entity(mensaje)
                        .build();
            } else {
                // Actualizar campos del objeto encontrado
                clienteFound.setNombre(cliente.getNombre());
                clienteFound.setDeuda(cliente.getDeuda());
                clienteFound.setTelefono(cliente.getTelefono());
                clienteFound.setFacturaCollection(cliente.getFacturaCollection());

                // Grabar los cambios
                dao.edit(clienteFound);
                statusResul = Response.Status.OK;
                mensaje.put("mensaje", "Cliente con id " + cliente.getIdCliente() + " actualizado");
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
    @Path("{idCliente}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("idCliente") Long id) {
        HashMap<String, String> mensaje = new HashMap<>();
        Response response;
        Response.Status statusResul;
        try (EntityManagerFactory emf
                = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);) {
            ClienteJpaController dao = new ClienteJpaController(emf);
            Cliente clienteFound = dao.findCliente(id);
            if (clienteFound== null) {
                statusResul = Response.Status.NOT_FOUND;
                mensaje.put("mensaje", "No existe cliente con id " + id);
                response = Response
                        .status(statusResul)
                        .entity(mensaje)
                        .build();
            } else {
                dao.destroy(id);
                statusResul = Response.Status.OK;
                mensaje.put("mensaje", "Cliente con id " + id + " eliminado");
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
