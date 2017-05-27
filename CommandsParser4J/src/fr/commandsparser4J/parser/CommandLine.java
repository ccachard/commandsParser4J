/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.commandsparser4J.parser;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Come CACHARD
 * 
 * Describes a list of options of a command line.
 */
public class CommandLine {
    
    /**
     * The map of options in command lines that are given by the user mapped to their values if they have one. 
     * Else the required option is mapped to an empty List if it does not have an argument value.
     */
    private final Map<String,List<String>> givenOptions;
    
    /**
     * The list of all available options.
     */
    private final OptionsList optionsList;

    /**
     * Constructs and initializes a command line with the available options 
     * that can be given and considered.
     * @param optionsList the available options that can be given and considered
     */
    public CommandLine(OptionsList optionsList) {
        this.optionsList = optionsList;
        givenOptions = new HashMap<>();
    }
    
    /**
     * Adds to the list of options given by the user a new option.
     * @param optionName the name of the option given by the user
     * @param values the values associated to the option
     */
    public void addGivenOption(String optionName, List<String>values){
        if(givenOptions.containsKey(optionName)){
            throw new RuntimeException("Error : The option "+optionName+" is used more than once.");
        }
        
        if(values == null){
            throw new RuntimeException("Error : values cannot be null. If the option has no arguments, it is an empty list.");
        }
        
        Option option = optionsList.getOptionWithName(optionName);
        if(option == null){
            throw new RuntimeException("Error : the option \""+optionName+"\" is not recognised.");
        }
        if(option.hasArgument() && values.isEmpty()){
            throw new RuntimeException("Error : the option \""+optionName+"\" requires arguments  but has not any.");
        }
        
        givenOptions.put(optionName,values);
    }
    
    /**
     * Returns true if the option is used by the user; otherwise false.
     * @param optionName the option we want to consider
     * @return true if the option is used by the user; otherwise false
     */
    public boolean isOptionGivenByUser(String optionName){
        return givenOptions.containsKey(optionName);
    }
    
    /**
     * Returns a non modifiable view of the values associated to an otpion if given by the user; null otherwise.
     * @param optionName the name of the option we want the values
     * @return a non modifiable view of the values associated to an otpion if given by the user; null otherwise.
     */
    public List<String> getValuesForOption(String optionName){
        if (givenOptions.containsKey(optionName)){
            return Collections.unmodifiableList(givenOptions.get(optionName));
        }
        return null;
    }
    
    /**
     * Returns the values of the arguments of an option if they are given by the user; null otherwise.
     * @param optionName the name of the option we want the values of its arguments
     * @return the values of the arguments of an option if they are given by the user; null otherwise.
     */
    public List<String> getOptionArgumentValue(String optionName){
        Option option = optionsList.getOptionWithName(optionName);
        if(!option.hasArgument()){
            throw new RuntimeException("Error : the specified option does not take arguments.");
        }
        return givenOptions.get(option.getName());
    }

    /**
     * Returns a non modifiable view of the options contained in the command line.
     * @return a non modifiable view of the options contained in the command line
     */
    public Map<String, List<String>> getGivenOptions() {
        return Collections.unmodifiableMap(givenOptions);
    }

    /**
     * Returns the options that are considered for this command line.
     * @return the options that are considered for this command line
     */
    public OptionsList getOptionsList() {
        return optionsList;
    }
    
    
}
