package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.servlet;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.LogRecord;

@WebFilter("/*") // Este filtro se aplicará a todas las rutas de la aplicación
public class CORSFilter implements Filter {


    public void init(FilterConfig filterConfig) throws ServletException {
        // Inicialización del filtro (si es necesario)
    }


    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse httpResp = (HttpServletResponse) response;

        // Permitir solicitudes CORS de tu frontend
       httpResp.setHeader("Access-Control-Allow-Origin", "*");  // Permitir cualquier origenCambia esto según el origen de tu frontend
        httpResp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        httpResp.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");

        // Si la solicitud es OPTIONS, responde con un 200 OK
        if ("OPTIONS".equalsIgnoreCase(((HttpServletRequest) request).getMethod())) {
            httpResp.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        // Continuar con la cadena de filtros
        chain.doFilter(request, response);
    }



    public boolean isLoggable(LogRecord record) {
        return false;
    }
}
