/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.commandsparser4J.parser;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author comecachard
 * 
 * Object that parses command line arguments and create a command line that 
 * describes the options given by the user.
 */
public class CommandLineParser {
    
    /**
     * A possible prefix for an optionName to be considered as one. 
     * If it does not start with that prefix in the command line, it will be considered as the value of an optionName.
     */
    private final String PREFIX_OPTION_1 = "-";
    
    /**
     * A possible prefix for an optionName to be considered as one.
     * If it does not start with that prefix in the command line, it will be considered as the value of an optionName.
     */
    private final String PREFIX_OPTION_2 = "--";
    
    /**
     * The map of options in command lines that are required with their value if they have at least one. 
     * Else the required optionName is mapped to an empty list if it does not have an argument value.
     */
    private final Map<String,List<String>> requiredOptionsValues;
    
    /**
     * The map of options in command lines that are optional with their value if they have at least one. 
     * Else the required optionName is mapped to an empty list if it does not have an argument value.
     */
    private final Map<String,List<String>> optionalOptionsValues;
    
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
        optionalOptionsValues = new HashMap<>();
        requiredOptionsValues = new HashMap<>();
    }
    
    public CommandLine parse(String[]args){
        CommandLine cmd = new CommandLine(optionsList);
        
        //boolean used in order to be sure that we have options before their values.
        //Initialized to false because we must start with an optionName.
        boolean lookingForArgumentValuesAvailable = false;
        
        String optionName = null;
        //tampon name
        String futureOptionName=null;
        List<String> values = null;

        int i = 0;
        for (String arg : args) {

            //used to know if the arg is an optionName or a value.
            boolean isArgAnOption = false;
            
            if(arg.startsWith(PREFIX_OPTION_2)){
                isArgAnOption = true;
                futureOptionName = arg.substring(PREFIX_OPTION_2.length());
            }else if(arg.startsWith(PREFIX_OPTION_1)){
                isArgAnOption = true;
                futureOptionName = optionsList.getOptionNameWithShortcut(arg.substring(PREFIX_OPTION_1.length()));
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
            
        }
        
        for(Map.Entry<String,List<String>>anyItem:cmd.getGivenOptions().entrySet()){
            System.out.println("");
            String arguments= "";
            for(String s : anyItem.getValue()){
                arguments += s;
                arguments+=" ";
            }
            System.out.println(anyItem.getKey()+" "+ arguments);
        }
        
        return cmd;
    }
    
    
    
}
