(ns fifteen.core
  (:require [reagent.core :as r]
            [goog.dom :as dom]
            [component.board :as board]))

(enable-console-print!)

(println "This text is printed from src/fifteen/core.cljs. Go ahead and edit it and see reloading in action.")

(r/render board/board
  (goog.dom/getElement "root"))