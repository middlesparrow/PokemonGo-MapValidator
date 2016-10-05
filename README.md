# PokemonGo-MapValidator

Compares a picture of the empty location, with a picture of the same location with the Pokémon.
If the picture with Pokémon is empty, it means that the server has stopped working or it is very slow.

I used for testing the maps available at http://www.oinkandstuff.com  
They are running on a previous, more light, version of the project https://github.com/PokemonGoMap/PokemonGo-Map


# How it works:

1 - Takes a screenshot of the empty map. All Options are disabled using Selenium.
http://pokemap.oinkandstuff.com:5011/

![without Pokémon](/images/semPokemongos.png)


2 - Takes a screenshot of the map with pokemons. Only Options->Pokémon is enabled using Selenium.

![with Pokémon](/images/comPokemongos.png)


3 - Generates a picture with the differences. This picture is not important, it's just an option of the library I used.

![result](/images/vila-real.jpg)


# What is does

For each map you have, it creates a folder with the prints.
After testing all the maps, it can send an email with the results. It also can send attached pictures (disabled for now).

It also logs to a log file and to an html file.

# E-mail account (if you want to send emails)

It is recommended to create a gmail account just for this project, and in this link
https://www.google.com/settings/security/lesssecureapps
activate the option for less secure applications.

If the gmail account you want to use has the 2-step verification, refer to this link https://support.google.com/accounts/answer/185833 and https://support.google.com/a/answer/6260879

# Installation prerequisites
 - Java 8 (if you have Java 7, try it and let me know how it went ;) )  
 - Chrome Browser (the project uses Selenium chromedriver)  
 - Gmail account (if you want to send email)  

# How to use
 -h,--help | show help  
 -l,--login <xxx@gmail.com> | email login  
 -p,--password <*****> | email password  
 -d,--email-dest <email> | email destination. Default: login email  
 -pl,--page-loading <value ms> | wait for the page to load: Default: 10000 miliseconds  
 -lp,--loading-pokemongo <value ms> | wait for pokemongo to load. Default: 10000 miliseconds  
 -md,--map-dimension <"square" size> | Square dimension (more or less...). Default: full screen  
 -px,--x-coord <position x> | Check graphics-info  
 -py,--y-coord <position y> | Check graphics-info  
 -z,--zoom <units or negative units> | zoom in or out  
 -gi,--graphics-info | x-coord y-coord width height of every screen  
 
 mapvalidator.xls - place the maps in this file. Start a fresh copy with mapvalidator-template.xls if necessary.
 
 chromedriver.exe - v2.24. Replace if there's a more recent version.
 
 chromeprofile.zip - a clean profile for chrome browser. Probably at some point in time might need to be recreated. Use Selenium to do it.

# Libs
 - javax.mail-1.5.6  
 - jxl-2.6.12  
 - commons-cli-1.3.1  
 - zip4j_1.3.2  
 - image-comparison-1.0  
 - log4j-1.2.17 and log4j-extras-1.2.17  
 - selenium-server-standalone-2.53.1  

# ToDo
 - Map dimension needs to be worked on;  
 - Graphics device: select the device where to send the browser more easily than x-y coords;  
 - Validate URL: option do detect the error code of the request, Selenium does not have this;  

# Develop
It's a NetBeans 8.1 project.

# Download
You can download de dist folder and run directly from the command line.  
Example to see the help: java -jar PokemonGo-MapValidator.jar -h
