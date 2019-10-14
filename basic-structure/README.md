# Basic NativeScript App Guide

# Setup
Good practices suggest we do not push npm packages to git since we can install them locally from package.json. If you freshly clone this repo, do the following.
```bash
cd basic-structure/
npm install
```

# Structure
- source: `app/`
- global app settings: `app/app-root.xml`
- `app/home/` and `app/search/` and `app/browser/` are each of the tab functionality. you can change these to any thing as long as they are correctly listed in `app-root.xml` 
- app/fonts/ contains icons and fonts and stuffs that you can use. learn more here https://www.w3schools.com/icons/icons_reference.asp
- The BottomNavigation component provides a way for high level navigation between different views by
  tapping on some of the tabs. Learn more about the BottomNavigation component
  in this documentation article: https://docs.nativescript.org/ui/ns-ui-widgets/bottom-navigation.
  
