
package serpelupos;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import jakarta.persistence.TemporalType;
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
import jakarta.ws.rs.core.Response.Status;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.json.JSONObject;
import peluposbd.Empleado;
import peluposbd.EmpleadoJpaController;



@Path("empleados")
public class ServiceRestEmpleados {
    
    private static final String PERSISTENCE_UNIT = "ApiRestPeluPosPU";
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllController() {
        HashMap<String, String> mensaje = new HashMap<>();
        Response response;
        Response.Status statusResul;
        List<Empleado> lista;
        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT)) {

            // **************************************************************
            // Obtener datos con CONTROLLER
            EmpleadoJpaController dao = new EmpleadoJpaController(emf);
            lista = dao.findEmpleadoEntities();
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
    @Path("/ventas/{idEmpleado}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getResumenVentasEmpleado(@PathParam("idEmpleado") Long idEmpleado) {
        HashMap<String, String> mensaje = new HashMap<>();
        Response response;
        Status statusResul;
        String resultado = "{}";

        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT)) {
            
            EntityManager em = emf.createEntityManager();

            // 1. Calculamos el primer y último día del mes actual
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.DAY_OF_MONTH, 1);
            Date inicioMes = cal.getTime();
            cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
            Date finMes = cal.getTime();

            // 2. Consulta 1: Total Facturado
            Query qTotal = em.createQuery("SELECT COALESCE(SUM(f.monto), 0.0) FROM Factura f WHERE f.idEmpleado.idEmpleado = :idEmpleado AND f.fecha >= :inicio AND f.fecha <= :fin");
            qTotal.setParameter("idEmpleado", idEmpleado);
            qTotal.setParameter("inicio", inicioMes, TemporalType.DATE);
            qTotal.setParameter("fin", finMes, TemporalType.DATE);
            Number resultadoSuma = (Number) qTotal.getSingleResult();
            Double totalFacturado = resultadoSuma.doubleValue();

            // 3. Consulta 2: Productos Vendidos
            Query qProductos = em.createQuery("SELECT COUNT(p) FROM Factura f JOIN f.facturaProductoCollection p WHERE f.idEmpleado.idEmpleado = :idEmpleado AND f.fecha >= :inicio AND f.fecha <= :fin");
            qProductos.setParameter("idEmpleado", idEmpleado);
            qProductos.setParameter("inicio", inicioMes, TemporalType.DATE);
            qProductos.setParameter("fin", finMes, TemporalType.DATE);
            Long productosVendidos = (Long) qProductos.getSingleResult();

            // 4. Consulta 3: Servicios Realizados
            Query qServicios = em.createQuery("SELECT COUNT(s) FROM Factura f JOIN f.facturaServicioCollection s WHERE f.idEmpleado.idEmpleado = :idEmpleado AND f.fecha >= :inicio AND f.fecha <= :fin");
            qServicios.setParameter("idEmpleado", idEmpleado);
            qServicios.setParameter("inicio", inicioMes, TemporalType.DATE);
            qServicios.setParameter("fin", finMes, TemporalType.DATE);
            Long serviciosRealizados = (Long) qServicios.getSingleResult();

            // 5. Construimos el JSON (Como es un solo empleado, es un JSONObject)
            JSONObject json = new JSONObject();
            json.put("totalFacturado", totalFacturado);
            json.put("productosVendidos", productosVendidos);
            json.put("serviciosRealizados", serviciosRealizados);

            resultado = json.toString();
            statusResul = Response.Status.OK;
            response = Response
                    .status(statusResul)
                    .entity(resultado)
                    .build();

        } catch (Exception ex) {
            statusResul = Response.Status.BAD_REQUEST;
            mensaje.put("mensaje", "Error al procesar la petición: " + ex.getMessage());
            JSONObject jsonError = new JSONObject(mensaje); 
            response = Response
                    .status(statusResul)
                    .entity(jsonError.toString())
                    .build();
        } 
        return response;
    }
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response post(Empleado empleado) {
        HashMap<String, String> mensaje = new HashMap<>();
        Response response;
        Response.Status statusResul;
        try (EntityManagerFactory emf
                = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);) {
            EmpleadoJpaController dao = new EmpleadoJpaController(emf);
            Empleado empleadoFound = null;
            if ( (empleado.getIdEmpleado() != null) ) {
                empleadoFound = dao.findEmpleado(empleado.getIdEmpleado());
            }
            if (empleadoFound != null) {
                statusResul = Response.Status.FOUND;
                mensaje.put("mensaje", "Ya existe empleado con id " + empleado.getIdEmpleado());
                response = Response
                        .status(statusResul)
                        .entity(mensaje)
                        .build();
            } else {
                dao.create(empleado);
                statusResul = Response.Status.CREATED;
                mensaje.put("mensaje", "Empleado " + empleado.getNombre() + " grabado");
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
    public Response put(Empleado empleado) {
        HashMap<String, String> mensaje = new HashMap<>();
        Response response;
        Response.Status statusResul;
        try (EntityManagerFactory emf
                = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);) {
            EmpleadoJpaController dao = new EmpleadoJpaController(emf);
            Empleado empleadoFound = dao.findEmpleado(empleado.getIdEmpleado());
            if (empleadoFound == null) {
                statusResul = Response.Status.NOT_FOUND;
                mensaje.put("mensaje", "No existe empleado con id " + empleado.getIdEmpleado());
                response = Response
                        .status(statusResul)
                        .entity(mensaje)
                        .build();
            } else {
                // Actualizar campos del objeto encontrado
                empleadoFound.setCargo(empleado.getCargo());
                empleadoFound.setNombre(empleado.getNombre());
                empleadoFound.setEmail(empleado.getEmail());
                empleadoFound.setFacturaCollection(empleado.getFacturaCollection());

                // Grabar los cambios
                dao.edit(empleadoFound);
                statusResul = Response.Status.OK;
                mensaje.put("mensaje", "empleado con id " + empleado.getIdEmpleado() + " actualizado");
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
    @Path("{idEmpleado}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("idEmpleado") Long id) {
        HashMap<String, String> mensaje = new HashMap<>();
        Response response;
        Response.Status statusResul;
        try (EntityManagerFactory emf
                = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);) {
            EmpleadoJpaController dao = new EmpleadoJpaController(emf);
            Empleado empleadoFound = dao.findEmpleado(id);
            if (empleadoFound == null) {
                statusResul = Response.Status.NOT_FOUND;
                mensaje.put("mensaje", "No existe empleado con id " + id);
                response = Response
                        .status(statusResul)
                        .entity(mensaje)
                        .build();
            } else {
                dao.destroy(id);
                statusResul = Response.Status.OK;
                mensaje.put("mensaje", "Empleado con id " + id + " eliminado");
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
