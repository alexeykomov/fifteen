(ns component.board
  (:require [reagent.core :as r]
            [component.piece :as piece]
            [state.state :as state]
            [goog.dom]))


(defn board-children []
  (map piece/piece (@state/state :order)))

(defn board []

  [:ul {:class "board"}
    (board-children)
    ])

(add-watch state/state :re-render (fn [key state old-val new-val]
                              (r/render board (goog.dom/getElement "root"))))
