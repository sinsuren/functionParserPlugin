### Function class hold three attribute of function signature
  1) List of argument types
  2) Name of the function
  3) Does function takes variable arguments as input(isVariadic)
 
### FunctionSearchPlugin exposes two function.
  1) Register functions set
  2) Search function based on the query parameters type.
  
### Eg.
  1) functionA(Integer, Integer, Integer)
  2) functionB(Boolean, Integer, Integer)
  3) functionC(Integer, Integer...)
  4) functionD(Boolean, Integer...)
  5) functionE(Integer...)
  
Above functions are defined in some class. Now user wants to search for functions based on below query.
Query:
  1) [Integer] should return functionC, functionE because both these functions can accept single Integer params.
  2) [Integer, Integer, Integer] should return functionA, functionC, functionE
