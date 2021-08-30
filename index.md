_Luwak is a project to make it easier to write functional code in the Java Language. It provides aliases to write the code a lot shorter and extends the language with extra objects. Unlike libraries like [Functional Java](https://www.functionaljava.org) and [Vavr](https://www.vavr.io), this project does not have the intention to replace native objects like the Java Stream._

With Luwak you could write this:
```java
Ø<String> getFirstMatch(List<Ø<String>> list, ℙ<String> p) {
    return Ø.of(list.stream()
        .flatMap(Ø::stream)
        .filter(p)
        .findFirst());
}
```

### Aliases

Java added a lot of new classes with version 8 to grant some possibility of writing code in a functional way. To work with them without writing a lot of code, this library provides aliases of these classes. The aliases are created by subclassing the original interface / class.

#### Supplier
Use the `$` symbol:
```java
Supplier<String> old = () -> "x";
$<String> now = () -> "x";
```

#### Function
Use the `ƒ` symbol:
```java
Function<String, String> old = String::toUpperCase;
ƒ<String, String> now = String::toUpperCase;
```
#### Consumer
Use the `₵` symbol:
```java
Consumer<String> old = x -> {};
₵<String> now = x -> {};
```

#### Predicate
Use the `ℙ` symbol:
```java
Predicate<String> old = s -> s.contains("x");
ℙ<String> now = s -> s.contains("x");
```

#### Runnable
Use the `ℝ` symbol:
```java
Runnable old = () -> {};
ℝ now = () -> {};
```

#### Optional
Use the `Ø` symbol:
```java
Optional<String> old = Optional.of("x");
Ø<String> now = Ø.of("x");
```
_:warning: The `Ø` is implemented as wrapper class of Optional due the Optional class being defined as final. This makes the use of this alias rather clumsy, thus this alias is defined as experimental._

### lambdas
When you write lambdas, you have to take some quirks into account. Luwak provide some options to workaround some problems.

#### Checked functions
The Java compiler does not let you write lambdas with checked exceptions. Therefore the library provides checked functions to bypass this problem. All above functional interfaces do have a checked variant; to keep it short and consise, the same symbols are used with a `_` prefix. With other words, the CheckedFunction does look like `_ƒ`. All the default functional interfaces do have a `__` function to wrap a checked variant.

Example:
```java
List.of("items?price=gte:10&price=lte:100").stream()
    .map(ƒ.__(s -> URLEncoder.encode(s, "UTF-8"))) // or `__(...)` with a static import
    .collect(toList());
```

#### Recursion
Java makes it's impossible to use lambdas with references to itself. With a small helper¹ it is possible to go around this:
```java
ƒ<Integer, Integer> fact = Recursable.recurse((i, f) -> 0 == i ? 1 : i * f.apply(i - 1, f));
```

Recursion can lead to a StackOverflowException when the input is big. With build in tail-call optimization² it is safe to do big computations:
```java
ƒ<BigDecimal, BigDecimal> fact = Recursable.tailRecurse((i, acc) ->
    0 == acc.intValue() ? ret(ONE) :
    1 == acc.intValue() ? ret(i) :
    next(() -> i.multiply(acc.subtract(ONE)), () -> acc.subtract(ONE)));
```

### Extension
To create even less verbose code, the following objects provide extra functionality.

#### Result
The result object can have two states, either successful or failure. Use the `Œ` symbol:
```java
ƒ<String, HttpRequest> get = url -> HttpRequest.newBuilder().uri(URI.create(url)).GET().build();

HttpClient client = HttpClient.newHttpClient();
Œ<HttpResponse<String>> result = doTry(() -> client.send(get.apply("http://openjdk.java.net/"), ofString()));
```

### Utility
Some actions like mapping, filtering and finding an element in a List are so common, it is a pity you need a lot of streaming code to write it down. Luwak provides a `Do` class to shorten these actions:
```java
List<String> lo
rCaseString = List.of("Example 1", "Example 2").stream().map(String::toLowerCase).collect(Collectors.toList());
List<String> lowerCaseString = Do.map(List.of("Example 1", "Example 2"), String::toLowerCase);

List<String> onlyFirstExample = List.of("Example 1", "Example 2").stream().filter(s -> s.equals("Example 1")).collect(Collectors.toList());
List<String> onlyFirstExample = Do.filter(List.of("Example 1", "Example 2"), s -> s.equals("Example 1"));

Optional<String> possibleFirstExample = List.of("Example 1", "Example 2").stream().filter(s -> s.equals("Example 1")).findAny();
Ø<String> possibleFirstExample = Do.findAny(List.of("Example 1", "Example 2"), s -> s.equals("Example 1"));

boolean matched = List.of("Example 1", "Example 2").stream().anyMatch(s -> s.equals("Example 1"));
boolean matched = anyMatch(List.of("Example 1", "Example 2"), s -> s.equals("Example 1")); // by importing `Do` statically
```

_:information_source: By using [Project Lombok's](https://projectlombok.org) extension methods, the utility methods could written like native syntax:_
```java
@ExtensionMethod({ Do.class })
public class Example {
    public static void main(String[] args) {
        List.of("Example 1", "Example 2").filter(s -> s.equals("Example 1"));
    }
}
```

### Why Luwak?
Kopi luwak is a coffee that consists of partially digested coffee cherries, which have been eaten and defecated by the Asian palm civet. In Indonesia this animal is called luwak. The process of creation can be written elegantly functional:
```java
ƒ<CoffeeCherry, CoffeeCherry> eat = Luwak::eat;
ƒ<CoffeeCherry, Feces> digest = Intestines::digest;
ƒ<Feces, Feces> defecate = Body::leave;
ƒ<Feces, Core> cleanup = f -> CoffeeTeam.search(f).clean();
ƒ<Core, Bean> roast = CoffeeRoasterMachine:go;

ƒ<CoffeeCherry, Bean> processCoffee = eat.andThen(digest).andThen(defecate).andThen(cleanup).andThen(roast).apply(new CoffeeCherry());
```
As the Java programming language is called after the Java coffee, everything comes beautifully together.

### Credits
The used favicon is a free vector image by VectorStock. You can find it [here](https://www.vectorstock.com/royalty-free-vector/asian-palm-civet-head-face-vector-33880719).

¹ The [helper](https://stackoverflow.com/a/35997193/2049986) is posted by [Ian Robertson](https://stackoverflow.com/users/333675/ian-robertson) at Stack Overflow. Stack Overflow uses the Creative Commons Attribution-ShareAlike licence, click [here](https://stackoverflow.com/help/licensing) if you want to know more.  
² Examples of runtime tail-call optimization for Java can be found in [Functional Programming in Java](http://www.r-5.org/files/books/computers/languages/java/style/Venkat_Subramaniam-Functional_Programming_in_Java-EN.pdf) by Venkat Subramaniam and [Functional Programming in Java](https://www.manning.com/books/functional-programming-in-java) by Pierre-Yves Saumont.
