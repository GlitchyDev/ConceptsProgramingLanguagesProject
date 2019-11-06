package com.DCB;

import com.DCB.ParserObjects.CoupledObject;

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
        for(int i = 0; i < parser.getAnalyzedScript().size(); i++) {
            Object o = parser.getAnalyzedScript().get(i);
            if(o instanceof CoupledObject) {
                System.out.println(((CoupledObject) o).getParsedGrammar());
            } else {
                System.out.println(o + " ||| " + parser.getLineNumbers().get(i));
            }
        }
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
