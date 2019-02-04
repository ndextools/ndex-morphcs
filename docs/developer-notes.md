## Notes For Developers

This classes and interfaces in this application are not (yet!) designed to be extended.  They are 
subject to change at any time. More likely than not they absolutely will change in the future without notice.

### Development Requirements
* [Oracle Java SE 8.0](https://www.oracle.com/technetwork/java/javase/downloads/index.html)
* [Maven (version 3.6+)](http://maven.apache.org/) 

### Library Dependencies
* [GitHub ndexbio/ndex-object-model](https://github.com/ndexbio/ndex-object-model)
* [GitHub ndexbio/ndex-java-client](https://github.com/ndexbio/ndex-java-client)
* [Apache Commons CLI](https://commons.apache.org/proper/commons-cli/)
* [Apache Commons CSV](https://commons.apache.org/proper/commons-csv/)

### Building The MorphCX Application

1. Insure that Java and Maven programs are installed.

2. Download the `ndex-object-model` and `ndndex-java-client` dependencies 
from their respective GitHub repositories and place into their own project directories. 
From each respective project directory, enter the following command 
from a Terminal or Command shell. Start first with the ndex-object-model dependency, and 
follow that with the ndex-java-client dependency.
```text
mvn clean install
```
This should result in both dependencies being installed into the Maven .M2 local repository.

3. Clone or download this application's GitHub repository to yet another project directory. 

4. Then using a Terminal or Command shell from the project directory, enter the following command:
```text
mvn clean package
```
The two Apache Commons dependencies should be automatically downloaded by Maven into it's local .M2
repository.

After execution of the above Maven command, the resulting executable binary files are found 
in the ${project_dir/src/target} directory.
The file named `morphcx.jar` contains a fully-formed executable packaged with all dependencies.
That is the file that needs to be placed in a directory pointed to by the Java PATH or CLASSPATH
environment variables, or in the present/current working directory from which you invoke the
application.

Sample CX networks are provided in the ${project_dir/src/target/sample-networks} directory. It is
possible -- even likely -- that these networks are not the most up to-date CX network.  They are 
only intended to be used for quickly testing the application.

A immediate way to test the application is to...
* Insure the project directory is make the target directory the current directory and enter
the following command:
```text
java -jar morphcx.jar -c tsv -i sample-networks/LUMINAL_BREAST_CANCER.json -o sample-networks/LUMINAL_BREAST_CANCER.tsv
```
The above command results in converting the input file into the tab-separated value file named
LUMINAL_BREAST_CANCER.tsv in the sample-networks folder.

The -o output parameter can be altogether omitted, and this application will output tab-separated columns onto
the console. 
