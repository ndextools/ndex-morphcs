## As an NDEx Server Process

```text
java -jar <classpath>morphcx.jar -S
```
In the above, the -S (must use a CAPITAL LETTER!) forces `stdin` as the input stream and `stdout` 
as the output stream.  

In the above example, the application default produces a tab-separated value
output stream.  This can also be explicitly done by adding the -c tsv (lower case!) 
parameter option.  

The next command example will produce the exact same .tsv file output:

```text
java -jar <classpath>morphcx.jar -S -c tsv
```
where the -c (use lower case!) parameter option designates a tsv output stream is wanted.


When a comma-separated value output stream is desired, use the following instead:

```text
java -jar <classpath>morphcx.jar -S -c csv 
```

Similarly, this next command will produce an Excel .xlsx file:

```text
java -jar <classpath>morphcx.jar -S -c excel 
```

The parameter options can be specified in any order.
