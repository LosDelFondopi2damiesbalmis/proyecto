package auth;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.HashMap;

@Provider
public class AuthFilter implements ContainerRequestFilter {

    @Context
    private UriInfo uriInfo;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String path = requestContext.getUriInfo().getPath();
        String method = requestContext.getMethod();

        // Permitir acceso sin autenticación a los endpoints de auth
        if (path.startsWith("auth")) {
            return;
        }

        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        HashMap<String, String> mensaje = new HashMap<>();
        String urlLogin = uriInfo.getBaseUriBuilder()
                .path(serpelupos.ServiceRESTAuth.class)
                .path(serpelupos.ServiceRESTAuth.class, "login")
                .toString();

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")
                && !authorizationHeader.equals("Bearer null")) {
            // Extraer el token
            String token = authorizationHeader.substring("Bearer".length()).trim();

            try {
                String idUsuario = JwtUtil.getIdUsuarioFromToken(token);

                if (idUsuario == null) {
                    mensaje.put("mensaje", "La token ha expirado debes volver a identificarte con un POST a " + urlLogin);
                    requestContext.abortWith(Response.status(Status.UNAUTHORIZED)
                            .entity(mensaje)
                            .build());
                } else {
                    // Si el token es válido, puedes agregar el usuario al contexto                    
                    requestContext.setProperty("idUsuario", idUsuario);

                    String rol = JwtUtil.getRolFromToken(token);
                    requestContext.setProperty("rol", rol);

                if (path.startsWith("locales")
                            && ("POST".equalsIgnoreCase(method) || "PUT".equalsIgnoreCase(method) || "DELETE".equalsIgnoreCase(method))
                            && (rol == null || !rol.equalsIgnoreCase("administrador"))) {

                        mensaje.put("mensaje", "Acceso denegado: solo los Administradores pueden modificar " + path);
                        requestContext.abortWith(Response.status(Response.Status.FORBIDDEN)
                                .entity(mensaje)
                                .build());
                        return; // Importante para que no siga leyendo el código hacia abajo
                    }


                    if ((path.startsWith("usuarios") || path.startsWith("empleados") || path.startsWith("clientes"))
                            && ("POST".equalsIgnoreCase(method) || "PUT".equalsIgnoreCase(method) || "DELETE".equalsIgnoreCase(method))
                            && (rol == null || (!rol.equalsIgnoreCase("administrador") && !rol.equalsIgnoreCase("manager")))) {

                        mensaje.put("mensaje", "Acceso denegado: necesitas permisos de Manager o Admin para modificar " + path);
                        requestContext.abortWith(Response.status(Response.Status.FORBIDDEN)
                                .entity(mensaje)
                                .build());
                        return;
                    }


                    if ((path.startsWith("productos") || path.startsWith("servicios") || path.startsWith("facturas"))
                            && ("POST".equalsIgnoreCase(method) || "PUT".equalsIgnoreCase(method) || "DELETE".equalsIgnoreCase(method))
                            && (rol == null || (!rol.equalsIgnoreCase("administrador") && !rol.equalsIgnoreCase("manager") && !rol.equalsIgnoreCase("empleado")))) {

                        mensaje.put("mensaje", "Acceso denegado: rol no reconocido para modificar " + path);
                        requestContext.abortWith(Response.status(Response.Status.FORBIDDEN)
                                .entity(mensaje)
                                .build());
                        return;
                    }
                }
            } catch (Exception e) {
                mensaje.put("mensaje", "Token incorrecto");
                requestContext.abortWith(Response.status(Status.UNAUTHORIZED)
                        .entity(mensaje)
                        .build());
            }
        } else {
            mensaje.put("mensaje", "Debes identificarte con un POST a " + urlLogin);
            requestContext.abortWith(Response.status(Status.UNAUTHORIZED)
                    .entity(mensaje)
                    .build());
        }
    }
}
