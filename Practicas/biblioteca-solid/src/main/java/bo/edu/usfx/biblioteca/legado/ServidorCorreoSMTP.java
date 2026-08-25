package bo.edu.usfx.biblioteca.legado;

import java.util.ArrayList;
import java.util.List;

/** Simulacion de un servidor SMTP. Registra los correos en memoria. */
public class ServidorCorreoSMTP {

    private final String host;
    private final int puerto;
    private final List<String> enviados = new ArrayList<>();

    public ServidorCorreoSMTP(String host, int puerto) {
        this.host = host;
        this.puerto = puerto;
    }

    public void enviar(String destinatario, String asunto, String cuerpo) {
        enviados.add(destinatario + "|" + asunto);
        System.out.println("[SMTP " + host + ":" + puerto + "] -> " + destinatario + " :: " + asunto);
    }

    public List<String> getEnviados() { return enviados; }
}
