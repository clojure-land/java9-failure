This reproduces an apparent bug in Clojure's bytecode generation, mirroring [this bug](https://issues.apache.org/jira/browse/GROOVY-7879) in Groovy.  When a static method on an interface is invoked using a Java 9 VM, this error is thrown:

```
java.lang.IncompatibleClassChangeError: Method failure.Interface.answer()I must be InterfaceMethodref constant,
```

This appears to be the result of stricter validation in Java 9 than existed in Java 8.  A complete reproduction:


```
> jenv local 1.8

> lein do clean, run
Compiling 2 source files to /Users/zach/Dropbox/home/clj/tmp/java9-failure/target/classes
class:
42

interface:
42

> jenv local 9.0

> lein do clean, run
Compiling 2 source files to /Users/zach/Dropbox/home/clj/tmp/java9-failure/target/classes
class:
42

interface:
Exception in thread "main" java.lang.IncompatibleClassChangeError: Method failure.Interface.answer()I must be InterfaceMethodref constant, compiling:(/private/var/folders/h2/vcdqb9910hq8hmz_6b51kctm0000gn/T/form-init4337240893468737369.clj:1:125)
	at clojure.lang.Compiler.load(Compiler.java:7526)
	at clojure.lang.Compiler.loadFile(Compiler.java:7452)
	at clojure.main$load_script.invokeStatic(main.clj:278)
	at clojure.main$init_opt.invokeStatic(main.clj:280)
	at clojure.main$init_opt.invoke(main.clj:280)
	at clojure.main$initialize.invokeStatic(main.clj:311)
	at clojure.main$null_opt.invokeStatic(main.clj:345)
	at clojure.main$null_opt.invoke(main.clj:342)
	at clojure.main$main.invokeStatic(main.clj:424)
	at clojure.main$main.doInvoke(main.clj:387)
	at clojure.lang.RestFn.applyTo(RestFn.java:137)
	at clojure.lang.Var.applyTo(Var.java:702)
	at clojure.main.main(main.java:37)
Caused by: java.lang.IncompatibleClassChangeError: Method failure.Interface.answer()I must be InterfaceMethodref constant
	at failure.main$_main.invokeStatic(main.clj:8)
	at failure.main$_main.doInvoke(main.clj:3)
	at clojure.lang.RestFn.invoke(RestFn.java:397)
	at clojure.lang.Var.invoke(Var.java:377)
	at user$eval149.invokeStatic(form-init4337240893468737369.clj:1)
	at user$eval149.invoke(form-init4337240893468737369.clj:1)
	at clojure.lang.Compiler.eval(Compiler.java:7062)
	at clojure.lang.Compiler.eval(Compiler.java:7052)
	at clojure.lang.Compiler.load(Compiler.java:7514)
	... 12 more
```