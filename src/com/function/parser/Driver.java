package com.function.parser;


import java.util.ArrayList;
import java.util.List;


public class Driver {


    public static void main(String[] args) {


        /**
         funcA(Boolean, Integer , Integer)
         funcB(Integer, Integer, Integer)
         funcC(Integer...)
         **/

        List<String> functionArgumentOfA = new ArrayList<>();
        functionArgumentOfA.add("Boolean");
        functionArgumentOfA.add("Integer");
        functionArgumentOfA.add("Integer");

        Function functionA = new Function("A", false, functionArgumentOfA);


        List<String> functionArgumentOfB = new ArrayList<>();
        functionArgumentOfB.add("Integer");
        functionArgumentOfB.add("Integer");
        functionArgumentOfB.add("Integer");

        Function functionB = new Function("B", false, functionArgumentOfB);

        List<String> functionArgumentOfC = new ArrayList<>();
        functionArgumentOfC.add("Integer");

        Function functionC = new Function("C", true, functionArgumentOfC);

        FunctionSearchPlugin searchPlugin = new FunctionSearchPlugin();

        List<Function> functions = new ArrayList<>();

        functions.add(functionA);
        functions.add(functionB);
        functions.add(functionC);

        searchPlugin.register(functions);
        //Search for functions now
        //Integer, Integer, Integer should return B and C

        List<String> firstSearch = new ArrayList<>();
        firstSearch.add("Integer");
        firstSearch.add("Integer");
        firstSearch.add("Integer");

        List<Function> searchResponse = searchPlugin.searchFunctions(firstSearch);
        System.out.format("Query: %s", String.join(", ", firstSearch) + "\n");
        System.out.println("Response: ");
        printFunctionSignature(searchResponse);


        List<String> secondSearch = new ArrayList<>();
        secondSearch.add("Integer");
        secondSearch.add("Integer");
        secondSearch.add("Integer");
        secondSearch.add("Integer");
        secondSearch.add("Integer");
        secondSearch.add("Integer");

        searchResponse = searchPlugin.searchFunctions(secondSearch);
        System.out.format("Query: %s", String.join(", ", secondSearch) + "\n");
        System.out.println("Response: ");
        printFunctionSignature(searchResponse);

    }

    public static void printFunctionSignature(List<Function> functions) {

        for (Function function : functions) {

            System.out.print(function.name + "(" + String.join(", ", function.arguments));
            if (function.isVariadic) {
                System.out.print(" ...");
            }

            System.out.println(")");
        }
    }
}