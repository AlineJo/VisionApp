package com.programining.visionapp.models;

public class MyConstants {
    //TODO : get your api key from : https://console.cloud.google.com/flows/enableapi?apiid=vision.googleapis.com
    // #1 select a project
    // #2 in the search type " Cloud vision api "
    // #3 Side menu > overview > top section of the page(under toolbar) make sure Vision AI is enabled, if it is enable you will see "DISABLE API"!
    // #4 Side menu > Credentials > click on    +CREATE CREDENTIALS, Then, click on OAuth client ID
    // #5 On the next screen :
    //# 5.1   select Android
    //# 5.2   add  SHA-1 key. click on Gradle on right hand side of the screen, VisionApp > app > Tasks > android > click on signingReport. Then you will find SHA1 in "Run" tab. Run tab is beside logcat and debug
    //SHA-1 : BA:51:AF:3E:0A:31:45:34:BF:62:4F:D9:CD:7A:1A:69:50:3B:A0:F9
    //# 5.3   add package name, package name can be found in Manifest file
    //package : com.programining.visionapp
    //# 5.4   copy the like which appears on the dialog and paste it as value for "KEY_VISION_API_KEY"
    //** give some time for the api key to be enabled
    public static final String KEY_VISION_API_KEY = "AIzaSyAXEPVsrJ6a734eW2G9NBJbRaecG-0rGmQ"; //"733021200608-a9eont5grvra6mqdom06q1id43vk0b69.apps.googleusercontent.com";//"AIzaSyDmMk5abaMr-x8edcL_cXNiPRhgnRaskzk";
}
