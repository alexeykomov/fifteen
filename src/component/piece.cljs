(ns component.piece
  (:require [reagent.core :as r]
            [state.state :as state]))


(defn hollow [piece-id]
  [:li {:key (str piece-id) :class "hollow"}])

(defn onmousedown []
  (js/console.log "onmousedown"))

(defn onmousemove []
  (js/console.log "onmousemove"))

(defn onmouseup []
  (js/console.log "onmouseup"))

(defn delta-x []
  (:delta-x @state/state))

(defn delta-y []
  (:delta-y @state/state))

(defn piece [piece-id]
  (if (= piece-id 0)
      (hollow piece-id)
      [:li {:key         (str piece-id)
            :style        {:transform (str "translate3d("
                                           (delta-x) "px,"
                                           (delta-y) "px,0)")}
            :class       "piece"
            :onMouseDown onmousedown
            :onMouseMove onmousemove
            :onMouseUp   onmouseup}
       [:span piece-id]]))

