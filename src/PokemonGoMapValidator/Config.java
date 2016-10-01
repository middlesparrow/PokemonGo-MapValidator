/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PokemonGoMapValidator;

import static PokemonGoMapValidator.Main.MAPDIMENSION;
import static PokemonGoMapValidator.Main.XCOORD;
import static PokemonGoMapValidator.Main.YCOORD;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 *
 * @author Altran
 */
public class Config {

    public void Config() {

    }

    public void configInstance(String driverFile, String profileFile, String mapFile) {
        WebDriver webDriver = null;

        try {
            System.setProperty("webdriver.chrome.driver", driverFile);

            String currentDirectory = Paths.get(".").toAbsolutePath().normalize().toString();

            ZipFile zipFile = new ZipFile(profileFile);
            zipFile.extractAll(currentDirectory + "/" + "profile" + "/");

            File f = new File(currentDirectory + "/prints/");
            if (!f.isDirectory()) {
                new File(currentDirectory + "/prints/").mkdir();
            }

            HashMap<String, Object> chromePrefs = new HashMap<>();
            chromePrefs.put("profile.default_content_settings.popups", 0);
            chromePrefs.put("download.default_directory", currentDirectory);

            ChromeOptions options = new ChromeOptions();

            //se na user-data-dir nao existir profile do chrome, é gerado um na hora
            options.addArguments("user-data-dir=" + currentDirectory + "/" + "profile");
            options.addArguments("--start-maximized");
            //adicionei esta linha, já que dns e portos andam sempre em mudanças
            options.addArguments("--dns-prefetch-disable");
            //options.addExtensions(new File(extensionFile));
            options.setExperimentalOption("prefs", chromePrefs);

            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
            capabilities.setCapability(ChromeOptions.CAPABILITY, options);
            webDriver = new ChromeDriver(capabilities);

            //pokemongos grow in sextagon shape, so I will force a square resolution
            webDriver.manage().window().setPosition(new Point(XCOORD, YCOORD));
            if (MAPDIMENSION>0)
            {
            webDriver.manage().window().setSize(new Dimension(MAPDIMENSION, MAPDIMENSION));
            }
            else
            {
                webDriver.manage().window().maximize();
            }
            
            //in the code below, I tried to create a square, but the width's and height's
            //seem to change, and I didn't figured out why and how
            //TODO in the future
            /*
            webDriver.get("http://www.google.com");
            Thread.sleep(1000);
            Dimension win_size = webDriver.manage().window().getSize();

            WebElement html = webDriver.findElement(By.tagName("html"));
            int inner_width = Integer.parseInt(html.getAttribute("clientWidth"));
            int outer_width = win_size.width - inner_width;
            int inner_height = Integer.parseInt(html.getAttribute("clientHeight"));
            int outer_height = win_size.height - inner_height;

            //pokemongos grow in sextagon shape, so I will force a square resolution
            webDriver.manage().window().setPosition(new Point(0, -1080));
            webDriver.manage().window().setSize(new Dimension(800 + outer_width, 600 + outer_height));

            if (VALIDATEBROWSER) {
                new Graphics().browserDimension(webDriver);
            }

            */
            
            RunComparison comparison = new RunComparison();
            comparison.comparison(currentDirectory, webDriver, mapFile);

        } catch (ZipException | IOException | InterruptedException ex) {
            System.err.println("Config error: " + ex.getMessage());
        } finally {
            try {
                if (webDriver != null) {
                    Exit sair = new Exit();
                    sair.exit(webDriver);
                    System.out.println("ChromeDriver and Chrome unloaded");
                }
            } catch (InterruptedException ex) {
                System.err.println("Exit error: " + ex.getMessage());
            }
        }
    }
}
