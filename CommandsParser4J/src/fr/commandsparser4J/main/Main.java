/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.commandsparser4J.main;

import fr.commandsparser4J.parser.CommandLineParser;
import fr.commandsparser4J.parser.Option;
import fr.commandsparser4J.parser.OptionsList;
import jdk.nashorn.internal.runtime.options.Options;

/**
 *
 * @author Come CACHARD
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        /*for(String arg:args){
            System.out.println("arg : "+arg);
        }*/
        
        Option o1 = new Option("output", "out", true, true, "the output file name.");
        Option o2 = new Option("input", "in", true, true, "the output file name.");
        Option o3 = new Option("origami", "o", false, false, "is it an origami");
        Option o4 = new Option("size", "s", false, true, "the size of the object.");
        Option o5 = new Option("lol", "l", true, false, "do we want to laugh");
        Option o6 = new Option("lola", "", false, true, "what is lola");
        
        OptionsList options = new OptionsList();
        options.addOption(o1);
        options.addOption(o2);
        options.addOption(o3);
        options.addOption(o4);
        options.addOption(o5);
        options.addOption(o6);
        
        CommandLineParser cmdParser = new CommandLineParser(options);
        
        cmdParser.parse(args);
        
    }
    
}
