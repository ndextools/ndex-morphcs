## General Information

### When used by the public or a private NDEx server:

* This application is invoked using the NDEx 
`batch/network/export` REST API function.  
* After the batch process runs, the `task/file` REST API 
function will download the resultant output file.  
* You can then import the downloaded file into applications that accept .csv/.tsv/.txt files as input.


### When used from a Terminal or Command-line:
 
* Input originates from a JSON-formatted NDEx CX network file that you downloaded to your local
file system using the NDEx Webapp front-end, or through the NDEx REST API.
* This application is run in a Terminal or Command/PowerShell shell using parameter options that specify the 
input and output filenames and the file-type you want.
* The output file can then be imported into applications that accept .csv/.tsv/.txt files as input.


### Running the Application
The executable program containing all dependencies is named `morphcx.jar`, and is found 
in this repository's `${project}/target` directory.  

The program is a Java executable, thus the Java JRE 8 or JDK 8 must be installed on your computer.
To test if Java is installed, enter the following on the Terminal or Command-line/PowerShell prompt:
```text
java -version
```
If the program is not found, it is likely Java is not installed or is incorrectly configured.

Execute the program in a Terminal or Command/Powershell shell using the following general format.  
```text
java -jar morphcx.jar [option-1, option-n...]
```

Valid options are documented in Parameter Options section below. Default values are assumed if none 
are specified.

A few sample CX networks are provided in the `target/sample-networks` directory.  They let you quickly
test whether this application has been installed correctly. For a quick test, copy the entire 
`${project}/target`directory and sub-directories onto your workstation, make it the current directory, 
and enter the following from a Terminal or Command-line prompt:
```text
java -jar morphcx.jar -i sample-networks/LBC_FILTERED_ERK_AKT.json -o sample-networks/LBC_FILTERED_ERK_AKT.tsv
```
... which will result in sample-networks/LBC_FILTERED_ERK_AKT.tsv being produced in the sample-networks directory.


### Parameter Options
When executing this application in a Terminal or Command/Powershell shell, the input and output files should 
be specified, either by using command-line parameters or by IO redirection. Default values are assumed for missing
parameters.

When configuring this application to be run as an NDEx server batch process, use the -S or --server command-line 
parameter options without specifying the input or output command-line parameters.  
These command-line parameter options force all input and output to use the system standard input and output,
respectively.

* [Parameter Options](docs/parameter-options.md)

### IO Redirection
IO redirection is commonly used by SysOps engineers and is less intuitive than using 
conventional parameters.  Nevertheless, IO redirection can be quite useful when executing this 
application within a script.  Examples of IO redirection are found in the link immediately below.
* [IO Redirection](docs/io-redirection.md) 

### Running as an NDEx Server Process
An NDEx server administrator can configure this application to run as an Exporter in the ImporterExporter tag
of the NDEx configuration file. Several exporter names can be created, one designated for each output file-type and
optional parameter options.
Instructions are found in the link that follows. 
* [NDEx Server Process](docs/as-server-process.md) 

### Notes for Developers
A fully built executable binary .jar file is found in the `${project}/target` directory of this repository.  
However, for those who prefer to build their own executables from scratch, instructions to do this are found at the
link below.
* [Developer Notes](docs/developer-notes.md)