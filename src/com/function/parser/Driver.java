package com.function.parser;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Driver {

    static class Function {
        public String name;
        public List<String> arguments;
        //Is variadic true represents that function's last argument is of ellipsis type. (...)
        public Boolean isVariadic;

        public Function(String name, Boolean isVariadic, List<String> arguments) {
            this.isVariadic = isVariadic;
            this.name = name;
            this.arguments = arguments;
        }
    }

    static class FunctionSearchPlugin {

        private Map<String, List<Function>> dataStore = new HashMap<>();

        public void register(List<Function> functions) {
            for (Function function : functions) {

                if (function.isVariadic) {

                    String zeroOccurrence = getKeyWithZeroOccurrence(function.arguments);
                    String oneOccurrence = getKeyWithOneOccurrence(function.arguments);
                    String moreThanOneOccurrence = getKeyWithMoreThanOneOccurrence(function.arguments);

                    insert(zeroOccurrence, function);
                    insert(oneOccurrence, function);
                    insert(moreThanOneOccurrence, function);

                } else {
                    String getNonVariadicKey = getNonVariadicKey(function.arguments);

                    insert(getNonVariadicKey, function);
                }
            }
        }

        public List<Function> searchFunctions(List<String> arguments) {
            List<Function> answer = new ArrayList<>();

            String nonVariadic = getNonVariadicKey(arguments);

            if (dataStore.containsKey(nonVariadic)) {
                answer.addAll(dataStore.get(nonVariadic));
            }

            List<String> argumentsCopy = new ArrayList<>(arguments);

            while (isLastTwoParamsSame(argumentsCopy)) {
                argumentsCopy.remove(argumentsCopy.size() - 1);

                String key = getKeyWithMoreThanOneOccurrence(argumentsCopy);

                if (dataStore.containsKey(key)) {
                    answer.addAll(dataStore.get(key));
                }
            }

            return answer;
        }


        private Boolean isLastTwoParamsSame(List<String> arguments) {

            if (arguments.size() < 2) {
                return false;
            }

            if (arguments.get(arguments.size() - 1).equals(arguments.get(arguments.size() - 2))) {
                return true;
            }

            return false;
        }

        private String getNonVariadicKey(List<String> attributes) {
            if (attributes.size() == 0) {
                return "";
            }

            return String.join("_", attributes);
        }


        private String getKeyWithOneOccurrence(List<String> attributes) {
            return getNonVariadicKey(attributes);
        }

        private String getKeyWithZeroOccurrence(List<String> attributes) {

            if (attributes.size() <= 1) {
                return "";
            }

            List<String> attributeCopy = new ArrayList<>(attributes);

            attributeCopy.remove(attributeCopy.size() - 1);


            return getNonVariadicKey(attributeCopy);
        }

        private String getKeyWithMoreThanOneOccurrence(List<String> attributes) {
            return getNonVariadicKey(attributes) + "_*";
        }

        private void insert(String key, Function function) {

            if (!dataStore.containsKey(key)) {
                dataStore.put(key, new ArrayList<>());
            }

            dataStore.get(key).add(function);
        }
    }


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