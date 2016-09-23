/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PokemonGoMapValidator;

import java.io.File;
import java.io.IOException;
import java.util.List;
import jxl.*;
import jxl.read.biff.BiffException;

/**
 *
 * @author Altran
 */
/**
 *
 * @author Altran
 */
public class LoadMap {

    public void LoadMap() {

    }

    public void LoadMap(String currentDirectory, List<String> links, String mapFile) {

        try {
            Workbook workbook1 = Workbook.getWorkbook(new File(mapFile)); //file containing the data
            Sheet sheet1 = workbook1.getSheet(0); //save the first sheet in sheet1
                int size = sheet1.getRows();
            // OBTAINING THE DATA YOU NEED
            if (size>0)
            {
            for (int i = 1; i < size; i++) {
                Cell s = sheet1.getCell(0, i); // getCell(column,row) //obtains site
                Cell n = sheet1.getCell(1, i); // obtains name
                if (s.getContents() != null)
                    if (!"".equals(s.getContents()))
                {
                links.add(s.getContents());
                links.add(n.getContents());
                }
               
            }
            }
            workbook1.close(); //close the file
        } catch (BiffException | IOException ex) {
            System.out.println(ex.getMessage());
        }

    }

}
