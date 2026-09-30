package pe.jllalle.gestortareasapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.net.URI;

@Component
public class SwaggerAutoOpenListener implements ApplicationListener<ApplicationReadyEvent> {

    @Value("${server.port:8080}")
    private String port;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (!Desktop.isDesktopSupported() || !Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            return;
        }

        try {
            String url = "http://localhost:" + port + "/swagger-ui/index.html";
            Desktop.getDesktop().browse(new URI(url));
        } catch (Exception e) {
            // No bloqueamos el arranque de la app si por algún motivo no se pudo abrir el navegador
        }
    }
}