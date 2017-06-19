(ns component.piece
  (:require [reagent.core :as r]))

(defn hollow []
  [:li {:class "hollow"}])

(defn piece [piece-id]
  (if (= piece-id 0)
    (hollow)
    [:li {:style {}
          :class "piece"
          :onmousedown ()
          :ontouchstart ()}
      [:span piece-id]]
      ))

