/*
 */
package PokemonGoMapValidator;

import static PokemonGoMapValidator.Main.PAGELOADING;
import static PokemonGoMapValidator.Main.VALIDATEURL;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;

/**
 *
 * @author Altran
 */
public class OpenURL {

    public void OpenURL() {

    }

    public String openURL(WebDriver driver, String url) throws IOException {

        driver.manage().timeouts().pageLoadTimeout(PAGELOADING, TimeUnit.MILLISECONDS);
        
        String responseCode = "";
        try {
            
            if (VALIDATEURL == false)
            {
                driver.get(url);
                responseCode = "200";
            }
            else
            {
     
            //driver.get(url);
            //responseCode = "200";
            
            //ao testar se o link existe, há dois problemas:
            //mesmo só a pedir o HEAD, vai ser lento se o site estiver em carga
            //depois, também queria mostrar o codigo de erro, nomeadamente o 500, mas n estava a dar, só o 200.
            
            responseCode = linkExists(url);
            if (responseCode == "200")
            {
            driver.get(url);
            }
            else
            {
                //return responseCode;
            }
            }



        } 
        catch (TimeoutException ex) {
            responseCode = "408";
            return responseCode;
        }
        catch (Exception ex) {
            return ex.getMessage();
        }
        return responseCode;
    }
    
    public String linkExists(String URLName){
        int statusCode = 0;
    try {
        HttpURLConnection.setFollowRedirects(false);
        HttpURLConnection connection = (HttpURLConnection) new URL(URLName).openConnection();
        connection.setConnectTimeout(PAGELOADING);
        connection.setRequestMethod("HEAD");
        connection.connect();
        //return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
        //System.out.println(statusCode);
        statusCode = connection.getResponseCode();
        return "" + statusCode;
        
    }
    catch (Exception ex) {
        return "" + ex.toString();
    }
}

}
