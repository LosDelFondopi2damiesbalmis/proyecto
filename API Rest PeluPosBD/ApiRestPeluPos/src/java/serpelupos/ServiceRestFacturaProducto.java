/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package serpelupos;

import java.util.HashMap;
import java.util.List;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import peluposbd.FacturaProducto;
import peluposbd.FacturaProductoJpaController;
import peluposbd.FacturaProductoPK;
import peluposbd.Factura;
import peluposbd.Producto;
@Path("facturaproductos")
public class ServiceRestFacturaProducto {

    private static final String PERSISTENCE_UNIT = "ApiRestPeluPosPU";

    // 1. GET ALL (Todos)
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllController() {
        HashMap<String, String> mensaje = new HashMap<>();
        Response response;
        Response.Status statusResul;
        List<FacturaProducto> lista;
        
        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT)) {
            FacturaProductoJpaController dao = new FacturaProductoJpaController(emf);
            lista = dao.findFacturaProductoEntities();

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
    @Path("{idFactura}/{idProducto}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOneController(@PathParam("idFactura") Long idFactura, @PathParam("idProducto") Long idProducto) {
        HashMap<String, String> mensaje = new HashMap<>();
        Response response;
        Response.Status statusResul;
        FacturaProducto facturaProducto;
        
        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT)) {
            FacturaProductoJpaController dao = new FacturaProductoJpaController(emf);
            
            // Creamos la clave compuesta para buscar
            FacturaProductoPK pk = new FacturaProductoPK(idFactura, idProducto);
            facturaProducto = dao.findFacturaProducto(pk);

            if (facturaProducto == null) {
                statusResul = Response.Status.NOT_FOUND;
                mensaje.put("mensaje", "No existe registro para factura " + idFactura + " y producto " + idProducto);
                response = Response.status(statusResul).entity(mensaje).build();
            } else {
                statusResul = Response.Status.OK;
                response = Response.status(statusResul).entity(facturaProducto).build();
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
    public Response post(FacturaProducto facturaProducto) {
        HashMap<String, String> mensaje = new HashMap<>();
        Response response;
        Response.Status statusResul;
        
        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT)) {
            FacturaProductoJpaController dao = new FacturaProductoJpaController(emf);
            
            // Verificamos si ya existe usando su clave primaria compuesta
            FacturaProducto encontrado = null;
            if (facturaProducto.getFacturaProductoPK() != null) {
                encontrado = dao.findFacturaProducto(facturaProducto.getFacturaProductoPK());
            }

            if (encontrado != null) {
                statusResul = Response.Status.FOUND;
                mensaje.put("mensaje", "Ya existe ese producto en esa factura");
                response = Response.status(statusResul).entity(mensaje).build();
            } else {
                if (facturaProducto.getFacturaProductoPK() != null) {
                Factura f = new Factura();
                f.setIdFactura(facturaProducto.getFacturaProductoPK().getIdFactura());
                facturaProducto.setFactura(f);

                Producto p = new Producto();
                p.setIdProducto(facturaProducto.getFacturaProductoPK().getIdProducto());
                facturaProducto.setProducto(p);
            }
                dao.create(facturaProducto);
                statusResul = Response.Status.CREATED;
                mensaje.put("mensaje", "Producto asignado a la factura correctamente");
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
    @Path("{idFactura}/{idProducto}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("idFactura") Long idFactura, @PathParam("idProducto") Long idProducto) {
        HashMap<String, String> mensaje = new HashMap<>();
        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT)) {
            FacturaProductoJpaController dao = new FacturaProductoJpaController(emf);
            
            // Creamos la clave compuesta para localizar la fila exacta
            FacturaProductoPK pk = new FacturaProductoPK(idFactura, idProducto);
            
            if (dao.findFacturaProducto(pk) == null) {
                mensaje.put("mensaje", "No existe registro para factura " + idFactura + " y producto " + idProducto);
                return Response.status(Response.Status.NOT_FOUND).entity(mensaje).build();
            }
            
            dao.destroy(pk); // Borramos usando la clave compuesta
            mensaje.put("mensaje", "Relación producto-factura eliminada");
            return Response.status(Response.Status.OK).entity(mensaje).build();
            
        } catch (Exception ex) {
            mensaje.put("mensaje", "Error al eliminar: " + ex.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(mensaje).build();
        }
    }
}
