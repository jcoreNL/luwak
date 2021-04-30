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

### Checked functions
The Java compiler does not let you write lambda's with checked exceptions. Therefore the library provides checked functions to bypass this problem. All above functional interfaces do have a checked variant; to keep it short and consise, the same symbols are used with a `_` prefix. With other words, the CheckedFunction does look like `_ƒ`. All the default functional interfaces do have a `__` function to wrap a checked variant.

Example:
```java
List.of("items?price=gte:10&price=lte:100").stream()
    .map(ƒ.__(s -> URLEncoder.encode(s, "UTF-8"))) // or `__(...)` with a static import
    .collect(toList());
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
