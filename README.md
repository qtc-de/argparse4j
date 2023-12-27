### argparse4j

----

This repository contains a fork of the popular [argparse4j](https://github.com/argparse4j/argparse4j)
Java library. It was created because the original project seems to be no longer maintained and attempts
to contact the last known maintainer failed.

Notice that this fork is more or less a private project. It is accessible via [maven](https://mvnrepository.com/artifact/eu.tneitzel/argparse4j),
but there is no interest in providing any support for bugs and features that do not affect my own projects.
However, pull requests that can be reviewed and merged without big efforts are welcome and will be merged.


### Usage

----

If you want to use this fork of *argparse4j*, just use the following dependency within your `pom.xml`:

```xml
<dependency>
  <groupId>eu.tneitzel</groupId>
  <artifactId>argparse4j</artifactId>
  <version>1.0.0</version>
</dependency>
```


### Features

----

This section lists some of the features that are different from the original *argparse4j* project.
For general documentation, it is recommended to visit the [website](https://argparse4j.github.io/)
of the original project.


### Acknowledgements

----

All credits go to the original authors and contributors of [argparse4j](https://github.com/argparse4j/argparse4j),
which are still references by the license and the `pom.xml` file. My part was only adding some additional
features to an alreday awesome library.
