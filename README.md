# LMS Grabber #


## Goal ##

To be able to download files from a users LMS (Blackboard) account to their local computers.
This program is written to interface directly with the Rensselaer Polytechnic Institute  (RPI)
interface.


This application is written in Python 2.x.  It uses the Selenieum webbrowser module
in order to parse through the LMS webpages.

Currently being worked on by [Jacob Lane](https://github.com/Jacob-Lane), [Colin Atkinson](https://github.com/colatkinson), Chris Veccio, [Mark Robinson](https://github.com/robinm8), and [Adeet Phanse](https://github.com/phansa).

[Original version](https://github.com/slegec/LMS_Grabber) by [Craig Slegel](https://github.com/slegec). 




## Usage ##

### Development Dependencies ###

* selenium

* Firefox web browser (Can be ported for other browsers)



### Other notes ###

* Only been tested on Windows 7 64bit

* At the time of writing this, Selenium has been problematic on verisons greater that 47.x.x

* If running from outside of the RPI campus, LMS may require a VPN. See [here](http://dotcio.rpi.edu/services/network-remote-access/vpn-connection-and-installation) for more details.



### How to use ###


Edit the 'PW.txt' file to include your LMS Username and Password, for easy login

Run the program in a CMD window.

The user should be prompted to enter the following information:

* LMS Username and password

* Select which courses to downloaded

* Max file size allowed

* File download location

The program should then run without error until it downloads all files


### Issues ###

* The program only goes one directory deep in an LMS course.  Potentially this could be problematic
    depending on how the course is set up.

 
### Style Guide ###

I didn't really follow a specific style of coding so to speak.  I really just used my own.  In essence
it is this:

* Variable start with lowercase
* Mostly camel case
* 2 space indents
* Max line length of around 100 characters
* Line breaks after binary operator
* Blank lines between most lines of code
* Comments above the line
