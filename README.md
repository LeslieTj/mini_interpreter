# mini_interpreter

A mini-interpreter for a toy programming language that allows the following:

1. The use of variables that consist of a single letter (e.g. A, a, ...)

2. The use of whole numbers: ( e.g. -1, -20, 0, 1, 200)

3. Assignment (=): ( e.g. A = B, A = 10, )

4. Addition of exactly two elements (variables or constants) (+) : ( e.g. C = A + B, D = 1 + A, ... )

5. The ability to "return" a value when a single variable or constant is on a line by itself ( e.g. A, B, 10)

For example, if we have the following program:

```Java
String program = "A = 2\n" +
                "B = 8\n" +
                "C = A + B\n" +
                "C";
```
When we execute the program by calling `executeProgram(program)`, the program will return `10`, i.e., `C = 10`.

The mini interpreter also has a debug flag. If the debug flag is true, the interpreter would print the debug information like this:

```
[1, A, 2]
[A]
[2]
-------------------------
[1, B, 8]
[A, B]
[2, 8]
-------------------------
[0, C, A, B]
[A, B, C]
[2, 8, 10]
-------------------------
Returning...
Your program: 
A = 2
B = 8
C = A + B
C
Your program returned: 10
```