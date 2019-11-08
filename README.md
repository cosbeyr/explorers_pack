# explorers_pack

First things first, it is very important that we all use the same version of android studio. 
The latest build is 3.5.2 --> https://developer.android.com/studio

Follow the install guide here for your OS of choice: https://developer.android.com/studio/install
Be sure to install the Android Virtual Device when prompted. That is how we can use the emulator.

Once you get it installed, start android studio and select "open an existing Android Studio project" and navigate to this directory, the directory logo should be the android studio logo if it can be recognized as a project, select the directory and wait for it to load.(Go get a snack and come back)

To get a brief overview of the project structure in android studio read this page starting with the "project files" section and stopping before the "Developer services" section : https://developer.android.com/studio/projects#ProjectFiles

Here is a set of guides to setup an emulator: 
https://developer.android.com/studio/run/managing-avds#createavd
https://developer.android.com/studio/run/emulator

If you want to use a samsung galaxy phone size for the emulator you will have to create a new hardware profile.
Here are the specs for the s series: https://wiki.saucelabs.com/display/DOCS/2018/05/25/New+emulators+for+Samsung+Galaxy+S+phones

I recomend looking around the user guide some more too: https://developer.android.com/studio/intro
I also recommend reading about the activity life cycle: https://developer.android.com/reference/android/app/Activity
The app template uses fragments so you can also read about their life cycle here: https://developer.android.com/guide/components/fragments

The workflow hasn't changed much from nativescript. XML layouts are how we define the look of our UI, but we use Java Classes as our codebehind instead of JS.


A couple of advanced topics I highly recommend reading about are events+listeners, adapters, RecyclerViews fragments and fragment transactions.
https://developer.android.com/guide/topics/ui/ui-events
https://developer.android.com/reference/android/widget/Adapter
https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/RecyclerView.Adapter
https://developer.android.com/guide/topics/ui/layout/recyclerview
https://developer.android.com/reference/android/app/Fragment
https://developer.android.com/guide/components/fragments.html#Managing

I also have found a pdf of an older edition of my textbook from class, somethings may be outdated though.
http://solutionsproj.net/software/Android_studio.pdf
(The good stuff starts around chapter 10, also fragments are chapter 24 and they have a nice sample app.)
