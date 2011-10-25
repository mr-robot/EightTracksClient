Eight-Tracks-Client: Android Access to Eight-Tracks.com
=========================================================

8tracks.com is a great service for sharing great mixes 
(and They also have a great API!)

I've played around with the Eight-Tracks API and I've created a Client library 
which simplifies it's consumption in Android Applications.

Hopefully someone finds it useful (maybe for making a Streaming App - checkout Four Reels)

The library is available as a JAR file from the downloads area of this GitHub repo.
The project itself is set up as an Android library project, in case you wish to use the source code
in that fashion.

There are two other folders in this repo:
 - Example - A simple sample application showing the usage of the Android App
 - Tests - The unit tests for testing the API Client

Usage
-----
To use the `Eight-TracksClient`, firstly you need will need an API key from the nice folks 
at 8tracks.com (check out 8tracks.com/developers/)

Once you have referenced the Eight-Tracks Client in your project you will need to create
a Eight-TracksService which you will use to retrieve information about Bands, Albums,
Tracks and Discography information.

Create an instance of Eight-TracksService by passing your API key information into the
one of the creation methods in the Eight-TracksServiceFactory.

Once you have an instance of Eight-TracksService, call any of the relevant methods in 
the service to return Eight-Tracks objects containing the results of your call

### Style

`Eight-TracksClient` have been built using a loosely typed object-mapping style. All of the 
results of calls to the Eight-TracksService result in objects being returned with field values
being stored in a single Map in the object

This is based on the assumption that the API is likely to change in the future (and the fact 
that I don't like boring boilerplate javabeans with a heap of redundant getters).

The most frequently used fields have getters defined.

### Methods

- searchMixes - Return a list of Mixes
- getMixes - Return a list of mixes filtered by input criteria (using FilterBuilder)
- getUser - Returns a User object


### Objects

 - Mix - Information describing a single mix -(e.g. Cover Art, Name)
 - User -  Information describing an 8tracks.com user
 
Dependencies
------------
This project requires Android 1.5 and up.
It has no external (outside of the Android SDK) dependencies and uses the 
Native Android JSON libraries for deserialisation.

Version
-------
This is version v0.1.0 of this library

Example
----
In the `Example/` sub-project you will find
a sample activity that demonstrates the use of `Eight-TracksClient`.

License
-------
The code in this project is licensed under the Apache
Software License 2.0, per the terms of the included LICENSE
file.

Questions
---------
If you have questions regarding the use of this code, please send me an email:
mr _ robot [at| wonderfulrobot dot com

Release Notes
-------------
* v0.1.0: Initial Release

[web]: http://www.wonderfulrobot.com
[web]: http://github.com/mr-robot/Eight-Tracks-Client/tree/master
[web]: http://github.com/mr-robot/FourReels/tree/master