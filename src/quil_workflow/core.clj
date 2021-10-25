(ns quil-workflow.core
  (:require [quil.core :as q])
  (:require [quil-workflow.dynamic :as dynamic])
  (:require [random-seed.core :refer :all])
  (:refer-clojure :exclude [rand rand-int rand-nth shuffle])
  )

(set-random-seed! 7)

(q/defsketch example
  :title "Oh so many grey circles"
  :setup dynamic/setup
  :draw dynamic/draw
  :size [1920 1080])

(defn -main [& args])
