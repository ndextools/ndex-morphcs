# Parameter Options

### Help

```text
[-h | --help]
```
This causes the display of all possible parameter options to `stdout` (the console, in most cases).
The -h and --help keywords are case sensitive. 

Examples:
```text
java -jar morphcx -h      or       java -jar morphcx --help
```

### Input

```text
[-i | --input] filename
```
The -i and --input keywords and the filename are case-sensitive. The filename is the absolute or 
relative path and filename of the JSON-formatted CX network input file. The absence of this
parameter suggests that input originates from `stdin`, where the input source is the NDEx
server or command-line redirection of a file is being performed.

Example:
```text
java -jar morphcx -i sample-network/LBC_FILETERED_ERK_AKT.json
```

The filename can be enclosed by double quotation (") marks if it contains spaces (such as a Window's filename).

This option is only to be used when executing the program from the
command line. The -i and --input keywords should not be used when executing as an
NDEx server process.

### Output

```text
[-o | --output] filename
```
The -o and --output keywords and the filename are case-sensitive. The filename is the absolute or 
relative path and filename of the JSON-formatted CX network output file. The absence of this
parameter suggests that output goes to `stdout`, where the output destination is the NDEx
server or command-line redirection of a file is being performed.

Example:
```text
-o sample-network/LBC_FILETERED_ERK_AKT.csv
```
This option is only to be used when executing the program from the
command line. The -o and --output keywords should not be used when executing as an
NDEx server process.

The filename can be enclosed by double quotation (") marks if it contains spaces (such as a Window's filename).

In case the input option is specified and output option is
omitted, the output file path and name will be made to match 
the input file path and name, but the file extension will be
changed to .csv or .tsv. Whichever extension is used depends on the specified or 
default state of the convert option (i.e. when -c and --convert keyword parameters 
are used).

### Newline Characters
Operating systems use platform-specific character strings when outputting to a new line.  
In most circumstances the system default newline character can be used. In most cases the
appropriate new line character will be generated and this option is not needed.  This option 
is only intended as a means to override the platform-specific newline character string to 
a specific OS platform.

* WINDOWS
* OSX
* OLDMAC (i.e. legacy Apple Mac)
* LINUX

```text
[-n | --newline] OS_Platform
```
where the keyword parameters -n and --newline are case sensitive, and the OS_Platform is case-insensitive.

Example:
```text
-n osx
```
where the output newline is to be forced to that used by the Apple OSX platform.

When this option is omitted, the Java VM system runtime determines the newline character string.

### Help

```text
[-S | --server]
```
This keyword parameter forces all IO to be forced to `stdin` and `stdout` and overrides the input and output
options.  We recommend that his keyword option be used whenever this application is executed as an NDEx server
process. 

Examples:
```text
java -jar morphcx -S -c tsv
```
The example command-line shown above will result in all IO to be be derived from the stdin and stdout streams in which
individual columns of data are separated by tab characters.  The -S and -c keyword parameters can be in any order, but
the tsv or csv parameter value must immediately follow the -c keyword.
