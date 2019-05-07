# ndex-morphcx
## Morphs NDEx (Network Data Exchange) CX networks into .csv/tsv and .xlsx file formats.

This application is designed to execute two ways.  When running as a batch process within an NDEx server, it is invoked
using the NDEx `batch/network/export` REST API endpoint. The resulting output can then be downloaded to a client
using the corresponding `task/file` REST API endpoint.

It can also be launched within a Terminal or Command shell.  In that case, a JSON-formatted CX file is input. 
Such a file can be obtained several ways.  Prehaps the easiest way is to download a CX network 
using the interactive NDEx [Search Panel](http://www.ndexbio.org/#/) feature.

## Recent Releases
Tested and stable releases can be obtained through the links provided in the following table.  

Version | Release Date | Notes
------- | ------------ | -----
[2.4.1c](https://github.com/ndextools/ndex-morphcx/releases/tag/2.4.1c) | 2019-05-07 |  Testing Underway
[2.4.1a](https://github.com/ndextools/ndex-morphcx/releases/tag/2.4.1a) | 2019-04-01 |  Tested and Stable
[2.4.0-Beta-1e](https://github.com/ndextools/ndex-morphcx/releases/tag/2.4.0-Beta-1e) | 2019-02-17 |  Tested and Stable

The master branch may contain more recent commits due to "hot fixes" to reported 
[Issues](https://github.com/ndextools/ndex-morphcx/issues?q=is%3Aissue+is%3Aclosed),
on-going feature development, and production code base improvements.
However their impact on the application will not have been tested as well as the versions listed above.

## Documentation

* [General Information](docs/general-information.md)
* [Parameter Options](docs/parameter-options.md)
* [IO Redirection](docs/io-redirection.md) 
* [NDEx Server Process](docs/as-server-process.md) 
* [Developer Notes](docs/developer-notes.md)

