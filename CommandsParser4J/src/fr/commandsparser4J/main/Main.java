/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.commandsparser4J.main;

import fr.commandsparser4J.parser.CommandLineParser;
import fr.commandsparser4J.parser.Option;
import fr.commandsparser4J.parser.OptionsList;

/**
 *
 * @author Come CACHARD
 * 
 * Example of usage
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Option o1 = new Option("output", "out", true, true, "the output file name.");
        Option o2 = new Option("input", "in", true, 2, "the output file name.");
        Option o3 = new Option("origami", "o", false, false, "is it an origami");
        Option o4 = new Option("size", "s", false, true, "the size of the object.");
        Option o5 = new Option("lol", "l", true, false, "do we want to laugh");
        Option o6 = new Option("lola", "", false, true, "what is lola");
        OptionsList options = new OptionsList();
        
        try{
            options.addOption(o1);
            options.addOption(o2);
            options.addOption(o3);
            options.addOption(o4);
            options.addOption(o5);
            options.addOption(o6);
        }catch(Exception e){
            System.err.println(e.getMessage());
            System.exit(1);
        }
        
        CommandLineParser cmdParser = new CommandLineParser(options);
        cmdParser.printUsage("CommandsParser4J");
        try{
            cmdParser.parse(args);
        }catch(Exception e){
            System.err.println(e.getMessage());
            System.err.println("");
            cmdParser.printUsage("CommandsParser4J");
            System.exit(1);
        }
        
        
    }
    
}
