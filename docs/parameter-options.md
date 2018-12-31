# Parameter Options

### Help

```text
[-h | --help]
```
This causes the display of all possible parameter options to `stdout` (the console, in most cases).
This parameter is case sensitive. 

Examples:
```text
java -jar morphcx -h      or       java -jar morphcx --help
```

### Input

```text
[-i | --input] filename
```
This parameter and the filename are case-sensitive. The filename is the absolute or 
relative path and filename of the input network JSON-formatted file. 

Example:
```text
java -jar morphcx -i sample-network/LBC_FILETERED_ERK_AKT.json
```
This option is used when executing this program from the
command line.  When omitted, the program will take its input
from `stdin` rather than a file.  `stdin` is also expected when the program is
run in the NDEx server so it should not be used in that execution
environment.

### Output

```text
[-o | --output] filename
```
where both the option parameter (-o and --output)  and the filename are 
case-sensitive, and where filename is the absolute or relative 
path and filename of the output destination.

Example:
```text
-o sample-network/LBC_FILETERED_ERK_AKT.csv
```
This option is also used when executing this application from the
command line.  When omitted, the application will output its
results to `stdout` instead of a file.

In case the input option is specified and output option is
omitted, the output file path and name will be made to match 
the input file path and name, but the file extension will be
changed to .csv or .tsv, depending upon whether the desired Operation
is 'tocsv' or 'totsv', respectively.

### Newline Characters
Operating systems use different character strings when outputting to a new lto be used.  
In most circumstances the system default newline character can be used. But if a cross-platform
problem with the newline characters, the following OS platforms defaults can be forced.  

* WINDOWS
* OSX
* OLDMAC (i.e. legacy Apple Mac)
* LINUX

```text
[-n | --newline] OS_Platform
```
where the option (-n and --newline) is case sensitive, and the OS_Platform is case-insensitive.

Example:
```text
-n osx
```
where the output newline is to be forced to that used by
the OSX platform.

In most cases, this option can be omitted.  When omitted, the Java VM system runtime determines
which platform is used.

### Format
Comma Separated Value (CSV) and Tab Separated Value (TSV) formatted files have been used since
the days of punched cards.  As such, therefore various industry-standard specifications come into
play.  This application supports three formats of output.  In most cases, DEFAULT is satisfactory.

* DEFAULT
* RFC4180
* EXCEL
  
```text
[-f | --format] format-type
```
where the option (-f and --format) is case sensitive, and the format-type is case-insensitive.

Example:
```text
-f rfc4180
```

where the RFC4180 file format specification will be enforced,

When omitted, the DEFAULT format specification is used.
