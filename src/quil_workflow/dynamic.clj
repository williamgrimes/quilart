(ns quil-workflow.dynamic
  (:require [quil.core :as q]))

(def nord-pallete
  (hash-map
   :nord0  [46  52      64 ]
   :nord1  [59  66      82 ]
   :nord2  [67  76      94 ]
   :nord3  [76  86      106]
   :nord4  [216 222     233]
   :nord5  [229 233     240]
   :nord6  [236 239     244]
   :nord7  [143 188     187]
   :nord8  [136 192     208]
   :nord9  [129 161     193]
   :nord10 [94  129     172]
   :nord11 [191 97      106]
   :nord12 [208 135     112]
   :nord13 [235 203     139]
   :nord14 [163 190     140]
   :nord15 [180 142     173]
   ))

(def chosen-color (rand-nth (vals nord-pallete)))

(defn setup []
  (let [background-color (rand-nth (vals nord-pallete))]
  (q/smooth)
  (q/frame-rate 1)
  (q/background (q/color (nth background-color 0)
                         (nth background-color 1)
                         (nth background-color 2)))))

(defn draw []
  (let [color1 (rand-nth (vals nord-pallete))
        color2 (rand-nth (vals nord-pallete))]
    (q/stroke (q/color(nth color1 0) (nth color1 1) (nth color1 2)))
    (q/stroke-weight (q/random 10))
    (q/fill (q/color (nth color2 0) (nth color2 1) (nth color2 2))))

  (let [diam (q/random 100)
        x    (q/random (q/width))
        y    (q/random (q/height))]
    (q/ellipse x y diam diam)))
