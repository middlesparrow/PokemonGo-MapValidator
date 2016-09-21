/*
http://www.mkyong.com/java/open-browser-in-java-windows-or-linux/


//um extra com exemplos de abrir em java, mas o java nao permite navegar, deve precisar do javascript


 */
package extra;

import java.awt.Desktop;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;

/**
 *
 * @author Altran
 */
public class OpenURLOld {

    public void OpenURL() {

    }

    public boolean openURL(String url) throws IOException, InterruptedException, URISyntaxException {
        String os = System.getProperty("os.name").toLowerCase();
        Runtime rt = Runtime.getRuntime();
        String[] browsersWin = {"iexplore"};
        String[] browsersNux = {"epiphany", "firefox", "mozilla", "konqueror",
            "netscape", "opera", "links", "lynx"};
        String browser = null;

        try {

            if (os.contains("win")) {

                // this doesn't support showing urls in the form of "page.html#nameLink"
                //rt.exec("rundll32 url.dll,FileProtocolHandler " + url);
                
                //the underline was supposed to work, but does not
                //rt.exec(new String[]{browsersWin[0], url}); // open using a browser
                
                //works
                //rt.exec(new String[]{"cmd", "/c", "start", browsersWin[0], url});

                /*
                //cicle through a list of browsers, but invalid names need to be dealed with
                for (int count = 0; count < browsersWin.length && browser == null; count++) {
                    if (rt.exec((new String[]{"cmd", "/c","start", browsersWin[count]})).waitFor() == 0) {
                        browser = browsersWin[count]; // have found a browser
                    }
                }
                 */
                
                /*
                //works
                ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "start", browsersWin[0], url);
                Process p = pb.start();
                p.waitFor();
                */
                
                /*
                URI uri = new URL(url).toURI();
                Desktop desktop = Desktop.getDesktop();
                desktop.browse(uri);
                */

            } else if (os.contains("mac")) {

                rt.exec("open " + url);

            } else if (os.contains("nix") || os.contains("nux")) {

                // Build a command string which looks like "browser1 "url" || browser2 "url" ||..."
                StringBuilder cmd = new StringBuilder();
                for (int i = 0; i < browsersNux.length; i++) {
                    cmd.append((i == 0 ? "" : " || ") + browsersNux[i] + " \"" + url + "\" ");
                }

                rt.exec(new String[]{"sh", "-c", cmd.toString()});

            } else {

            }
           

            URL url2 = new URL("http://www.oinkandstuff.com");
            HttpURLConnection connection = (HttpURLConnection) url2.openConnection();
            connection.setConnectTimeout(30000);
            connection.setRequestMethod("GET");
            //connection.connect();
            int statusCode = connection.getResponseCode();
            
            System.out.println(statusCode);
            
            
        } catch (TimeoutException ex) {
            return false;
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return true;
    }

    public boolean openURL(WebDriver driver, String url, int pageLoadTime) throws IOException {

        try {

            driver.get(url);

        } catch (TimeoutException ex) {
            return false;
        }
        return true;
    }

}
