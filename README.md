# View as JSON ![Build](https://github.com/selcukguvel/view-as-json/workflows/Build/badge.svg) [![Version](https://img.shields.io/jetbrains/plugin/v/16658-view-as-json.svg)](https://plugins.jetbrains.com/plugin/16658-view-as-json) [![Downloads](https://img.shields.io/jetbrains/plugin/d/16658-view-as-json.svg)](https://plugins.jetbrains.com/plugin/16658-view-as-json) 

![view-as-json](https://user-images.githubusercontent.com/22414712/116786753-13019d80-aaa9-11eb-9505-3b02994fece5.gif)

<!-- Plugin description -->
## What is it for?

Using this **IntelliJ** plugin, you can: 

* Prettify your JSON string and view colored format
* Highlight bracket pairs as you move through lines
* Scroll through matching bracket line
* Copy formatted JSON string seen on the text pane

## How to use it?
* There are two use cases where **View as JSON** button will be visible:
  * Select your text and right click on it
    * You need to select a valid JSON string, otherwise you will see an error message
  * Right click on the file with **json** extension
    * You need to select a JSON file that contains a valid JSON string, otherwise you will see an error message

* Click **View as JSON**
* Enjoy!
    * Use arrow keys or mouse clicks to move through lines
    * Use **Space** to scroll through matching bracket line if there is any
<!-- Plugin description end -->

## Installation

- Using IDE built-in plugin system:
  
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>Marketplace</kbd> > <kbd>Search for "View as JSON"</kbd> >
  <kbd>Install Plugin</kbd>
  
- Manually:

  Download the [latest release](https://github.com/selcukguvel/view-as-json/releases/latest) and install it manually using
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>⚙️</kbd> > <kbd>Install plugin from disk...</kbd>
  
## Resources

- Rob Camick's [LinePainter](http://www.camick.com/java/source/LinePainter.java) is adapted to implement line highlighting feature