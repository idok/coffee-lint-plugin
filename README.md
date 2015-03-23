# CoffeeLint Plugin #

[CoffeeLint](http://www.coffeelint.org/) is The pluggable linting utility for JavaScript. see more [here](http://coffeelint.org/).<br/>
CoffeeLint plugin for WebStorm, PHPStorm and other Idea family IDE with Javascript plugin, provides integration with CoffeeLint and shows errors and warnings inside the editor.
* Support displaying coffeelint warnings as intellij inspections
* Support for custom coffeelint rules

## Getting started ##
### Prerequisites ###
* [NodeJS](http://nodejs.org/)
* IntelliJ 13.1.4 / Webstorm 8.0.4, or above.

Install coffeelint npm package [coffeelint npm](https://www.npmjs.org/package/coffeelint)</a>:<br/>
```bash
$ cd <project path>
$ npm install coffeelint
```
Or, install coffeelint globally:<br/>
```bash
$ npm install -g coffeelint
```

### Settings ###
To get started, you need to set the CoffeeLint plugin settings:<br/>

* Go to preferences, CoffeeLint plugin page and check the Enable plugin.
* Set the path to the nodejs interpreter bin file.
* Set the path to the coffeelint bin file. should point to ```<project path>node_modules/coffeelint/bin/coffeelint``` if you installed locally or ```/usr/local/bin/coffeelint``` if you installed globally.
  * For Windows: install coffeelint globally and point to the coffeelint cmd file like, e.g.  ```C:\Users\<username>\AppData\Roaming\npm\coffeelint.cmd```
* Select whether to let coffeelint search for ```coffeelint.json``` file
* You can also set a path to a custom rules directory.
