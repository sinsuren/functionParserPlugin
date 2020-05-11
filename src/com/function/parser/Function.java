package com.function.parser;

import java.util.List;

public class Function {
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
