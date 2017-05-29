/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.commandsparser4J.parser;

import static fr.commandsparser4J.parser.Option.PREFIX_OPTION_FULLNAME;
import static fr.commandsparser4J.parser.Option.PREFIX_OPTION_SHORTCUT;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Come CACHARD
 * 
 * Object that parses command line arguments and create a command line that 
 * describes the options given by the user.
 */
public class CommandLineParser {
    
    /**
     * The list of all available options.
     */
    private final OptionsList optionsList;
    
    /**
     * Constructs and initializes a command line parser with the available options 
     * that can be given and considered.
     * @param optionsList the available options that can be given and considered
     */
    public CommandLineParser(OptionsList optionsList) {
        this.optionsList = optionsList;
    }
    
    /**
     * Parses commands given in arguments and returns a command line containing 
     * the options given by the user and their values.
     * @param args the commands to parse
     * @return a command line containing the options given by the user and their values
     */
    public CommandLine parse(String[]args){
        CommandLine cmd = new CommandLine(optionsList);
        
        //boolean used in order to be sure that we have options before their values.
        //Initialized to false because we must start with an optionName.
        boolean lookingForArgumentValuesAvailable = false;
        //used to know if the arg is an optionName or a value.
        boolean isArgAnOption = false;
        String optionName = null;
        //tampon name
        String futureOptionName=null;
        List<String> values = null;

        int i = 0;
        for (String arg : args) {

            isArgAnOption = false;
            
            if(arg.startsWith(PREFIX_OPTION_FULLNAME)){
                isArgAnOption = true;
                futureOptionName = arg.substring(PREFIX_OPTION_FULLNAME.length());
            }else if(arg.startsWith(PREFIX_OPTION_SHORTCUT)){
                isArgAnOption = true;
                futureOptionName = optionsList.getOptionNameWithShortcut(arg.substring(PREFIX_OPTION_SHORTCUT.length()));
            }
            
            if(isArgAnOption){
                
                if(values != null){
                    cmd.addGivenOption(optionName,values);
                }
                
                optionName = futureOptionName;
                values = new LinkedList();
                
                lookingForArgumentValuesAvailable = true;
                
            }else{

                //We cannot have an argument before an optionName
                if(lookingForArgumentValuesAvailable == false){
                    throw new RuntimeException("Error : an argument has been given without an option.");
                }
                
                values.add(arg);
            }
            
            //if we are considering the last arg, and that we are going to go out of the loop.
            if(i == args.length-1){
                cmd.addGivenOption(optionName,values);
            }
                
            i++;
        }
                
        checkRequiredOptions(cmd);

        return cmd;
    }
    
    /**
     * Checks if all the required otions have been given by the user. 
     * If some are missing we throw an exception.
     * @param cmd the command line that contains the options given by the user.
     */
    private void checkRequiredOptions(CommandLine cmd){
        Set<String> requiredOptions = optionsList.getRequiredOptions().keySet();
        Map<String, List<String>> givenOptions = cmd.getGivenOptions();
        List<Option> missingOptions = new LinkedList<>();
        
        for(String requiredKey:requiredOptions){
            if (!givenOptions.containsKey(requiredKey)){
                missingOptions.add(optionsList.getOptionWithName(requiredKey));
            }
        }
        
        if(!missingOptions.isEmpty()){
            StringBuilder sb = new StringBuilder("Error : the following options have not been given while required :");
        
            for(Option o : missingOptions){
                sb.append("\n").append("\t").append(o.getUsage());
            }

            throw new RuntimeException(sb.toString());
        }
        
    }

    
    /**
     * Prints the usage of the application to the user.
     * @param applicationName the name of the application
     */
    public void printUsage(String applicationName){
        StringBuilder sb = new StringBuilder();
        sb.append("Usage : ").append(applicationName);
        
        sb.append("\n").append("\t").append("Required options :");
        optionsList.getRequiredOptions().values().stream().forEach((requiredOption) -> {
            sb.append("\n").append("\t").append("\t").append(requiredOption.getUsage());
        });
        sb.append("\n");
        sb.append("\n").append("\t").append("Optional options :");
        optionsList.getOptionalOptions().values().stream().forEach((optionalOption) -> {
            sb.append("\n").append("\t").append("\t").append(optionalOption.getUsage());
        });
        
        System.out.println(sb.toString());
    }
}
