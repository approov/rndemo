# Simple Approov React Native Demo


> **IMPORTANT:** This repository is now **archived**, because it relates to Approov 1 which is **deprecated**. For Approov 2 please find [here](https://github.com/approov?q=quickstarts) the relevant Quick Start for your mobile platform and http stack. If you don't find one, please refer to the [Getting Started with Approov](https://approov.io/docs/latest/approov-usage-documentation/#getting-started-with-approov) guide or into the [guide](https://approov.io/docs/latest/approov-usage-documentation/#migrating-from-approov-1) for migrating from Approov 1 to Approov 2.

This is a simple React Native client demonstrating Approov usage.

The client is attested by the Approov library, and if registration is successful,
a random shape will be displayed when the 'get shape' button is pressed.

For now, the demo runs on Android. The iOS version is on its way.

# Prerequisites

You will need to download the Approov Demo package at [approov.io](https://approov.io).
See the demo download README.md file for more information.

This demo uses the download demo's backend server, the approov.aar library, and the registration tools.

# The Shapes Back-end Server

The Shapes demo server is really simple, you can access it at https://demo-server.approovr.io/.
It has 2 endpoints:

* A Hello endpoint (https://demo-server.approovr.io/hello) that returns a string
* A Shapes endpoint (https://demo-server.approovr.io/shapes) that returns a random shape

The Hello endpoint has no security (except https) so you should be able to
access it using your software of choice. For the purposes of our examples we
will just use curl.

```
$ curl -D- https://demo-server.approovr.io/hello
HTTP/1.0 200 OK
Content-Type: text/html; charset=utf-8
Content-Length: 12
Server: Werkzeug/0.11.15 Python/3.4.3
Date: Tue, 31 Jan 2017 23:38:52 GMT

Hello World!
```

The Shape endpoint is set up to expect an Approov token. If you try to access
it using curl without the correct header, or with a header that contains an
invalid token, you will get a 400 response.

```
$ curl -D- https://demo-server.approovr.io/shapes
HTTP/1.0 400 BAD REQUEST
Content-Type: text/html
Content-Length: 192
Server: Werkzeug/0.11.15 Python/3.4.3
Date: Tue, 31 Jan 2017 23:43:40 GMT

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 3.2 Final//EN">
<title>400 Bad Request</title>
<h1>Bad Request</h1>
<p>The browser (or proxy) sent a request that this server could not
understand.</p>
```

# iOS and Android Apps

The React Native app, rndemo, integrates the Approov SDK on both iOS and Android apps.

To build and run the app on iOS, see [First experiences with React Native: bridging an iOS native module for app authentication](https://blog.approov.io/react-native-bridging-an-ios-native-module-for-app-authentication).

To build and run the app on Android, see [First experiences with React Native: bridging an Android native module for app authentication](https://blog.approov.io/react-native-bridging-an-android-native-module-for-app-authentication).

# Running the RNDemo App

The simple rndemo app contains only one screen. It has two buttons, `test hello` and `get shape`.

The `test hello` button will attempt to connect with the backend server. If successful,
a smiley face will be displayed. If the connection fails, a frowny face will be displayed.

The `get shape` button will fetch an approov token, attesting as necessary, and make an
Approov-protected API call to teh backend shapes server. If teh Approov token is valid,
a random shape will be displayed. If teh Approov token is invalid or there is any other error,
a froeny face and error message will be displayed.

Note, Approov attestation will always fail until the rndemo app has been properly registered. Attestation will also always fail if running in teh iOS simulator or in either iOS or Android debuggers.

# Registering an App

For our servers to know about your App, you have to tell us about it. We use a
simple registration program to do this. All you have to do is point our
executable at your App package (APK or IPA depending on your platform) and we
will do the rest. The app signature will be added to the list of recognized signatures in
our attestation servers.

__Note:__ For those behind a firewall, our registration application communicates on port 8087.
If you get an error while submitting data saying you can't establish a connection, this
could be the problem.

It is good practice to register the App whenever it is modified and the process
is simple. In the `registration-tools/` folder, find the executable for your OS.
You will need to provide it with some information to allow it to register your
app. The most important pieces are the App itself and the registration token,
which you received with your demo download link in the email we sent you.
The token authorizes you to upload the new App signature.
You need to place it in a file and feed that to the registration program.

Android on Linux example:

```
$ cd <demo root>/registration-tools/Android/Linux
$ ./registration -a <project home>/rndemo/android/app/build/outputs/apk/debug/app-debug.apk -t <registration_access.tok from email> -e 2h
```

The `-e` flag determines how long until the registration expires. It is considerate to only register your app for a
few hours so that the demo service does not overflow with stale registrations.

# Test It Now Works

Now after a short propagation delay (1-2 minutes) your app will be recognized
as valid and given a token that will let it access the demo server. To make
sure the SDK is not caching an invalid token, you might have to restart the
App.

# Programming Notes

The RNDemo demonstrates partially integrating the Approov SDK with React Native as a
native module.

The convenience function, `Approov.fetch(input, init)` wraps the normal React Native
`fetch(input, init)` call by fetching an Approov token and adding it into the headers
during the fetch call.

# CAUTION

This is a simple example for illustrative purposes, and it is not intended for production.
