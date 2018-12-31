# Notes For Developers

This classes and interfaces in this application are not (yet!) designed to be extended.  They are subject to
change at any time. In fact, they will most likely be changed in the future without notice.

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

2. Download the `ndex-object-model` and `ndndex-java-client` dependencies into their own 
project directories. From each respective project directory, enter the following command 
from a Terminal or Command Shell. Start first with the ndex-object-model dependency, and next with the
ndex-java-client dependency.
```text
mvn clean install
```
This should result in these two NDEx Maven dependencies to be installed into the Maven .M2 local
repository.

3. Clone or download this application's GitHub repository to yet another project directory. 

4. Then using a Terminal or Command Shell from the `ndex-morphcx` project directory, enter the following
command:
```text
mvn clean package
```
The executable binary files are found in the ${project_dir/src/target} directory.
The file named `morphcx.jar` contains a fully-formed executable packaged with all dependencies.  

Sample CX networks aree placed into the ${project_dir/src/target/sample-networks} directory. It is
possible -- even likely -- that these networks are not the most up to-date CX network.  They are 
only intended to be used for quickly testing the application.
