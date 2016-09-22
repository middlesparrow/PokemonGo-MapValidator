/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PokemonGoMapValidator;

import static PokemonGoMapValidator.Main.LOADINGPOKEMONGOS;
import static PokemonGoMapValidator.Main.MAPDIMENSION;
import static PokemonGoMapValidator.Main.EMAIL_DEST;
import static PokemonGoMapValidator.Main.LOGIN;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.frontendtest.components.ImageComparison;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author Altran
 */
public class RunComparison {

    public void RunComparison() {

    }

    public void comparison(String currentDirectory, WebDriver driver, String mapFile) throws IOException, InterruptedException {

        List<String> links = new ArrayList<>();
        List<WebElement> extra = new ArrayList<>();
        List<String> subjectList = new ArrayList<>();
        List<String> attachmentList = new ArrayList<>();
        WebDriverWait wait = new WebDriverWait(driver, 15);
        WebElement formElement;
        OpenURL openURL = new OpenURL();
        String localPrtscFile = "semPokemongos.png";
        String urlPrtscFile = "comPokemongos.png";
        String comPokemongos = currentDirectory + "/prints/";
        String semPokemongos = currentDirectory + "/prints/";
        String diferencasPath = currentDirectory + "/prints/";
        String diferencasFile = "";
        String baseUrl = "";
        int count = 0;
        int optionsAnimation = 1000;
        String responseCode = "";

        //pokemongos grow in sextagon shape, so I will force a square resolution
        driver.manage().window().setSize(new Dimension(MAPDIMENSION, MAPDIMENSION));
        //50*50 is the size of the squares to compare
        //much bigger and they start to fail
        ImageComparison imageComparison = new ImageComparison(50, 50, 0);

        LoadMap carregarDados = new LoadMap();
        carregarDados.LoadMap(currentDirectory, links, mapFile);

        if (links.size() > 0) {
            for (int i = 0; i < links.size(); i = i+2) {
                
                if (links.get(i).length() > 0)
                {

                //validate directory
                File f = new File(currentDirectory + "/prints/" + links.get(i + 1) + "/");
                if (!f.isDirectory()) {
                    new File(currentDirectory + "/prints/" + links.get(i + 1) + "/").mkdir();
                }
                if (f.isDirectory()) {

                    //validate url
                    
                    baseUrl = links.get(i);
                                       
                    responseCode = openURL.openURL(driver, baseUrl);
                    if ("200".equals(responseCode)) {

                        //para correrem umas animacoes no google maps dps de chegar a resposta
                        Thread.sleep(1000);

                        //open the Options
                        //wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Options']")));
                        extra = driver.findElements(By.xpath("//span[text()='Options']"));
                        if(extra.size()>0)
                        {
                        formElement = driver.findElement(By.xpath("//span[text()='Options']"));
                        formElement.click();
                        Thread.sleep(optionsAnimation);
                        
                        //turn off all the options
                        for (int j = 1; j < 7; j++) {
                            if (driver.findElement(By.xpath("//div[contains(@class, 'form-control') and contains(@class, 'switch-container')][" + j + "]/div/input")).isSelected()) {
                                driver.findElement(By.xpath("//div[contains(@class, 'form-control') and contains(@class, 'switch-container')][" + j + "]/div/label")).click();
                            }
                            //Thread.sleep(250);
                            if (j == 3)
                            {
                                j= j+1;
                            }
                        }
                        
                        //close the options
                        formElement = driver.findElement(By.xpath("//span[text()='Options']"));
                        formElement.click();
                        Thread.sleep(optionsAnimation);
                        
                        //empty screenshot
                        File baseFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                        FileUtils.copyFile(baseFile, new File(semPokemongos + links.get(i + 1) + "/" + localPrtscFile));

                        //open the options
                        formElement = driver.findElement(By.xpath("//span[text()='Options']"));
                        formElement.click();
                        Thread.sleep(optionsAnimation);
                        
                        //turn on the pokemongos option
                        if (!driver.findElement(By.xpath("//div[contains(@class, 'form-control') and contains(@class, 'switch-container')][1]/div/input")).isSelected()) {
                                driver.findElement(By.xpath("//div[contains(@class, 'form-control') and contains(@class, 'switch-container')][1]/div/label")).click();
                            
                            Thread.sleep(250);
                        }
                        
                        //close the options
                        formElement = driver.findElement(By.xpath("//span[text()='Options']"));
                        formElement.click();

                        //time for the elements to load
                        Thread.sleep(LOADINGPOKEMONGOS);

                        //take screenshot
                        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                        // Now you can do whatever you need to do with it, for example copy somewhere
                        FileUtils.copyFile(scrFile, new File(comPokemongos + links.get(i + 1) + "/" + urlPrtscFile));

                        String imgOriginal = semPokemongos + links.get(i + 1) + "/" + localPrtscFile;
                        String imgToCompareWithOriginal = comPokemongos + links.get(i + 1) + "/" + urlPrtscFile;
                        String imgOutputDifferences = diferencasPath + links.get(i + 1) + "/" + diferencasFile + links.get(i + 1) + ".jpg";

                        if (imageComparison.fuzzyEqual(imgOriginal, imgToCompareWithOriginal, imgOutputDifferences)) {
                            System.out.println("Images are equal and that is bad!" + links.get(i + 1));
                            subjectList.add("Location without pokemongos: " + links.get(i + 1));

                            //to send by email the generated image, activate these two lines below
                            //attachmentList.add(diferencasPath + links.get(i + 1) + "/");
                            //attachmentList.add(diferencasFile + links.get(i + 1) + ".jpg");
                        } else {
                            //System.out.println("Images are not equal and that is good!" + links.get(i + 1));
                            count++;
                        }
                        }
                        else
                        {
                            System.out.println("Timeout finding an element: " + links.get(i + 1));
                            subjectList.add("Timeout finding an element: " + links.get(i + 1));
                        }

                    } else {
                        System.out.println("Erro a abrir a localização: " + responseCode + " - " + links.get(i + 1));
                        subjectList.add("Error opening the location: " + responseCode + " - " + links.get(i + 1));

                    }
                } else {
                    System.out.println("Directoria inexistente: " + links.get(i + 1));
                    subjectList.add("Error opening folder: " + links.get(i + 1));
                }
                
                }
                else
                {
                    System.out.println("Endereço vazio: " + i);
                    subjectList.add("Endereço vazio: " + i);
                }
                
            }

            subjectList.add("Maps without any errors: " + count);
            subjectList.add("Total maps tested : " + count);

        } else {
            System.out.println("Não carregou os url dos mapas");
            subjectList.add("Não carregou os url dos mapas");
        }

        //if email not configured, prints the message
        if(LOGIN.length()>0)
        {
        SendMail sendMail = new SendMail();
        sendMail.sendMail(currentDirectory, subjectList, attachmentList);
        }
        else
        {
            for (int i = 0; i < subjectList.size(); i++) 
                System.out.println(subjectList.get(i));
        }
    }
}
