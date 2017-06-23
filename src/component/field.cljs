(ns component.field
  (:require [reagent.core :as r]
            [component.board :as board]
            [state.state :as state]
            [goog.dom]))


(defn onmousemove [event]
  (let [new-client-x (.-clientX event)
        new-client-y (.-clientY event)
        client-x     (:client-x @state/state)
        client-y     (:client-y @state/state)
        delta-x      (state/bound-x! (- new-client-x client-x))
        delta-y      (state/bound-y! (- new-client-y client-y))]
    (if (not= (state/dragging-id) "")
        (reset! state/state
                (merge @state/state
                       {:delta-x delta-x
                        :delta-y delta-y}))
    )
  ))

(defn onmouseup []
  (js/console.log "onmouseup")
  (if (not= (state/dragging-id) "")
    (if (or (> (js/Math.abs (state/delta-x)) (/ (state/rect-width) 2))
          (> (js/Math.abs (state/delta-y)) (/ (state/rect-height) 2)))
      (do
        (state/swap-places!)
        (if (state/check-victory?)
            (js/alert "You achieved victory!!!")
            )
        )
      (reset! state/state (merge @state/state {:dragging-id ""}))
    ))
  )

(defn field []
  (r/create-class
    {
      :component-did-mount (fn [] (println "component did mount"))
      :component-will-unmount (fn [])
      :reagent-render (fn []
                       [:div {:class       "field"
                               :onMouseMove onmousemove
                               :onMouseUp   onmouseup}
                        (board/board)])
     }))

(add-watch state/state :re-render
  (fn [key state old-val new-val]
    (println @state/state)
    (r/render-component [field] (goog.dom/getElement "root"))))
