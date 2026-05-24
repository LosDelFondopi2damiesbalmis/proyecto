package serpelupos;


import auth.JwtUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.HashMap;
import peluposbd.Usuario;
import peluposbd.UsuarioJpaController;

@Path("auth")
public class ServiceRESTAuth {

    private static final String PERSISTENCE_UNIT = "ApiRestPeluPosPU";
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(Usuario usuario, @Context ContainerRequestContext requestContext) {
        
        HashMap<String, String> mensaje = new HashMap<>();
        Response.Status statusResul;
        Usuario usuarioEncontrado;
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            
            // 1. Buscamos al usuario por su NOMBRE (usuario.getUsuario()), no por su ID
            try {
                usuarioEncontrado = em.createQuery("SELECT u FROM Usuario u WHERE u.usuario = :nombre", Usuario.class)
                                      .setParameter("nombre", usuario.getUsuario())
                                      .getSingleResult();
            } catch (NoResultException e) {
                usuarioEncontrado = null; // Si no encuentra a "pedro", lo dejamos a null
            }

            // 2. Comprobamos si existe
            if (usuarioEncontrado == null) {
                statusResul = Response.Status.UNAUTHORIZED;
                mensaje.put("mensaje", "No existe el usuario " + usuario.getUsuario()); // Cambiado a getUsuario()
                
            // 3. Comprobamos la contraseña
            } else if (usuario.getContrasena() == null
                    || !usuario.getContrasena().equals(usuarioEncontrado.getContrasena())) {
                statusResul = Response.Status.UNAUTHORIZED;
                mensaje.put("mensaje", "Contraseña no válida para " + usuario.getUsuario()); // Cambiado a getUsuario()
                
            // 4. ¡Todo correcto! Generamos el Token
            } else {
                String token = JwtUtil.generarToken(usuarioEncontrado.getIdUsuario().toString(), usuarioEncontrado.getRolUsuario().name());
                mensaje.put("jwtToken", token);
                mensaje.put("idUsuario", usuarioEncontrado.getIdUsuario().toString());
                mensaje.put("usuario", usuarioEncontrado.getUsuario());
                mensaje.put("rolUsuario", usuarioEncontrado.getRolUsuario().name());

                if (usuarioEncontrado.getIdEmpleado() != null) {
                    mensaje.put("idEmpleado", usuarioEncontrado.getIdEmpleado().getIdEmpleado().toString());
                }
                statusResul = Response.Status.OK;
            }
        } catch (Exception ex) {
            statusResul = Response.Status.BAD_REQUEST;
            mensaje.put("mensaje", "Error al procesar la petición " + ex.getLocalizedMessage());
        }finally {
            // 🚀 3. CERRAMOS LA CONEXIÓN SIEMPRE, PASE LO QUE PASE
            if (em != null && em.isOpen()) {
                em.close();
            }
        }

        Response response = Response
                .status(statusResul)
                .entity(mensaje)
                .build();
        return response;

    }

    @POST
    @Path("/logout")
    @Produces(MediaType.APPLICATION_JSON)
    public Response logout(@Context ContainerRequestContext requestContext) {
        HashMap<String, String> mensaje = new HashMap<>();
        Response.Status statusResul;

        try {
            // Obtener el token del header Authorization
            String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String token = authorizationHeader.substring("Bearer".length()).trim();

                // Invalidar el token
                JwtUtil.invalidarToken(token);

                statusResul = Response.Status.OK;
                mensaje.put("mensaje", "Sesión cerrada correctamente");
            } else {
                statusResul = Response.Status.BAD_REQUEST;
                mensaje.put("mensaje", "Token no proporcionado");
            }
        } catch (Exception ex) {
            statusResul = Response.Status.BAD_REQUEST;
            mensaje.put("mensaje", "Error al cerrar sesión: " + ex.getLocalizedMessage());
        }

        return Response
                .status(statusResul)
                .entity(mensaje)
                .build();
    }

}
