# Parameter Options

The user can pick from several command-line parser standards, which are listed below.  And although examples
used in this documentation are given in the POSIX and GNU formats, please use whichever standard you are most
accustomed to using.

* POSIX like options (i.e. `-Sn linux`)
* GNU like long options (i.e. `--input filename --output outfile --convert csv`)
* Java like properties (i.e. `-Dc tsv`)
* Short options with value attached (i.e. `-i filename`)
* Long options with single hyphen (i.e. `-server`)

### Help

```text
[-h | --help]
```
This causes the display of all possible parameter options to `stdout` (the console, in most cases).
No other processing is done.
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

This option is only to be used when executing the program from a Terminal or Command shell. 
The -i and --input keywords should never be used when executing as an NDEx server process.

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

The filename can be enclosed by double quotation (") marks if it contains spaces (such as in a Window's filename).

This option is only to be used when executing the program from a Terminal or Command shell. 
The -o and --output keywords should never be used when executing as an NDEx server process

### Newline Characters
Operating systems use platform-specific character strings when outputting to the next line.  
In most circumstances the system default newline character can be used. Most of the time the
appropriate new line character will be generated and this parameter option is not needed.  It is only
here as a means to override the platform-specific newline character string to a specific OS platform.

* WINDOWS
* OSX
* OLDMAC (i.e. legacy Apple Mac)
* LINUX
* SYSTEM

```text
[-n | --newline] OS_Platform
```
where the keyword parameters -n and --newline are case sensitive, and the OS_Platform is case-insensitive.

Example:
```text
-n osx
```
where the output newline is to be forced to that used by the Apple OSX platform.

When this option SYSTEM or omitted, the newline character string is determined by the JAVA runtime on which
this application is executing.

### Server

```text
[-S | --server]
```
This keyword parameter forces all IO to be forced to `stdin` and `stdout` and overrides the input and output
options.  We recommend that this keyword option be used whenever this application is executed as an NDEx server
process. 

Examples:
```text
java -jar morphcx -S -c tsv
```
The example command-line shown above will result in all IO to be be derived from the stdin and stdout streams in which
individual columns of data are separated by tab characters.  The -S and -c keyword parameters can be in any order, but
the tsv or csv parameter value must immediately follow the -c keyword.
