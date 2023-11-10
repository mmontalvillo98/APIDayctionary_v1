package com.tfg.mariomh.v2.myApi.services.apis.implementations;

import com.tfg.mariomh.v2.myApi.exceptions.requests.RequestApiError;
import com.tfg.mariomh.v2.myApi.exceptions.responses.TooManyRequestsResponseException;
import com.tfg.mariomh.v2.myApi.services.apis.QueryParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public abstract class ExternalApiService {

    public final static String URL_PLACEHOLDER="{WORD}";
    public final static Integer TOO_MANY_REQUESTS_STATUS = 429;

    /**
     * Realiza una petición get a una url
     * @param urlText url en forma de String
     * @return String cadena con la respuesta
     * @throws RequestApiError
     */
    public String requestGet(String urlText) throws RequestApiError {
        return request("GET", createURL(urlText));
    }

    /**
     * Realiza una petición get a una url con query parámetros
     * @param urlText url en forma de String
     * @param params parámetros de la llamada
     * @return String cadena con la respuesta
     * @throws RequestApiError
     */
    public String requestGetWithParams(String urlText, QueryParams params) throws RequestApiError {
        return requestGet(urlText+params.getQueryParams());
    }

    /**
     * Remplaza el placeholder de la URL por el de la palabra usada
     * @param url cadena con la url
     * @param replacement palabra a añadir a la url
     * @return String cadena con la url con la nueva información
     */
    public String replacePlaceholder(String url, String replacement){
        return url.replace(URL_PLACEHOLDER, replacement);
    }

    /**
     * Realiza una petición con un método a una URL
     * @param method método de la llamada (GET | POST | PUT | DELETE | ...)
     * @param url url a la que realizar la petición
     * @return String cadena con la respuesta obtenida
     * @throws RequestApiError
     */
    private String request(String method, URL url) throws RequestApiError {
        String response = "";
        Integer status = 500;
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);
            status = connection.getResponseCode();
            response = readResponse(connection);
        } catch (IOException e) {
            if(status.equals(TOO_MANY_REQUESTS_STATUS)) {
                throw new TooManyRequestsResponseException(url.toString());
            }
            throw new RequestApiError(status, ExternalApiService.class, e.getMessage());
        }
        return response;
    }

    /**
     * Interpreta la respuesta obtenida de una petición
     * @param connection conexión http
     * @return String con respuesta
     * @throws IOException
     */
    private String readResponse(HttpURLConnection connection) throws IOException {
        StringBuilder result = new StringBuilder();
        try(BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String linea;
            while ((linea = rd.readLine()) != null) {
                result.append(linea);
            }
        }
        return result.toString();
    }

    /**
     * Crea una url a partir de una cadena de texto
     * @param urlText cadena en forma de texto
     * @return URL
     * @throws RequestApiError
     */
    private URL createURL(String urlText) throws RequestApiError {
        try{
            return new URL(urlText);
        } catch (MalformedURLException e) {
            throw new RequestApiError(404, ExternalApiService.class, urlText);
        }
    }

}
