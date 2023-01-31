package nl.aim.regterschotracing;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.ext.Provider;

import java.util.List;

/**
 * Supports secure cross-origin requests and data transfers between browsers and servers.
 */
@Provider
public class CORSFilter implements ContainerResponseFilter {


  /**
   * Creates the CORS filter.
   *
   * @param requestContext Request for the CORS filter.
   * @param responseContext Response for the CORS filter.
   */
  public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) {
    MultivaluedMap<String, Object> headers = responseContext.getHeaders();
    headers.add("Access-Control-Allow-Origin", "*");
    headers.add("Access-Control-Allow-Headers", this.getRequestedHeaders(requestContext));
    headers.add("Access-Control-Allow-Credentials", "true");
    headers.add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
    headers.add("Access-Control-Max-Age", 151200);
    headers.add("x-responded-by", "cors-response-filter");
  }

  String getRequestedHeaders(ContainerRequestContext responseContext) {
    List<String> headers = (List) responseContext.getHeaders().get("Access-Control-Request-Headers");
    return this.createHeaderList(headers);
  }

  /**
   * Creates the CORS header.
   *
   * @param headers List with headers to put in the CORS header.
   * @return A list of CORS header.
   */
  String createHeaderList(List<String> headers) {
    if (headers != null && !headers.isEmpty()) {
      StringBuilder retVal = new StringBuilder();

      for (String s : headers) {
        String header = s;
        retVal.append(header);
        retVal.append(',');
      }

      retVal.append("origin,accept,content-type");
      return retVal.toString();
    } else {
      return "origin,accept,content-type";
    }
  }
}
