/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PokemonGoMapValidator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import org.apache.commons.cli.ParseException;

/**
 *
 * @author Altran
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static boolean VALIDATEURL;
    public static String LOGIN;
    public static String EMAIL_DEST;
    public static String PASS;
    public static int LOADINGPOKEMONGOS;
    public static int PAGELOADING;
    public static int MAPDIMENSION;

    public static void main(String[] args) throws IOException, InterruptedException, ParseException {
        // TODO code application logic here
        String currentDirectory = Paths.get(".").toAbsolutePath().normalize().toString();
        String driverFile = null;
        String profileFile = null;
        String mapFile = null;
        int exit = 0;

        String javaHome = System.getProperty("java.home");
        if (javaHome != null) {
            double version = Double.parseDouble(System.getProperty("java.specification.version"));
            if (version >= 1.8) {

                //argument parser
                new Cli(args).parse();

                try {

                    File mataInstancia = new File(currentDirectory + "/" + "mataCR.bat");
                    if (mataInstancia.exists()) {
                        //System.out.println(mataInstancia.getAbsolutePath());
                        Runtime.getRuntime().exec("cmd /c " + "\"" + mataInstancia.getAbsolutePath() + "\"");
                        Thread.sleep(1000);
                    }
                    else
                    {
                        System.out.println("File " + currentDirectory + "/" + "mataCR.bat" + " is missing, but not required.");
                    }

                    File driverFileExists = new File(currentDirectory + "/" + "chromedriver.exe");
                    if (driverFileExists.exists()) {
                        driverFile = currentDirectory + "/" + "chromedriver.exe";
                        
                    }
                    else
                    {
                        System.out.println("File " + currentDirectory + "/" + "chromedriver.exe" + " is missing.");
                        exit = 1;
                    }

                    File profileFileExists = new File(currentDirectory + "/" + "chromeprofile.zip");
                    if (profileFileExists.exists()) {
                        profileFile = currentDirectory + "/" + "chromeprofile.zip";
                    }
                    else
                    {
                        System.out.println("File " + currentDirectory + "/" + "chromeprofile.zip" + " is missing.");
                        exit = 1;
                    }
                    
                    File mapFileExists = new File(currentDirectory + "/" + "mapvalidator.xls");
                    if (mapFileExists.exists()) {
                        mapFile = currentDirectory + "/" + "mapvalidator.xls";
                    }
                    else
                    {
                        System.out.println("File " + currentDirectory + "/" + "mapvalidator.xls" + " is missing.");
                        exit = 1;
                        
                    }
                    
                    if (exit>0)
                    {
                        System.exit(0);
                    }

                    Config mapa = new Config();
                    mapa.configInstance(driverFile, profileFile, mapFile);

                    //  } else {
                    //    System.out.println("Entry arguments: email; password; time to wait (ms)");
                    //}
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            else
            {
                System.out.println("Installed java version: " + version + " - version required: 1.8");
            }
        }
        else
            {
                System.out.println("Please install java 1.8");
            }
    }

}
