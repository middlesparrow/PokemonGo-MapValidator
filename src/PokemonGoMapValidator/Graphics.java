/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PokemonGoMapValidator;

import java.awt.DisplayMode;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 *
 * @author Altran
 */
public class Graphics {
    
    public void graphicsDevice() {
        

        int count;
//        GraphicsEnvironment ge = GraphicsEnvironment.
//   getLocalGraphicsEnvironment();
//   GraphicsDevice[] gs = ge.getScreenDevices();
//   for (int j = 0; j < gs.length; j++) {
//      GraphicsDevice gd = gs[j];
//      GraphicsConfiguration[] gc =
//      gd.getConfigurations();
//      for (int i=0; i < gc.length; i++) {
//         JFrame f = new
//         JFrame(gs[j].getDefaultConfiguration());
//         Canvas c = new Canvas(gc[i]);
//         Rectangle gcBounds = gc[i].getBounds();
//         int xoffs = gcBounds.x;
//         int yoffs = gcBounds.y;
//         f.getContentPane().add(c);
//         f.setLocation((i*50)+xoffs, (i*60)+yoffs);
//         f.show();
//      }

        

        System.out.println("Graphics:");
        
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        
        GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
        System.out.println("Default screen device: " + defaultScreen.getIDstring());
        
        GraphicsDevice[] devices = ge.getScreenDevices();
        System.out.println("Total devices: " + devices.length);
        
        for (int j = 0; j < devices.length; j++) {
            count = j+1;
            System.out.println("Device: " + devices[j].getIDstring());
            
            DisplayMode dm = devices[j].getDisplayMode();
            System.out.println("  Width " + dm.getWidth());
            System.out.println("  Height " + dm.getHeight());
            
            
            //in windows, should be a total of just 1 configuration
            GraphicsDevice gd = devices[j];
            GraphicsConfiguration config = gd.getDefaultConfiguration();
            
            //System.out.println("  Configuration " + (i + 1));
            System.out.println("bounds  " + config.getBounds());

            
        }
        //System.out.println("Browser:");
        System.exit(0);
        
        //GraphicsConfiguration[] configurations = defaultScreen.getConfigurations();
        
    }
    
    //on standby
    public void browserDimension(WebDriver driver) throws InterruptedException {
        
        System.out.println("Opening browser...");
        
        driver.get("http://www.google.com");
        Thread.sleep(1000);
        //driver.manage().window().maximize();
        Dimension win_size = driver.manage().window().getSize();
        System.out.println("Browser border dimensions :");
        
            WebElement html = driver.findElement(By.tagName("html"));
            int inner_width = Integer.parseInt(html.getAttribute("clientWidth"));
            int outer_width = win_size.width - inner_width;
            System.out.println("Left + right border width: " + outer_width);
            int inner_height = Integer.parseInt(html.getAttribute("clientHeight"));
            int outer_height = win_size.height - inner_height;
            System.out.println("Top + bottom border height: " + outer_height);
            
            graphicsDevice();

        try {
                if (driver != null) {
                    Exit sair = new Exit();
                    sair.exit(driver);
                    System.out.println("ChromeDriver and Chrome unloaded");
                    System.exit(0);
                }
            } catch (InterruptedException ex) {
                System.err.println("Exit error: " + ex.getMessage());
            }
    }
    
    public int existsGraphicsDevice()
    {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        
        GraphicsDevice[] devices = ge.getScreenDevices();

        return devices.length;
    }
    
}
