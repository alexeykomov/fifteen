(ns state.state
  [:require [reagent.core :as r]])


(defn make-pieces [counter result max-counter]
    (def id counter)

    (if (< counter max-counter)
      (recur (inc counter) (conj result (str id)) max-counter)
      result)
  )

(def state (r/atom {
                     :etalon-order (conj (make-pieces 1 [] 16) (str 0))
                     :order (shuffle (conj (make-pieces 1 [] 16) (str 0)))
                     :delta-x 0
                     :delta-y 0
                     :dragging-id ""
                     :client-x 0
                     :client-y 0
                     :piece-rect {
                                   :height 0
                                   :width 0
                                   }
                     }))

(defn delta-x []
  (:delta-x @state))

(defn delta-y []
  (:delta-y @state))

(defn dragging-id []
  (:dragging-id @state))

(defn hollow-index []
  (let [all (:order @state)]
    (.indexOf all (str 0))
    ))

(defn draggable-index []
  (let [all (:order @state)]
    (.indexOf all (str (dragging-id)))
    ))

(defn rect-height []
  (:height (:piece-rect @state)))

(defn rect-width []
  (:width (:piece-rect @state)))

(defn col [index]
  (mod index 4))

(defn row [index]
  (/ (- index (mod index 4)) 4))

(defn where-hollow? []
  (let [draggable-index (draggable-index)
        hollow-index    (hollow-index)
        draggable-row (row draggable-index)
        draggable-col (col draggable-index)
        hollow-row (row hollow-index)
        hollow-col (col hollow-index)]
    (cond
      (and (= draggable-col hollow-col) (=(- draggable-row hollow-row) 1)) :up
      (and (= draggable-col hollow-col) (=(- draggable-row hollow-row) -1)) :down
      (and (= draggable-row hollow-row) (=(- draggable-col hollow-col) 1)) :left
      (and (= draggable-row hollow-row) (=(- draggable-col hollow-col) -1)) :right
      :else :not-adjacent
    )
  ))

(defn get-bounds-y []
  (cond (= (where-hollow?) :up)   {:min (- (rect-height)) :max 0}
        (= (where-hollow?) :down) {:min 0 :max (rect-height)}
        :else                   {:min 0 :max 0}
        ))

(defn get-bounds-x []
  (cond (= (where-hollow?) :left)  {:min (- (rect-width)) :max 0}
        (= (where-hollow?) :right) {:min 0 :max (rect-width)}
        :else                    {:min 0 :max 0}))

(defn bound-y! [y]
  (let [bounds-y (get-bounds-y)]
    (cond (< y (:min bounds-y)) (:min bounds-y)
          (> y (:max bounds-y)) (:max bounds-y)
          :else                 y)))

(defn bound-x! [x]
  (let [bounds-x (get-bounds-x)]
    (cond (< x (:min bounds-x)) (:min bounds-x)
          (> x (:max bounds-x)) (:max bounds-x)
          :else                 x)))

(defn swap-places! []
  (let [dragging-id (dragging-id)
        new-order (map
                         (fn [piece-id]
                           (cond
                             (= piece-id (str 0)) dragging-id
                             (= piece-id (str dragging-id)) (str 0)
                             :else piece-id
                             ))
                         (:order @state)
                         )]

    (reset! state (merge @state {
                                  :order new-order
                                  :dragging-id ""
                                  }))
    )
  )

(defn check-victory? []
  (= (:order @state) (:etalon-order @state))
  )