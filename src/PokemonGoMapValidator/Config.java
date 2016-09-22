/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PokemonGoMapValidator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
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
