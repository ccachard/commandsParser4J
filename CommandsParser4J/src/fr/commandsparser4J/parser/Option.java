/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.commandsparser4J.parser;

/**
 *
 * @author Come CACHARD
 * 
 * Describes an option in command line.
 */
public class Option {
    
    /**
     * A possible prefix for an optionName to be considered as one. 
     * This prefix is used for shortcut.
     * If it does not start with that prefix in the command line, it will be considered as the value of an optionName.
     */
    public static final String PREFIX_OPTION_SHORTCUT = "-";
    
    /**
     * A possible prefix for an optionName to be considered as one.
     * This prefix is used for fullname.
     * If it does not start with that prefix in the command line, it will be considered as the value of an optionName.
     */
    public static final String PREFIX_OPTION_FULLNAME = "--";
    
    /**
     * If true, the option is necessary to be given; otherwise it is optional.
     */
    private final boolean isRequired;
    
    /**
     * If true, the option has an argument; otherwise not.
     */
    private final boolean hasArgument;
    
    /**
     * The name of this Option.
     */
    private final String name;
    
    /**
     * The shortcut associated to this option.
     */
    private final String shortcut;
    
    /**
     * The description of this option that will be displayed in usage() of the application.
     */
    private final String description;

    /**
     * Constructs and initializes an Option with the specified parameters.
     * @param name the name of this Option
     * @param shortcut the shortcut associated to this option
     * @param isRequired if true, the option is necessary to be given; otherwise it is optional
     * @param hasArgument if true, the option has an argument; otherwise not
     * @param description the description of this option that will be displayed in usage() of the application
     */
    public Option(String name, String shortcut, boolean isRequired, boolean hasArgument, String description) {
        this.isRequired = isRequired;
        this.hasArgument = hasArgument;
        this.name = name;
        this.shortcut = shortcut;
        if (!description.endsWith(".")){
            this.description = description + ".";
        }else{
            this.description = description;
        }
    }

    /**
     * Returns true if this Option is mandatory; otherwise false.
     * @return true if this Option is mandatory; otherwise false
     */
    public boolean isRequired() {
        return isRequired;
    }

    /**
     * Returns true if this Option has an argument; otherwise false.
     * @return true if this Option has an argument; otherwise false
     */
    public boolean hasArgument() {
        return hasArgument;
    }

    /**
     * Returns the name of this Option.
     * @return the name of this option
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the shortcut of this Option.
     * @return the shortcut of this Option
     */
    public String getShortcut() {
        return shortcut;
    }

    /**
     * Returns the description of this Option.
     * @return the description of this Option
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * Returns the usage of this Option.
     * @return the usage of this Option
     */
    public String getUsage(){
        StringBuilder sb = new StringBuilder();
        if(!shortcut.isEmpty()){
            sb.append(PREFIX_OPTION_SHORTCUT).append(shortcut);
            sb.append(" ");
        }
        sb.append(PREFIX_OPTION_FULLNAME).append(name);
        sb.append(" : ");
        /*if(isRequired){
            sb.append("(Required) ");
        }else{
            sb.append("(Optional) ");
        }*/
        sb.append(description);
                
        return sb.toString();
    }
    
}
