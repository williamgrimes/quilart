(ns quil-workflow.dynamic
  (:require [quil.core :as q])
  (:require [random-seed.core :refer :all])
  (:refer-clojure :exclude [rand rand-int rand-nth shuffle]))

(set-random-seed! 10)

(def nord-pallete
  (hash-map
   :nord0  [46  52  64 ]
   :nord1  [59  66  82 ]
   :nord2  [67  76  94 ]
   :nord3  [76  86  106]
   :nord4  [216 222 233]
   :nord5  [229 233 240]
   :nord6  [236 239 244]
   :nord7  [143 188 187]
   :nord8  [136 192 208]
   :nord9  [129 161 193]
   :nord10 [94  129 172]
   :nord11 [191 97  106]
   :nord12 [208 135 112]
   :nord13 [235 203 139]
   :nord14 [163 190 140]
   :nord15 [180 142 173]
   ))

(defn setup []
  (let [background-color (rand-nth (vals nord-pallete))]
  (q/smooth)
  (q/frame-rate 1)
  (q/background (apply q/color background-color))))

(defn draw-circle []
    (let [color1 (rand-nth (vals nord-pallete))
          color2 (rand-nth (vals nord-pallete))
          diam   (rand-int 100)
          x      (rand-int (q/width))
          y      (rand-int (q/height))]
      (q/stroke (apply q/color color1))
      (q/stroke-weight (rand-int 10))
      (q/fill (apply q/color color2))
      (q/ellipse x y diam diam)))

(defn draw-triangle []
    (let [color1 (rand-nth (vals nord-pallete))
          color2 (rand-nth (vals nord-pallete))
          diam   (rand-int 100)
          x      (rand-int (q/width))
          y      (rand-int (q/height))]
      (q/stroke (apply q/color color1))
      (q/stroke-weight (rand-int 10))
      (q/fill (apply q/color color2))
      (q/triangle (- x (/ diam 2)) (- y (/ diam 2))
                  x (+ y (/ diam 2))
                  (+ x (/ diam 2)) (- y (/ diam 2)))))

(defn draw []
  (loop [iteration 1]
    (println(str "Iteration " iteration))
    (draw-triangle)
    (if (>= iteration 300)
      (do (q/save "box.png")
          (q/exit)
          (println "Goodbye"))
      (recur (inc iteration)))))
