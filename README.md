# LMS Grabber #


[![Codacy Badge](https://api.codacy.com/project/badge/Grade/97a997a5aaad418a974e01c2fe02d7cf)](https://www.codacy.com/app/robinm8/LMSGrabber?utm_source=github.com&utm_medium=referral&utm_content=LMSGrabber/LMSGrabber&utm_campaign=badger)
[![Build Status](https://img.shields.io/travis/LMSGrabber/LMSGrabber/master.svg)](https://travis-ci.org/LMSGrabber/LMSGrabber)
[![Codecov](https://img.shields.io/codecov/c/github/LMSGrabber/LMSGrabber.svg)](https://codecov.io/gh/LMSGrabber/LMSGrabber/)
[![Code Climate](https://img.shields.io/codeclimate/github/LMSGrabber/LMSGrabber.svg)](https://codeclimate.com/github/LMSGrabber/LMSGrabber)
[![Code Climate](https://img.shields.io/codeclimate/issues/github/LMSGrabber/LMSGrabber.svg)](https://codeclimate.com/github/LMSGrabber/LMSGrabber)

## Overview ##

This program is designed to create offline archives of various Learning Management Systems (LMS). It is created as a part of the [Rensselaer Center for Open Source](https://rcos.io/projects/lmsgrabber/lmsgrabber/profile) and [Software Design and Documentation](https://sites.google.com/site/rpisdd/) courses.

Currently being worked on by [Jacob Lane](https://github.com/Jacob-Lane), [Colin Atkinson](https://github.com/colatkinson), [Daniel Zhu](https://github.com/zhuguotian), [Chris Vecchio](https://github.com/ChrisVech), [Mark Robinson](https://github.com/robinm8), and [Adeet Phanse](https://github.com/phansa).

Inspired by the [Original Python version](https://github.com/slegec/LMS_Grabber) by [Craig Slegel](https://github.com/slegec).

## Compiling/Running

### Class Files ###

```bash
cd LMSGrabber
mvn package (Only need to run this once)
mvn exec:java -D exec.mainClass=rpi.lmsgrabber.App
```

### JAR ###
Only one below required per OS:

[Install Maven - OS: Windows](https://www.mkyong.com/maven/how-to-install-maven-in-windows/)

[Install Maven Shell - OS: Linux/MacOS](https://github.com/jdillon/mvnsh)

All OS:

```bash
cd LMSGrabber
```

Windows (mvn):

```bash
mvn package
```

Linux / MacOS (mvnsh):

```bash
mvnsh package
```

All OS:

```
java -jar target/lmsgrabber-0.0.1-SNAPSHOT-jar-with-dependencies.jar
```

### Firefox and webdrive ###

You will need to install [firefox](https://www.mozilla.org/en-US/firefox/new/) to run this 
program 


You will then need to download the [gecko driver](https://github.com/mozilla/geckodriver/releases)
, extract it and add it the to path.

Unix :

```export PATH=$PATH:/path/to/geckodriver```

Windows:
Add /path/to/geckodriver to the path variable
(instructions [here](https://www.java.com/en/download/help/path.xml)). If you extracted
the driver to the downloads folder and a 64-bit windows machine point the path to 
C:\Users\userName\Downloads\geckodriver-v0.19.1-win64 for example.

Once this is complete you can go the class files step

## Contributing ##

Please see our [Contributor's Guide](https://github.com/LMSGrabber/LMSGrabber/wiki/Contributing) and [wiki pages](https://github.com/LMSGrabber/LMSGrabber/wiki) for information on how to contribute towards the project.

Builds are automatically triggered and placed under the release section for any [tags](https://git-scm.com/book/en/v2/Git-Basics-Tagging).

## Using with RPI LMS ##

* Select class rpi.lmsgrabber.BlackBoardGrab

* Enter https://lms.rpi.edu/ for URL

* Enter your RCS Username

* Enter your RCS password

* Click Add New Lms then click grab

## Other notes ##

Please check your LMS's user agreements and your school's policies on automated web crawling before using LMS Grabber.
