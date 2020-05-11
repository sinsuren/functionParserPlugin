package com.function.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FunctionSearchPlugin {

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
