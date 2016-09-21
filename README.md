# PokemonGo-MapValidator

Compares a picture of the empty location with a picture with the Pokémon. If the picture with Pokémon is empty, it means that the server has stopped working or is very slow.

How it works:

Takes a screenshot of the empty map. All Options are disabled using Selenium.

![]({{site.baseurl}}/https://github.com/middlesparrow/PokemonGo-MapValidator/blob/master/semPokemongos.png)

Takes a screenshot of the map with pokemons. Only Options->Pokémon is enabled using Selenium.

![]({{site.baseurl}}/https://github.com/middlesparrow/PokemonGo-MapValidator/blob/master/comPokemongos.png)

Generates a picture with the differences.

![]({{site.baseurl}}/https://github.com/middlesparrow/PokemonGo-MapValidator/blob/master/vila-real.jpg)


What is does:

After testing all the maps, it can send an email with the results. It also can send attached pictures (disabled for now).
At the moment, the source account needs to be a gmail account and in this link
https://www.google.com/settings/security/lesssecureapps
we have to activate the option for less secure applications.

So, it is recommended to create an account just for this project.
