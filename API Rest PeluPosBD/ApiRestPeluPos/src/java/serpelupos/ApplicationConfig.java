package serpelupos;
import org.glassfish.jersey.server.ResourceConfig;
import jakarta.ws.rs.ApplicationPath;

@ApplicationPath("servicio")
public class ApplicationConfig extends ResourceConfig {

    public ApplicationConfig() {
        // 1. Escanea automáticamente todas las clases en el paquete "serpelupos"
        packages("serpelupos");
        
        register(auth.AuthFilter.class);
    }
}