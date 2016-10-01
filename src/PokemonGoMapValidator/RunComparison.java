/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PokemonGoMapValidator;

import static PokemonGoMapValidator.Main.LOADINGPOKEMONGOS;
import static PokemonGoMapValidator.Main.MAPDIMENSION;
import static PokemonGoMapValidator.Main.LOGIN;
import static PokemonGoMapValidator.Main.PAGELOADING;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.frontendtest.components.ImageComparison;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 *
 * @author Altran
 */
public class RunComparison {

    private Logger log = Logger.getLogger(RunComparison.class.getName());

    public void RunComparison() {

    }

    public void comparison(String currentDirectory, WebDriver driver, String mapFile) throws IOException, InterruptedException {

        List<String> links = new ArrayList<>();
        List<WebElement> extra = new ArrayList<>();
        List<String> subjectList = new ArrayList<>();
        List<String> attachmentList = new ArrayList<>();
        WebElement formElement;
        OpenURL openURL = new OpenURL();
        String localPrtscFile = "semPokemongos.png";
        String urlPrtscFile = "comPokemongos.png";
        String comPokemongos = currentDirectory + "/prints/";
        String semPokemongos = currentDirectory + "/prints/";
        String comparisonPath = currentDirectory + "/prints/";
        String comparisonFile = "";
        String baseUrl = "";
        int count = 0;
        int countTot = 0;
        int optionsAnimation = 1000;
        String responseCode = "";
        boolean zoom = false;

        

        //50*50 is the size of the squares to compare //much bigger and they start to fail
        ImageComparison imageComparison = new ImageComparison(50, 50, 0);

        //load data
        LoadMap carregarDados = new LoadMap();
        carregarDados.LoadMap(currentDirectory, links, mapFile);

        if (links.size() > 0) {
            for (int i = 0; i < links.size(); i = i + 2) {

                if (links.get(i).length() > 0) {

                    //validate directory
                    File f = new File(currentDirectory + "/prints/" + links.get(i + 1) + "/");
                    if (!f.isDirectory()) {
                        new File(currentDirectory + "/prints/" + links.get(i + 1) + "/").mkdir();
                    }
                    if (f.isDirectory()) {

                        //it is not necessary to clean the directory, but I'm doing it
                        FileUtils.cleanDirectory(f);

                        //validate url
                        baseUrl = links.get(i);

                        responseCode = openURL.openURL(driver, baseUrl);
                        if ("200".equals(responseCode)) {

                            //para correrem umas animacoes no google maps dps de chegar a resposta
                            Thread.sleep(1000);

                            //open the Options
                            //wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Options']")));
                            extra = driver.findElements(By.xpath("//span[text()='Options']"));
                            if (extra.size() > 0) {
                                
                                //this area runs if we define a zoom in or out
                                //first it zooms in so that the zoom out can be controled
                                if (zoom == true)
                                {
                                formElement = driver.findElement(By.xpath("//area"));
                                formElement.click();
                                
                                formElement.sendKeys(Keys.chord(Keys.ADD));
                                formElement.sendKeys(Keys.chord(Keys.SUBTRACT));
                                
                                }
                                        
                                formElement = driver.findElement(By.xpath("//span[text()='Options']"));
                                formElement.click();
                                Thread.sleep(optionsAnimation);

                                //turn off all the options
                                for (int j = 1; j < 7; j++) {
                                    if (driver.findElement(By.xpath("//div[contains(@class, 'form-control') and contains(@class, 'switch-container')][" + j + "]/div/input")).isSelected()) {
                                        driver.findElement(By.xpath("//div[contains(@class, 'form-control') and contains(@class, 'switch-container')][" + j + "]/div/label")).click();
                                    }
                                    //Thread.sleep(250);
                                    if (j == 3) {
                                        j = j + 1;
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
                                String imgOutputDifferences = comparisonPath + links.get(i + 1) + "/" + comparisonFile + links.get(i + 1) + ".jpg";

                                if (imageComparison.fuzzyEqual(imgOriginal, imgToCompareWithOriginal, imgOutputDifferences)) {
                                    System.out.println("Location without pokémon: " + links.get(i + 1) + " - " + LOADINGPOKEMONGOS + "ms");
                                    subjectList.add("Location without pokémon: " + links.get(i + 1) + " - " + LOADINGPOKEMONGOS + "ms");

                                    //to send by email the generated image, activate these two lines below
                                    //attachmentList.add(diferencasPath + links.get(i + 1) + "/");
                                    //attachmentList.add(diferencasFile + links.get(i + 1) + ".jpg");
                                } else {
                                    //System.out.println("Images are not equal and that is good!" + links.get(i + 1));
                                    count++;
                                }

                                countTot++;

                            } else {
                                System.out.println("Probably there is an error with the url: " + links.get(i) + " - Location: " + links.get(i + 1));
                                subjectList.add("Probably there is an error with the url: " + links.get(i) + " - Location: " + links.get(i + 1));
                                countTot++;
                            }

                        } else if ("408".equals(responseCode)) {
                            System.out.println("Timeout for the location: " + links.get(i + 1) + " - " + PAGELOADING + "ms");
                            subjectList.add("Timeout for the location: " + links.get(i + 1) + " - " + PAGELOADING + "ms");
                            countTot++;
                        } else {
                            System.out.println("Error opening the location: " + responseCode + " - " + links.get(i + 1));
                            subjectList.add("Error opening the location: " + responseCode + " - " + links.get(i + 1));
                            countTot++;

                        }
                    } else {
                        System.out.println("Error opening folder: " + links.get(i + 1));
                        subjectList.add("Error opening folder: " + links.get(i + 1));
                    }

                } else {
                    System.out.println("The url is empty. Line: " + i);
                    subjectList.add("The url is empty. Line: " + i);
                }

            }

            subjectList.add("Total maps working: " + count);
            subjectList.add("Total maps tested : " + countTot);

        } else {
            System.out.println("No data was loaded!");
            subjectList.add("No data was loaded!");
        }

        //sends to console and log
        for (int i = 0; i < subjectList.size(); i++) {
            System.out.println(subjectList.get(i));
            log.info(subjectList.get(i));
        }

        //if email not configured, prints the message
        if (LOGIN.length() > 0) {
            System.out.println("Sending email...");
            SendMail sendMail = new SendMail();
            sendMail.sendMail(currentDirectory, subjectList, attachmentList);

        } else {
            System.out.println("Email not available. Check log file with subject.");
        }
    }
}
