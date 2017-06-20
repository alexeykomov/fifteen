(ns component.piece
  (:require [reagent.core :as r]
            [state.state :as state]
            [goog.dom :as dom]))


(defn hollow [piece-id]
  [:li {:key (str piece-id) :class "hollow"}])

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
                                             :client-y client-y}))
    (println offset-x)
    (println offset-y)
  ))

(defn onmousemove [event]
  (let [new-client-x (.-clientX event)
        new-client-y (.-clientY event)
        client-x (:offset-x @state/state)
        client-y (:offset-y @state/state)]
    (reset! state/state (merge @state/state {:delta-x (- new-client-x client-x)
                                             :delta-y (- new-client-y client-y)
                                              }))
    (js/console.log "onmousemove")
  ))

(defn onmouseup []
  (js/console.log "onmouseup"))

(defn delta-x []
  (:delta-x @state/state))

(defn delta-y []
  (:delta-y @state/state))

(defn piece [piece-id]
  (if (= piece-id 0)
      (hollow piece-id)
      [:li {:id piece-id
            :key         (str piece-id)
            :style        (if (= piece-id (:dragging-id @state/state))
                              {:transform (str "translate3d("
                                           (delta-x) "px,"
                                           (delta-y) "px,0)")}
                              {})
            :class       "piece"
            :onMouseDown onmousedown
            :onMouseMove onmousemove
            :onMouseUp   onmouseup}
       [:span piece-id]]))

