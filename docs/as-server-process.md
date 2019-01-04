# As an NDEx Server Process

# === WORK IN PROGRESS ===

```text
java -jar <classpath>morphcx.jar -S
```
In the above, the -S (USE CAPITAL LETTER!) forces `stdin` as the input stream and `stdout` 
as the output stream.  

In the above example, the application default was to produce a tab-separated value
output stream.  This can also be explicitly done by adding the -c tsv (lower case!) 
parameter option.  Here is an example of stating what conversion results will be:

```text
java -jar <classpath>morphcx.jar -S -c tsv
```
where the -c (use lower case!) parameter option designates a tsv output stream is wanted.


When a comma-separated value output stream is desired, use the following instead:

```text
java -jar <classpath>morphcx.jar -S -c csv 
```
