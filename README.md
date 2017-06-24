# fifteen

Fifteen game.

[Demo](https://github.io/alexeykomov/fifteen).

![Screenshot](https://github.io/alexeykomov/fifteen/resources/img/Screen-Shot-2017-06-24-at-14.43.17.png)

## Overview

Fifteen game written in [ClojureScript](https://clojurescript.org/) and [Reagent](https://github.com/reagent-project/reagent). Rules can be overviewed at the Wikipedia [https://en.wikipedia.org/wiki/15_puzzle](https://en.wikipedia.org/wiki/15_puzzle).

## Setup

To get an interactive development environment run:

    lein figwheel

and open your browser at [localhost:3449](http://localhost:3449/).
This will auto compile and send all changes to the browser without the
need to reload. After the compilation process is complete, you will
get a Browser Connected REPL. An easy way to try it is:

    (js/alert "Am I connected?")

and you should see an alert in the browser window.

To clean all compiled files:

    lein clean

To create a production build run:

    lein do clean, cljsbuild once min

And open your browser in `resources/public/index.html`. You will not
get live reloading, nor a REPL. 

## License

Copyright Aλex K © 2017 MIT
