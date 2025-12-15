package com.cdsoftware.docuware.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.compiere.model.MClient;
import org.compiere.model.MStorageProvider;
import org.compiere.model.MTable;
import org.compiere.util.Env;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DocuwareUtil {
	
	/**
	 * Load data.
	 *
	 * @return true, if successful
	 */
	public boolean loadData() {
		return false;
	} 
	
	/**
	 * Save data to open KM.
	 *
	 * @param data the data
	 * @param fileName the file name
	 * @param url the url
	 * @param path the path
	 * @param encoding the encoding
	 * @return the string
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public String saveDataToDocuware(byte[] data, String fileName, String url, String path, String encoding)
			throws IOException {
		
		return "";
	}

	/**
	 * Check in.
	 *
	 * @param data the data
	 * @param fileName the file name
	 * @param url the url
	 * @param path the path
	 * @param encoding the encoding
	 * @param uuid the uuid
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void checkIn(byte[] data, String fileName, String url, String path, String encoding, String uuid) throws IOException{
		
	}

	/**
	 * Check out.
	 *
	 * @param existingUUID the existing UUID
	 * @param url the url
	 * @param fileName the file name
	 * @param encoding the encoding
	 * @return the int
	 */
	private int checkOut(String existingUUID, String url, String fileName, String encoding) {
		int statusCode = 0;
		
		return statusCode;
	}

	/**
	 * Gets the existing UUID.
	 *
	 * @param fileName the file name
	 * @param url the url
	 * @param path the path
	 * @param encoding the encoding
	 * @return the existing UUID
	 */
	public static String getExistingUUID(String fileName, String url, String path, String encoding) {
		URL obj;
		
		return null; 
	
	}

	/**
	 * Gets the dmsuuid.
	 *
	 * @param data the data
	 * @param config the config
	 * @param dataTitle the data title
	 * @return the dmsuuid
	 */
	public byte[] getDMSUUID(byte[] data, DMSConfig config, String dataTitle) {
		String uuid = null;

		return uuid.getBytes();
	}

	/**
	 * Gets the DMS config.
	 *
	 * @param prov the prov
	 * @param tableId the table id
	 * @param recordId the record id
	 * @return the DMS config
	 */
	public static DMSConfig getDMSConfig(MStorageProvider prov, int tableId, int recordId) {
		DMSConfig config = new DMSConfig();
		String authString = prov.getUserName() + ":" + prov.getPassword();
		try {
			String encoded = Base64.getEncoder().encodeToString(authString.getBytes("UTF-8"));
			config.setEncoding(encoded);
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		config.setUrl(prov.getURL());
		config.setRootFolder(prov.getFolder()); 
		config.setTableName(getTableName(tableId));
		config.setRecordId(recordId);
		return config;
	}
	
	/**
	 * Gets the table name.
	 *
	 * @param tableId the table id
	 * @return the table name
	 */
	public static String getTableName(int tableId) {
		return new MTable(Env.getCtx(), tableId, null).getTableName().toUpperCase();
	}
	
	
	/**
	 * Gets the client name.
	 *
	 * @param clientId the client id
	 * @return the client name
	 */
	public static String getClientName(int clientId) {
		return new MClient(Env.getCtx(), clientId, null).getName();
	}
	
	/**
	 * Gets the document.
	 *
	 * @param uuid the uuid
	 * @param config the config
	 * @return the document
	 */
	public static byte[] getDocument(String uuid, DMSConfig config, String sessionCookie) {
		byte[] dataEntry = null;
		
		String url = config.getUrl() + "FileCabinets/" + config.getRootFolder() + "/Documents/" + uuid + "/FileDownload";
		
		URL obj;
		try {
			obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("Accept", "application/pdf");
			con.setRequestProperty("Cookie", sessionCookie);
			con.setRequestProperty("User-Agent", "iDempiere-Client/1.0");
			con.setInstanceFollowRedirects(false);
			
			int responseCode = con.getResponseCode();
            
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream pdfInputStream = con.getInputStream();

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
    		    byte[] byteChunk = new byte[4096]; // Or whatever size you want to read in at a time.
    		    int n;

    		    while ( (n = pdfInputStream.read(byteChunk)) > 0 ) {
    		      baos.write(byteChunk, 0, n);
    		    }

    		    dataEntry = baos.toByteArray();
                
            } else {
                return null;
            } 
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dataEntry;
	}
	
	/**
	 * Checks if is docs delete by UUID.
	 *
	 * @param uuid the uuid
	 * @param config the config
	 * @return true, if is docs delete by UUID
	 */
	public static boolean isDocsDeleteByUUID(String uuid, DMSConfig config) {
		boolean status = Boolean.TRUE;
		
		return status;
	}
	
	/**
	 * Gets the client path.
	 *
	 * @return the client path
	 */
	public static String getClientPath() { 
		return "/" + getClientName(Env.getAD_Client_ID(Env.getCtx()));
	}
	
	/**
	 * Login DMS.
	 *
	 * @param DMSConfig
	 * @param authString
	 * @return confirm authentication
	 */
	public static String loginDMS(DMSConfig config) {
	    String[] credentials = decodeCredentials(config.getEncoding());
	    
	    if (credentials != null && credentials.length == 2) {
	        String userName = credentials[0];
	        String password = credentials[1];
	        
	        try {
	            String urlString = config.getUrl() + "Account/Logon";
	            URL url = new URL(urlString);
	            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	            
	            // Configurar la conexión
	            connection.setRequestMethod("POST");
	            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	            connection.setRequestProperty("Accept", "application/json");
	            connection.setDoOutput(true);
	            connection.setInstanceFollowRedirects(false);
	            
	            // Construir el body
	            String body = "UserName=" + java.net.URLEncoder.encode(userName, "UTF-8") +
	                         "&Password=" + java.net.URLEncoder.encode(password, "UTF-8") +
	                         "&RedirectToMyselfInCaseOfError=false" +
	                         "&RememberMe=false";
	            
	            // Enviar los datos
	            try (OutputStream os = connection.getOutputStream()) {
	                byte[] input = body.getBytes("utf-8");
	                os.write(input, 0, input.length);
	            }
	            
	            // Obtener la respuesta
	            int responseCode = connection.getResponseCode();
	            
	            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
	                // Recopilar las cookies específicas de Docuware
	                String sessionCookie = extractDocuwareCookies(connection.getHeaderFields());
	                
	                //return sessionCookie != null && sessionCookie.length() > 0;
	                return sessionCookie;
	            } else {
	                // Manejar error de manera segura
	                InputStream errorStream = connection.getErrorStream();
	                if (errorStream != null) {
	                    try (BufferedReader br = new BufferedReader(
	                        new InputStreamReader(errorStream, "utf-8"))) {
	                        String responseBody = br.lines().collect(Collectors.joining(System.lineSeparator()));
	                        System.err.println("Login failed. Response: " + responseBody);
	                    }
	                } else {
	                    System.err.println("Login failed with status code: " + responseCode + ". No error stream available.");
	                }
	                return null;
	            }
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        }
	    }
	    
	    return null;
	}
	
	/**
	 * Decode credentials
	 *
	 * @param encodedAuth
	 * @return String[] 
	 * [0] user
	 * [1] password
	 */
	public static String[] decodeCredentials(String encodedAuth) {
        if (encodedAuth == null || encodedAuth.isEmpty()) {
            return null;
        }
        
        try {
            // Decodificar el Base64
            byte[] decodedBytes = Base64.getDecoder().decode(encodedAuth);
            String decodedString = new String(decodedBytes, "UTF-8");
            
            // Separar usuario y contraseña (formato: "usuario:contraseña")
            return decodedString.split(":", 2);
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
	
	/**
     * Método para loguear headers (debugging)
     */
    private static void logHeaders(String message, Map<String, List<String>> headers) {
        if (headers != null) {
            for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
                if (entry.getKey() != null && entry.getValue() != null) {
                    String headerValue = String.join(", ", entry.getValue());
                    // Acortar valores largos para mejor legibilidad
                    if (headerValue.length() > 500) {
                        headerValue = headerValue.substring(0, 500) + "... [truncated]";
                    }
                }
            }
        }
    }
    
    /**
     * Extrae específicamente las cookies de Docuware que necesitamos
     */
    private static  String extractDocuwareCookies(Map<String, List<String>> headers) {
        StringBuilder cookies = new StringBuilder();
        
        if (headers != null) {
            for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
                if ("Set-Cookie".equalsIgnoreCase(entry.getKey())) {
                    for (String cookie : entry.getValue()) {
                        
                        // Buscar específicamente .DWPLATFORMAUTH y otras cookies importantes
                        if (cookie.contains(".DWPLATFORMAUTH") || 
                            cookie.contains("DWPLATFORMHOSTID") ||
                            cookie.contains("OpenIdConnect") || 
                            cookie.contains("AspNetCore")) {
                            
                            // Extraer solo la parte clave=valor (antes del primer ';')
                            String cookieValue = extractCookieValue(cookie);
                            if (cookieValue != null && cookieValue.length() > 0) {
                                if (cookies.length() > 0) {
                                    cookies.append("; ");
                                }
                                cookies.append(cookieValue);
                            }
                        }
                    }
                }
            }
        }
        
        return cookies.toString();
    }
    
    /**
     * Extrae la parte clave=valor de la cookie (antes del primer ';')
     */
    private static String extractCookieValue(String cookie) {
        try {
            // Tomar solo la parte antes del primer ';'
            String[] parts = cookie.split(";");
            if (parts.length > 0) {
                return parts[0].trim();
            }
        } catch (Exception e) {
        }
        return "";
    }

}
