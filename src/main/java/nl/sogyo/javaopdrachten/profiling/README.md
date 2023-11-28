h1. Profiling

The assignments for profiling (A and B in the intermediate track) are on the academy website. This readme contains a few hints to start searching.

The general documentation is at https://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html.

Java uses `Streams` to allow lazy evaluation. You can chain operations on a stream without evaluating the result. All applied operations are evaluated element-wise when the stream is iterated (consumed). An operation on a stream is given by a function call on the stream that tells the stream what to do with each element (e.g. `filter` or `map`). The input for those functions is another function that meets the required interface. For example `filter` requires a function that accepts an element from the stream and returns a `boolean`. This interface is called a `Predicate<T>`, where `T` is the element type of the stream.

For assignment 1, you will need a `Predicate`. Assignment 2 focusses on `Function`. You might want to read about `Consumer`s for assignment 3. For assignments 4 and 5, you are on your own.