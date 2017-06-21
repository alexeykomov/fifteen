(ns component.piece
  (:require [reagent.core :as r]
            [state.state :as state]
            [goog.dom :as dom]))


(defn hollow [piece-id]
  [:li {:key (str piece-id) :class "hollow"}])

(defn delta-x []
  (:delta-x @state/state))

(defn delta-y []
  (:delta-y @state/state))

(defn onmousedown [event]
  (js/console.log "onmousedown")
  (let [target (.-currentTarget event)
        dragging-id (.-id target)
        rect (. target getBoundingClientRect)
        element-x (.-x rect)
        element-y (.-y rect)
        client-x (.-clientX event)
        client-y (.-clientY event)
        offset-x (- client-x element-x)
        offset-y (- client-y element-y)
        ]
    (reset! state/state (merge @state/state {:dragging-id dragging-id
                                             :client-x client-x
                                             :client-y client-y
                                             :piece-rect {
                                                           :width (.-width rect)
                                                           :height (.-height rect)
                                                           }
                                             }))
    (println offset-x)
    (println offset-y)
    ))

(defn piece [piece-id]
  (if (= piece-id 0)
      (hollow piece-id)
      (do
      [:li {:id piece-id
            :key         (str piece-id)
            :style        (if (= piece-id (:dragging-id @state/state))
                              {:transform (str "translate3d("
                                           (delta-x) "px,"
                                           (delta-y) "px,0)")}
                              {})
            :class       "piece"
            :onMouseDown onmousedown
            }
       [:span piece-id]])))

