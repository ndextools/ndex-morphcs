# IO-Redirection

When run in a Terminal or Command-line shell, the application can make use of standard IO-redirection
features of most terminal or command shells.  Examples include Unix and OSX Terminal or Window's PowerShell.

IO-Redirection is typically seen in batch scripts, and use the greater-than `">"`, less-than `"<"`,
and pipe `"|"` characters to indicate OS platform stream source or destination streams and pipes.

When used, they replace the input and output keyword parameter options used by this application.  In
other words, instead of using -i filename or -o filename options, IO-redirection can be used in their
place.

This would be an example of piping a JSON-formatted CX network file to this application from the
Linux cat program:

```text
cat \home\user1\cxfiles\network1.json | java -jar morphcx.jar -o \home\tsvfiles\network1.tsv
```
In the above example, the program named cat passes its output (as printlines) to this application,
which in turn produces a tsv file.  Because the -c keyword option was not used, the output is
converted to a tab-separated value format by default.

An example of using IO-redirection where both the input and output are specified by IO-redirection.

```text
java -jar morphcx.jar < \home\user1\source\network1.json > \home\out\network1.txt
```
Here the network1.json file in the source directory is used as input, and the output of this
application is written to the file named network1.txt in the our directory.  Notice that
the output file extension is txt.  The -c parameter option was not used, thus making
the default output file extension as tsv.  However, because the redirected filename explicitly
uses txt as the extension, the file extension becomes ".txt".
