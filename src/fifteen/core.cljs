(ns fifteen.core
  (:require [reagent.core :as r]
            [goog.dom]
            [component.field :as field]))


(enable-console-print!)

(println "This text is printed from src/fifteen/core.cljs. Go ahead and edit it and see reloading in action.")

(r/render-component [field/field]
  (goog.dom/getElement "root"))