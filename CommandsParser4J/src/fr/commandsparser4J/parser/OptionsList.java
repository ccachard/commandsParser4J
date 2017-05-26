/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.commandsparser4J.parser;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Come CACHARD
 * 
 * Object that contains the list of available options.
 */
public class OptionsList {
    
    /**
     * Map that contains all the available required options mapped by their name.
     */
    private final Map<String,Option> requiredOptions;
    
    /**
     * Map that contains all the available optional options mapped by their name.
     */
    private final Map<String,Option> optionalOptions;

    /**
     * Map that contains the mapping between the shortcut of an option and its name;
     */
    private final Map<String,String> optionsShortcuts;
    
    /**
     * Constructs and initializes a list of options with an empty list of options.
     */
    public OptionsList() {
        requiredOptions = new HashMap<>();
        optionalOptions = new HashMap<>();
        optionsShortcuts = new HashMap<>();
    }

    /**
     * Returns a non modifiable view of the required options.
     * @return a non modifiable view of the required options
     */
    public Map<String,Option> getRequiredOptionsList() {
        return Collections.unmodifiableMap(requiredOptions);
    }

    /**
     * Returns a non modifiable view of the optional options.
     * @return a non modifiable view of the optional options
     */
    public Map<String,Option> getOptionalOptionsList() {
        return Collections.unmodifiableMap(optionalOptions);
    }
    
    /**
     * Returns a non modifiable view of the available options.
     * @return a non modifiable view of the available options
     */
    public Map<String,Option> getOptionsList(){
        Map<String,Option> optionsList = new HashMap<>(requiredOptions);
        optionsList.putAll(optionalOptions);
        return Collections.unmodifiableMap(optionsList);
    }

    /**
     * Return a non modifiable view of the mapping between option's shortcut and their name.
     * @return a non modifiable view of the mapping between option's shortcut and their name
     */
    public Map<String, String> getOptionsShortcutsMapping() {
        return Collections.unmodifiableMap(optionsShortcuts);
    }
    
    /**
     * Adds an option to the list of available options.
     * @param option the option to add to the list of available options.
     */
    public void addOption(final Option option){
        if(option.isRequired()){
            requiredOptions.put(option.getName(),option);
        }else{
            optionalOptions.put(option.getName(),option);
        }
        
        optionsShortcuts.put(option.getShortcut(), option.getName());
    }
    
    /**
     * Returns the Option associated to the given name if found in the list of available options;
     * otherwise returns null if Option not found.
     * @param optionName the name of the Option we want
     * @return the Option associated to the given name if found; null otherwise
     */
    public Option getOptionWithName(final String optionName){
        if(requiredOptions.containsKey(optionName)){
            return requiredOptions.get(optionName);
        }else if (optionalOptions.containsKey(optionName)){
            return optionalOptions.get(optionName);
        }
        
        return null;
    }
    
    /**
     * Returns the Option associated to the given shortcut if found in the list of available options;
     * otherwise returns null if Option not found.
     * @param optionShortcut the shortcut of the Option we want
     * @return the Option associated to the given shortcut if found; null otherwise
     */
    public Option getOptionWithShortcut(final String optionShortcut){
        String optionName = optionsShortcuts.get(optionShortcut);
        
        if (optionName == null){
            return null;
        }
        
        return getOptionWithName(optionName);
    }
    
    /**
     * Returns the name of the option associated to the given shortcut. Returns null if not found.
     * @param shortcut the shortcut of the option we want the full name
     * @return the name of the option associated to the given shortcut. Returns null if not found.
     */
    public String getOptionNameWithShortcut(final String shortcut){
        return optionsShortcuts.get(shortcut);
    }
}
