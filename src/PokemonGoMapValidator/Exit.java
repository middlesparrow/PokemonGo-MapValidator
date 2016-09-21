/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PokemonGoMapValidator;

import org.openqa.selenium.WebDriver;

/**
 *
 * @author to
 */
public class Exit {
    
    public void exit()
    {
        
    }
    
    public void exit(WebDriver driver) throws InterruptedException
    {
        //parece que em algumas situações, o melhor é aguardar uns segundos antes de sair
        Thread.sleep(5000);
        
        //para tirar o chromedriver da memória, tem de se fazer quit
        driver.quit();
  
    }
    
}
