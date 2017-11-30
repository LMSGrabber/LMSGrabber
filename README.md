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
mvn exec:java -D exec.mainClass=rpi.lmsgrabber.App
```

### JAR ###
Only one below required per OS:
[Install Maven - OS: Windows](https://www.mkyong.com/maven/how-to-install-maven-in-windows/)
[Install Maven Shell - OS: Linux/MacOS](https://github.com/jdillon/mvnsh)

```bash
cd LMSGrabber
mvn package
java -jar target/lmsgrabber-0.0.1-SNAPSHOT-jar-with-dependencies.jar
```

## Contributing ##

Please see our [Contributor's Guide](https://github.com/LMSGrabber/LMSGrabber/wiki/Contributing) and [wiki pages](https://github.com/LMSGrabber/LMSGrabber/wiki) for information on how to contribute towards the project.

Builds are automatically triggered and placed under the release section for any [tags](https://git-scm.com/book/en/v2/Git-Basics-Tagging).

## Other notes ##

Please check your LMS's user agreements and your school's policies on automated web crawling before using LMS Grabber.
