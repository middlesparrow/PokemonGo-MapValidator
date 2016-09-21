# PokemonGo-MapValidator

Compares a picture of the empty location, with a picture of the same location with the Pokémon.
If the picture with Pokémon is empty, it means that the server has stopped working or it is very slow.

# How it works:

1 - Takes a screenshot of the empty map. All Options are disabled using Selenium.
http://pokemap.oinkandstuff.com:5011/

![without Pokémon](https://github.com/middlesparrow/PokemonGo-MapValidator/blob/master/semPokemongos.png)


2 - Takes a screenshot of the map with pokemons. Only Options->Pokémon is enabled using Selenium.

![with Pokémon](https://github.com/middlesparrow/PokemonGo-MapValidator/blob/master/comPokemongos.png)


3 - Generates a picture with the differences. This picture is not important, it's just an option of the library I used.

![result](https://github.com/middlesparrow/PokemonGo-MapValidator/blob/master/vila-real.jpg)


# What is does

For each map you have, it creates a folder with the prints.
After testing all the maps, it can send an email with the results. It also can send attached pictures (disabled for now).

It is recommended to create a gmail account just for this project, and in this link
https://www.google.com/settings/security/lesssecureapps
activate the option for less secure applications.

# Installation
 - Java 8
 - Chrome Browser (the project uses chromedriver)
 - Gmail account

# How to use
 -h,--help                            show help  
 -l,--login <xxx@gmail.com>           email login  
 -p,--password <*****>                email password  
 -d,--email-dest <email>              email destination. Default: login email  
 -pl,--page-loading <value ms>        wait for the page to load: Default: 10000 miliseconds  
 -lp,--loading-pokemongo <value ms>   wait for pokemongo to load. Default: 10000 miliseconds
