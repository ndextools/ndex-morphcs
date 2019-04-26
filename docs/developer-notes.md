## Notes For Developers

This classes and interfaces in this application are not (yet!) designed to be extended.  They are 
subject to change at any time - in fact, you can bet on it!

### Development Requirements
* [Oracle Java SE 8.0](https://www.oracle.com/technetwork/java/javase/downloads/index.html)
* [Maven (version 3.6+)](http://maven.apache.org/) 

### Library Dependencies
* [GitHub ndexbio/ndex-object-model](https://github.com/ndexbio/ndex-object-model)
* [GitHub ndexbio/ndex-java-client](https://github.com/ndexbio/ndex-java-client)
* [Apache Commons CLI](https://commons.apache.org/proper/commons-cli/)
* [Apache Commons CSV](https://commons.apache.org/proper/commons-csv/)
* [Apache Commons POI](https://poi.apache.org/index.html)

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

4. Then using a Terminal or Command shell from the project directory, enter the following command -
just as you did with the `ndex-object-model` and `ndex-java-client` dependencies:
```text
mvn clean package
```

The above command will compile and build this application's executable library (JAR) file, which is 
found in the ${project_dir/src/target} directory.
The executable file named `morphcx.jar` contains a fully-formed executable packaged with all dependencies.
This file should be placed in a directory pointed to by the Java CLASSPATH environment variable, 
or explicitedly pointed to when the application is invoked through the Java JVM.

Sample CX networks are provided in the ${project_dir/src/target/sample-networks} directory. It is
possible -- even likely! -- that these networks are not the most up to-date CX network.  The files are 
only intended to be used to quickly test the application and to verify a successful installation.

Test the application by making the target ${project_dir/src/target} directory the current directory, then enter
the following command:
```text
java -jar morphcx.jar -c tsv -i sample-networks/LUMINAL_BREAST_CANCER.json -o sample-networks/LUMINAL_BREAST_CANCER.tsv
```
The above command results in converting the input file into the tab-separated value file named
LUMINAL_BREAST_CANCER.tsv in the sample-networks folder.

The -o output parameter can be altogether omitted, and this application will output tab-separated columns onto
the console. 

Finally, if a Microsoft Excel Workbook file (.xlsx) is desired, then enter this command:
```text
java -jar morphcx.jar -c excel -i sample-networks/LUMINAL_BREAST_CANCER.json -o sample-networks/LUMINAL_BREAST_CANCER.tsv
```
