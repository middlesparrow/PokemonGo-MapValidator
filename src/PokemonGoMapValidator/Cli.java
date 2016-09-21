/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PokemonGoMapValidator;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import static PokemonGoMapValidator.Main.EMAIL_DEST;
import static PokemonGoMapValidator.Main.LOGIN;
import static PokemonGoMapValidator.Main.PASS;
import static PokemonGoMapValidator.Main.LOADINGPOKEMONGOS;
import static PokemonGoMapValidator.Main.PAGELOADING;
import static PokemonGoMapValidator.Main.VALIDATEURL;
import static PokemonGoMapValidator.Main.MAPDIMENSION;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;

public class Cli {

    private static final Logger log = Logger.getLogger(Cli.class.getName());
    private String[] args = null;
    private Options options = new Options();

    public Cli(String[] args) {

        System.out.println("Number of Arguments : " + args.length);

        LOADINGPOKEMONGOS = 10000;
        PAGELOADING = 10000;
        MAPDIMENSION = 800;
        VALIDATEURL = false;
        LOGIN = "";
        PASS = "";
        EMAIL_DEST = "";

        this.args = args;

        options.addOption("h", "help", false, "show help");

        Option login = Option.builder("l")
                .longOpt("login") //another calling for the option
                .desc("email login") //description
                .required(false) //option required
                .hasArg(true) //the option has arguments
                .numberOfArgs(1) //number of arguments
                .argName("xxx@gmail.com") //arg template
                .build(); //build!!

        Option password = Option.builder("p")
                .longOpt("password") //another calling for the option
                .desc("email password") //description
                .required(false) //option required
                .hasArg(true) //the option has arguments
                .numberOfArgs(1) //number of arguments
                .argName("*****") //arg template
                .build(); //build!!

        Option pageLoading = Option.builder("pl")
                .longOpt("page-loading") //another calling for the option
                .desc("wait for the page to load: Default: " + PAGELOADING + " miliseconds") //description
                .required(false) //option required
                .hasArg(true) //the option has arguments
                .numberOfArgs(1) //number of arguments
                .argName("value ms") //arg template
                .build(); //build!!

        Option loadinPokemongo = Option.builder("lp")
                .longOpt("loading-pokemongo") //another calling for the option
                .desc("wait for pokemongo to load. Default: " + LOADINGPOKEMONGOS + " miliseconds") //description
                .required(false) //option required
                .hasArg(true) //the option has arguments
                .numberOfArgs(1) //number of arguments
                .argName("value ms") //arg template
                .build(); //build!!

        Option mapDimension = Option.builder("md")
                .longOpt("map-dimension") //another calling for the option
                .desc("TODO: Map dimension it's a square. Default: " + MAPDIMENSION + "*" + MAPDIMENSION + " image") //description
                .required(false) //option required
                .hasArg(true) //the option has arguments
                .numberOfArgs(1) //number of arguments
                .argName("xxx") //arg template
                .build(); //build!!

        Option validateUrl = Option.builder("v")
                .longOpt("validate-url") //another calling for the option
                .desc("TODO: Validate URL. Default: " + VALIDATEURL) //description
                .required(false) //option required
                .hasArg(false) //the option has arguments
                .numberOfArgs(0) //number of arguments
                //.argName("xxx") //arg template
                .build(); //build!!

        Option emailDest = Option.builder("d")
                .longOpt("email-dest") //another calling for the option
                .desc("email destination. Default: login email") //description
                .required(false) //option required
                .hasArg(true) //the option has arguments
                .numberOfArgs(1) //number of arguments
                .argName("email") //arg template
                .build(); //build!!

        options.addOption(login);
        options.addOption(password);
        options.addOption(emailDest);
        options.addOption(pageLoading);
        options.addOption(loadinPokemongo);
        //options.addOption(mapDimension);
        //options.addOption(validateUrl);

    }

    public void parse() {
        CommandLineParser parser = new DefaultParser();

        CommandLine cmd = null;
        try {
            //validate the options
            cmd = parser.parse(options, args);

            if (cmd.hasOption("h")) {
                help();
            }

            if (cmd.hasOption("l")) {
                log.log(Level.INFO, "Using argument -l={0}", cmd.getParsedOptionValue("l"));
                LOGIN = cmd.getOptionValue("l");
                EMAIL_DEST = cmd.getOptionValue("l");

            }

            if (cmd.hasOption("p")) {
                log.log(Level.INFO, "Using argument -p={0}", cmd.getParsedOptionValue("p"));
                PASS = cmd.getOptionValue("p");

            }
            
            if (cmd.hasOption("l") && !cmd.hasOption("p")) {
                log.log(Level.SEVERE, "Missing p option");
                help();
            }
            if (cmd.hasOption("p") && !cmd.hasOption("l")) {
                log.log(Level.SEVERE, "Missing l option");
                help();
            }

            if (cmd.hasOption("pl")) {
                log.log(Level.INFO, "Using argument -pl={0}", cmd.getParsedOptionValue("pl"));
                PAGELOADING = Integer.parseInt(cmd.getOptionValue("pl"));

            }

            if (cmd.hasOption("lp")) {
                log.log(Level.INFO, "Using argument -lp={0}", cmd.getParsedOptionValue("lp"));
                LOADINGPOKEMONGOS = Integer.parseInt(cmd.getOptionValue("lp"));
            }

            if (cmd.hasOption("d")) {
                log.log(Level.INFO, "Using argument -d={0}", cmd.getParsedOptionValue("d"));
                    EMAIL_DEST = cmd.getOptionValue("d");
               

            }
/*
            if (cmd.hasOption("v")) {
                log.log(Level.INFO, "Using argument -v");
                VALIDATEURL = true;

            }
*/

        } catch (ParseException | NumberFormatException e) {
            System.out.println(e.getMessage());
            help();
        }

    }

    private void help() {
        // This prints out some help
        HelpFormatter formater = new HelpFormatter();
        formater.setOptionComparator(null);
        formater.printHelp("Image Comparison", options);
        System.exit(0);
    }
}
