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
import static PokemonGoMapValidator.Main.VALIDATEBROWSER;
import static PokemonGoMapValidator.Main.MAPDIMENSION;
import static PokemonGoMapValidator.Main.XCOORD;
import static PokemonGoMapValidator.Main.YCOORD;
import static PokemonGoMapValidator.Main.ZOOMINOROUT;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;

public class Cli {

    private Logger log = Logger.getLogger(Cli.class.getName());
    private String[] args = null;
    private Options options = new Options();

    public Cli(String[] args) {

        System.out.println("Number of Arguments : " + args.length);

        LOADINGPOKEMONGOS = 10000;
        PAGELOADING = 10000;
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
                .desc("Square dimension (more or less...) Default: full screen") //description
                .required(false) //option required
                .hasArg(true) //the option has arguments
                .numberOfArgs(1) //number of arguments
                .argName("\"square\" size") //arg template
                .build(); //build!!
        
        Option positionX= Option.builder("px")
                .longOpt("x-coord") //another calling for the option
                .desc("") //description
                .required(false) //option required
                .hasArg(true) //the option has arguments
                .numberOfArgs(1) //number of arguments
                .argName("position x") //arg template
                .build(); //build!!
        
        Option positionY= Option.builder("py")
                .longOpt("y-coord") //another calling for the option
                .desc("") //description
                .required(false) //option required
                .hasArg(true) //the option has arguments
                .numberOfArgs(1) //number of arguments
                .argName("position y") //arg template
                .build(); //build!!

        Option zoom= Option.builder("z")
                .longOpt("zoom-in") //another calling for the option
                .desc("zoom in or out") //description
                .required(false) //option required
                .hasArg(true) //the option has arguments
                .numberOfArgs(1) //number of arguments
                .argName("units or negative units") //arg template
                .build(); //build!!
        
        Option emailDest = Option.builder("d")
                .longOpt("email-dest") //another calling for the option
                .desc("email destination. Default: login email") //description
                .required(false) //option required
                .hasArg(true) //the option has arguments
                .numberOfArgs(1) //number of arguments
                .argName("email") //arg template
                .build(); //build!!

        Option graphicsInfo = Option.builder("gi")
                .longOpt("graphics-info") //another calling for the option
                .desc("x y width height of every screen") //description
                .required(false) //option required
                .hasArg(false) //the option has arguments
                .numberOfArgs(0) //number of arguments
                //.argName("email") //arg template
                .build(); //build!!
        
        Option validateUrl = Option.builder("v")
                .longOpt("validate-url") //another calling for the option
                .desc("TODO: Validate URL. Default: " + VALIDATEURL) //description
                .required(false) //option required
                .hasArg(false) //the option has arguments
                .numberOfArgs(0) //number of arguments
                //.argName("xxx") //arg template
                .build(); //build!!
        
        Option graphicsDevice = Option.builder("gd")
                .longOpt("graphics-device") //another calling for the option
                .desc("select screen") //description
                .required(false) //option required
                .hasArg(true) //the option has arguments
                .numberOfArgs(1) //number of arguments
                .argName("0 to ?") //arg template
                .build(); //build!!

        options.addOption(login);
        options.addOption(password);
        options.addOption(emailDest);
        options.addOption(pageLoading);
        options.addOption(loadinPokemongo);
        options.addOption(mapDimension);
        options.addOption(positionX);
        options.addOption(positionY);
        options.addOption(zoom);
        options.addOption(graphicsInfo);
        //options.addOption(graphicsDevice);
        //options.addOption(validateUrl);

    }

    public void parse() {
        CommandLineParser parser = new DefaultParser();
        int countDevice;

        CommandLine cmd = null;
        try {
            //validate the options
            cmd = parser.parse(options, args);
            
            if (cmd.hasOption("h")) {
                help();
            }

            if (cmd.hasOption("gi")) {
                //log.log(Level.INFO, "Using argument -gi={0}", cmd.getParsedOptionValue("gi"));
                VALIDATEBROWSER = false;
                new Graphics().graphicsDevice();
            }

            //work this in the future
            //place the browser in "0,0" of the selected screen
//            if (cmd.hasOption("gd")) {
//                countDevice = new Graphics().existsGraphicsDevice();
//
//                if (countDevice > 0) {
//                    if (countDevice >= Integer.parseInt(cmd.getOptionValue("gd")) + 1) {
//                        //fazer as contas para colocar no device escolhido
//                    } else {
//                        System.out.println("Device not found. Max: " + countDevice + "; Argument: " + Integer.parseInt(cmd.getOptionValue("gd")));
//                        new Graphics().graphicsDevice();
//                    }
//                } else {
//                    //vai existir sempre um, pelo que fica no default
//                }
//            }
            
            if (cmd.hasOption("l") && !cmd.hasOption("p")) {
                log.log(Level.SEVERE, "Missing p option");
                help();
            }
            
            if (cmd.hasOption("p") && !cmd.hasOption("l")) {
                log.log(Level.SEVERE, "Missing l option");
                help();
            }
            
            if (cmd.hasOption("d") && !cmd.hasOption("l")) {
                log.log(Level.SEVERE, "Missing l option");
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
            
            if (cmd.hasOption("d")) {
                log.log(Level.INFO, "Using argument -d={0}", cmd.getParsedOptionValue("d"));
                EMAIL_DEST = cmd.getOptionValue("d");
            }

            if (cmd.hasOption("pl")) {
                log.log(Level.INFO, "Using argument -pl={0}", cmd.getParsedOptionValue("pl"));
                
                if (Integer.parseInt(cmd.getOptionValue("pl")) > 0)
                {
                PAGELOADING = Integer.parseInt(cmd.getOptionValue("pl"));
                }
                else
                {
                    log.log(Level.SEVERE, "pl needs to be > 0");
                }
                
            }

            if (cmd.hasOption("lp")) {
                log.log(Level.INFO, "Using argument -lp={0}", cmd.getParsedOptionValue("lp"));
                
                if (Integer.parseInt(cmd.getOptionValue("lp")) > 0)
                {
                LOADINGPOKEMONGOS = Integer.parseInt(cmd.getOptionValue("lp"));
                }
                else
                {
                    log.log(Level.SEVERE, "lp needs to be > 0");
                }
                
                
            }
            
            if (cmd.hasOption("md")) {
                log.log(Level.INFO, "Using argument -md={0}", cmd.getParsedOptionValue("md"));
                if (Integer.parseInt(cmd.getOptionValue("md")) > 0)
                {
                MAPDIMENSION = Integer.parseInt(cmd.getOptionValue("md"));
                }
                else
                {
                    log.log(Level.SEVERE, "md needs to be > 0");
                }
                
            }
            
            if (cmd.hasOption("px") && !cmd.hasOption("py")) {
                log.log(Level.INFO, "py: default value");
            }
            if (cmd.hasOption("py") && !cmd.hasOption("px")) {
                log.log(Level.INFO, "px: default value");
            }
            if (cmd.hasOption("px")) {
                log.log(Level.INFO, "Using argument -px={0}", cmd.getParsedOptionValue("px"));
                XCOORD = Integer.parseInt(cmd.getOptionValue("px"));
            }
            if (cmd.hasOption("py")) {
                log.log(Level.INFO, "Using argument -py={0}", cmd.getParsedOptionValue("py"));
                YCOORD = Integer.parseInt(cmd.getOptionValue("py"));
            }
            
            if (cmd.hasOption("z")) {
                log.log(Level.INFO, "Using argument -z={0}", cmd.getParsedOptionValue("z"));
                ZOOMINOROUT = Integer.parseInt(cmd.getOptionValue("z"));
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
