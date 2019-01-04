# ndex-morphcx
## Morphs an NDEx (Network Data Exchange) CX network into .csv and .tsv file formats.

This application is normally invoked through the NDEx `batch/network/export` REST API endpoint.  
Following that, the `task/file` REST API endpoint is used to download the resulting .csv/.tsv output file.

The alternative is to run the application from a __(MacOS or Linux) Terminal__ or 
__(Windows) Command-line or PowerShell__ prompt using a JSON-formatted NDEX CX file for input.

### When used by the public or a private NDEx server:

* This application is invoked using the NDEx 
`batch/network/export` REST API function.  
* After the batch process runs, the `task/file` REST API 
function will download the resultant output file.  
* You can then import the downloaded file to applications that accept .csv/.tsv/.txt files as input.


### When used from a Terminal or Command-line:
 
* Input originates from a JSON-formatted NDEx CX network file that you downloaded to your local
file system using the NDEx Webapp front-end, or through the NDEx REST API.
* This application is run in a Terminal or Command-line/PowerShell prompt, using parameters or IO 
redirection to specify the input and, optionally, output file.
* The output file can be imported into applications that accept .csv/.tsv/.txt files as input.


### Running the Application
The executable program containing all dependencies is named `morphcx.jar`, and is found 
in this repository's `${project}/target` directory.  

The program is a Java executable, thus requiring the Java JRE 8 or JDK 8 be installed on your workstation.
To test if Java is installed, enter the following on the Terminal or Command-line/PowerShell prompt:
```text
java -version
```

Execute the program using a Terminal or Command-line prompt using the following general format.  
```text
java -jar morphcx.jar [option-1, option-n...]
```

Valid options are documented in Parameter Options section below. Default values are assumed if none 
are specified. At a minimum, the input file should be specified as a parameter or by IO redirection.

A few sample CX networks are provided in the `target/sample-networks` directory.  They let you quickly
test whether the application has been installed successfully. So you may copy the entire 
`${project}/target`directory and sub-directories onto your workstation, make it the current directory, 
and enter the following from a Terminal or Command-line prompt:
```text
java -jar morphcx.jar -i sample-networks/LBC_FILTERED_ERK_AKT.json
```
... which will result in `sample-networks/LBC_FILTERED_ERK_AKT.tsv` being produced.


### Parameter Options
The input file needs to be specified, either by using the input parameter or by IO redirection.  
Most other parameters are not required because default values are otherwise assumed.

* [Parameter Options](docs/parameter-options.md)

### IO Redirection
IO redirection is commonly used by SysOps engineers and is less intuitive than using 
conventional parameters.  Nevertheless, the fact that it can be used is worth documenting.
It is a feature that is quite useful when executing this program within Shell or Command-line/PowerShell 
scripts.
* [IO Redirection](docs/io-redirection.md) 

### Running as an NDEx Server Process
An IT server administrator will normally be responsible for configuring NDEx servers.  That 
individual can install this application as one or more NDEx server configuration commmands.
Instructions are found in the link below. 
* [NDEx Server Process](docs/as-server-process.md) 

### Notes for Developers
A fully built executable binary .jar file is found in the `` directory of this repository.  However, for
those who prefer to build their own executables from scratch, instructions to do this are found at the
link below.
* [Developer Notes](docs/developer-notes.md)