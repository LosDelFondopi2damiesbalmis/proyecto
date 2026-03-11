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
import peluposbd.Factura;
import peluposbd.FacturaJpaController;

@Path("facturas")
public class ServiceRestFacturas {
    private static final String PERSISTENCE_UNIT = "ApiRestPeluPosPU";
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllController() {
        HashMap<String, String> mensaje = new HashMap<>();
        Response response;
        Response.Status statusResul;
        List<Factura> lista;
        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT)) {

            // **************************************************************
            // Obtener datos con CONTROLLER
            FacturaJpaController dao = new FacturaJpaController(emf);
            lista = dao.findFacturaEntities();
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
    @Path("{idFactura}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOneController(@PathParam("idFactura") Long id) {
        HashMap<String, String> mensaje = new HashMap<>();
        Response response;
        Response.Status statusResul;
        Factura factura;
        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT)) {

            // **************************************************************
            // Obtener datos con CONTROLLER
            FacturaJpaController dao = new FacturaJpaController(emf);
            factura = dao.findFactura(id);
            // **************************************************************

            if (factura == null) {
                statusResul = Response.Status.NOT_FOUND;
                mensaje.put("mensaje", "No existe factura con id " + id);
                response = Response
                        .status(statusResul)
                        .entity(mensaje)
                        .build();
            } else {
                statusResul = Response.Status.OK;
                response = Response
                        .status(statusResul)
                        .entity(factura)
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
    public Response post(Factura factura) {
        HashMap<String, String> mensaje = new HashMap<>();
        Response response;
        Response.Status statusResul;
        try (EntityManagerFactory emf
                = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);) {
            FacturaJpaController dao = new FacturaJpaController(emf);
            Factura facturaFound = null;
            if ( (factura.getIdFactura() != null) ) {
                facturaFound = dao.findFactura(factura.getIdFactura());
            }
            if (facturaFound != null) {
                statusResul = Response.Status.FOUND;
                mensaje.put("mensaje", "Ya existe factura con id " + factura.getIdFactura());
                response = Response
                        .status(statusResul)
                        .entity(mensaje)
                        .build();
            } else {
                dao.create(factura);
                statusResul = Response.Status.CREATED;
                mensaje.put("mensaje", "Factura " + factura.getIdFactura() + " grabada");
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
    public Response put(Factura factura) {
        HashMap<String, String> mensaje = new HashMap<>();
        Response response;
        Response.Status statusResul;
        try (EntityManagerFactory emf
                = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);) {
            FacturaJpaController dao = new FacturaJpaController(emf);
            Factura facturaFound = dao.findFactura(factura.getIdFactura());
            if (facturaFound == null) {
                statusResul = Response.Status.NOT_FOUND;
                mensaje.put("mensaje", "No existe factura con id " + facturaFound.getIdFactura());
                response = Response
                        .status(statusResul)
                        .entity(mensaje)
                        .build();
            } else {
                // Actualizar campos del objeto encontrado
                facturaFound.setFecha(factura.getFecha());
                facturaFound.setIdCliente(factura.getIdCliente());
                facturaFound.setIdEmpleado(factura.getIdEmpleado());
                facturaFound.setPendiente(factura.getPendiente());
                facturaFound.setTipoPago(factura.getTipoPago());
                facturaFound.setMonto(factura.getMonto());
                facturaFound.setFacturaProductoCollection(factura.getFacturaProductoCollection());
                facturaFound.setFacturaServicioCollection(factura.getFacturaServicioCollection());

                // Grabar los cambios
                dao.edit(facturaFound);
                statusResul = Response.Status.OK;
                mensaje.put("mensaje", "Factura con id " + factura.getIdFactura() + " actualizado");
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
    @Path("{idFactura}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("idFactura") Long id) {
        HashMap<String, String> mensaje = new HashMap<>();
        Response response;
        Response.Status statusResul;
        try (EntityManagerFactory emf
                = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);) {
            FacturaJpaController dao = new FacturaJpaController(emf);
            Factura facturaFound = dao.findFactura(id);
            if (facturaFound == null) {
                statusResul = Response.Status.NOT_FOUND;
                mensaje.put("mensaje", "No existe factura con id " + id);
                response = Response
                        .status(statusResul)
                        .entity(mensaje)
                        .build();
            } else {
                dao.destroy(id);
                statusResul = Response.Status.OK;
                mensaje.put("mensaje", "Factura con id " + id + " eliminada");
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
