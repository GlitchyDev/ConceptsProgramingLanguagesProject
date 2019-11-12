package com.DCB;

import com.DCB.ParserObjects.CoupledObject;
import com.DCB.ParserObjects.CouplingControlStatement;
import com.DCB.ParserObjects.CouplingStatement;
import com.DCB.ParserObjects.Couplings.ControlStatements.CouplingForStatement;
import com.DCB.ParserObjects.Couplings.ControlStatements.CouplingIfStatement;
import com.DCB.ParserObjects.Couplings.ControlStatements.CouplingWhileStatement;

import java.io.*;
import java.util.Scanner;

/*
 * Class:       CS 4308 Section 2
 * Term:        Fall 2019
 * Name:        Robert, Chris, James
 * Instructor:   Deepa Muralidhar
 * Project:  Deliverable 1 Scanner - Java
 */

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("Files/TestDocument.txt");
        Scanner scanner = new Scanner(file);
        ScriptReader scriptReader = new ScriptReader(scanner);
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(scriptReader);
        for(Object o: lexicalAnalyzer.getAnalyzedScript()) {
            System.out.println(o);
        }
        try {
            usingBufferedWriter(lexicalAnalyzer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("[Identifiers]=========================");
        Parser parser = new Parser(lexicalAnalyzer.getAnalyzedScript(),lexicalAnalyzer.getScriptLines(),lexicalAnalyzer.getCurrentLineNumber());
        for(int i = 0; i < parser.getAnalyzedScript().size(); i++) {
            Object o = parser.getAnalyzedScript().get(i);
            if(o instanceof CoupledObject) {
                System.out.println(((CoupledObject) o).getStringIdentifier());
            } else {
                System.out.println(o + " ||| " + parser.getLineNumbers().get(i));
            }
        }
        System.out.println("[Grammer]=========================");
        System.out.println("\n<program> -> function id ( ) <block> end\n");
        if(parser.getAnalyzedScript().get(parser.getAnalyzedScript().size()-1) instanceof CouplingStatement) {
            ((CouplingStatement) parser.getAnalyzedScript().get(parser.getAnalyzedScript().size()-1)).setLateStatement();
        }
        for(int i = 0; i < parser.getAnalyzedScript().size(); i++) {
            Object o = parser.getAnalyzedScript().get(i);
            if(o instanceof CoupledObject) {
                System.out.println(((CoupledObject) o).getParsedGrammar());
            } else {
                System.out.println(o + " ||| " + parser.getLineNumbers().get(i));
            }
        }
        int i = 0;
        // write your code here
    }


    public static void usingBufferedWriter(LexicalAnalyzer lexicalAnalyzer) throws IOException
    {
        String fileContent = "";
        for(Object o: lexicalAnalyzer.getAnalyzedScript()) {
            fileContent += o + "\n";
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter("Files/OutputFile.txt"));
        writer.write(fileContent);
        writer.close();
    }
}
