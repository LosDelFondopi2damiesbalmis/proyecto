/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package serpelupos;
import jakarta.ws.rs.*;
import java.util.HashMap;
import java.util.List;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import peluposbd.FacturaServicio;
import peluposbd.FacturaServicioJpaController;
import peluposbd.FacturaServicioPK;
import peluposbd.Factura;
import peluposbd.Servicio;
@Path("facturaservicios")
public class ServiceRestFacturaServicio {

    private static final String PERSISTENCE_UNIT = "ApiRestPeluPosPU";

    // 1. GET ALL (Todos)
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllController() {
        HashMap<String, String> mensaje = new HashMap<>();
        Response response;
        Response.Status statusResul;
        List<FacturaServicio> lista;
        
        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT)) {
            FacturaServicioJpaController dao = new FacturaServicioJpaController(emf);
            lista = dao.findFacturaServicioEntities();

            if (lista == null || lista.isEmpty()) {
                statusResul = Response.Status.NO_CONTENT;
                response = Response.status(statusResul).build();
            } else {
                statusResul = Response.Status.OK;
                response = Response.status(statusResul).entity(lista).build();
            }
        } catch (Exception ex) {
            statusResul = Response.Status.BAD_REQUEST;
            mensaje.put("mensaje", "Error al procesar la petición");
            response = Response.status(statusResul).entity(mensaje).build();
        }
        return response;
    }

    // 2. GET ONE (Uno solo) - ⚠️ Adaptado para Clave Compuesta
    @GET
    @Path("{idFactura}/{idServicio}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOneController(@PathParam("idFactura") Long idFactura, @PathParam("idServicio") Long idServicio) {
        HashMap<String, String> mensaje = new HashMap<>();
        Response response;
        Response.Status statusResul;
        FacturaServicio facturaServicio;
        
        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT)) {
            FacturaServicioJpaController dao = new FacturaServicioJpaController(emf);
            
            // Creamos la clave compuesta para buscar
            FacturaServicioPK pk = new FacturaServicioPK(idFactura, idServicio);
            facturaServicio = dao.findFacturaServicio(pk);

            if (facturaServicio == null) {
                statusResul = Response.Status.NOT_FOUND;
                mensaje.put("mensaje", "No existe registro para factura " + idFactura + " y servicio " + idServicio);
                response = Response.status(statusResul).entity(mensaje).build();
            } else {
                statusResul = Response.Status.OK;
                response = Response.status(statusResul).entity(facturaServicio).build();
            }
        } catch (Exception ex) {
            statusResul = Response.Status.BAD_REQUEST;
            mensaje.put("mensaje", "Error al procesar la petición");
            response = Response.status(statusResul).entity(mensaje).build();
        }
        return response;
    }

    // 3. POST (Crear)
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response post(FacturaServicio facturaServicio) {
        HashMap<String, String> mensaje = new HashMap<>();
        Response response;
        Response.Status statusResul;
        
        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT)) {
            FacturaServicioJpaController dao = new FacturaServicioJpaController(emf);
            
            // Verificamos si ya existe usando su clave primaria compuesta
            FacturaServicio encontrado = null;
            if (facturaServicio.getFacturaServicioPK() != null) {
                encontrado = dao.findFacturaServicio(facturaServicio.getFacturaServicioPK());
            }

            if (encontrado != null) {
                statusResul = Response.Status.FOUND;
                mensaje.put("mensaje", "Ya existe ese servicio en esa factura");
                response = Response.status(statusResul).entity(mensaje).build();
            } else {
                if (facturaServicio.getFacturaServicioPK() != null) {
                Factura f = new Factura();
                f.setIdFactura(facturaServicio.getFacturaServicioPK().getIdFactura());
                facturaServicio.setFactura(f);

                Servicio s = new Servicio();
                s.setIdServicio(facturaServicio.getFacturaServicioPK().getIdServicio());
                facturaServicio.setServicio(s);
            }
                dao.create(facturaServicio);
                statusResul = Response.Status.CREATED;
                mensaje.put("mensaje", "Servicio asignado a la factura correctamente");
                response = Response.status(statusResul).entity(mensaje).build();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            statusResul = Response.Status.INTERNAL_SERVER_ERROR;
            mensaje.put("mensaje", "Error real: " + ex.getMessage());
            response = Response.status(statusResul).entity(mensaje).build();
        }
        return response;
    }
    @DELETE
    @Path("{idFactura}/{idServicio}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("idFactura") Long idFactura, @PathParam("idServicio") Long idServicio) {
        HashMap<String, String> mensaje = new HashMap<>();
        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT)) {
            FacturaServicioJpaController dao = new FacturaServicioJpaController(emf);
            
            // Creamos la clave compuesta
            FacturaServicioPK pk = new FacturaServicioPK(idFactura, idServicio);
            
            if (dao.findFacturaServicio(pk) == null) {
                mensaje.put("mensaje", "No existe registro para factura " + idFactura + " y servicio " + idServicio);
                return Response.status(Response.Status.NOT_FOUND).entity(mensaje).build();
            }
            
            dao.destroy(pk); // Borramos usando la clave compuesta
            mensaje.put("mensaje", "Relación servicio-factura eliminada");
            return Response.status(Response.Status.OK).entity(mensaje).build();
            
        } catch (Exception ex) {
            mensaje.put("mensaje", "Error al eliminar: " + ex.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(mensaje).build();
        }
    }
}
