(ns state.state
  [:require [reagent.core :as r]])

(defn make-pieces [counter result]
    (def id counter)

    (if (< counter 16)
      (recur (inc counter) (conj result id))
      result)
  )


(def state (r/atom {
                     :order (make-pieces 0 [])
                     :delta-x 0
                     :delta-y 0
                     }))


