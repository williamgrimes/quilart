(ns quilart.core
  (:gen-class)
  (:require [quil.core :as q])
  (:require [clojure.tools.cli :refer [parse-opts]])
  (:require [random-seed.core :refer :all])
  (:refer-clojure :exclude [rand rand-int rand-nth shuffle]))

;TODO rename project
;TODO output file as CLI
;TODO use svg instead of png
;TODO move color-schemes to palettes.clj
;FIXME fix background color seed

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

(defn draw-circle []
    (let [color1 (rand-nth (vals nord-pallete))
          color2 (rand-nth (vals nord-pallete))
          diam   (rand-int 100)
          x      (rand-int (q/width))
          y      (rand-int (q/height))]
      (q/stroke (apply q/color color1))
      (q/stroke-weight (rand-int 10))
      (q/fill (apply q/color color2))
      (q/ellipse x y diam diam)
      (println (str " circle \tx: " x "\ty " y "\tcolor1: " color1
                    "\tcolor2: " color2 "\tdiam: " diam))))

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
                  (+ x (/ diam 2)) (- y (/ diam 2)))
      (println (str " triangle \tx: " x "\ty " y "\tcolor1: " color1
                    "\tcolor2: " color2 "\tdiam: " diam))))

(defn draw [count]
  (loop [iteration 1]
    (print (str "Iteration: " iteration "\t"))
    (draw-circle)
    (if (>= iteration count)
      (do (q/save "box.png")
          (q/exit))
      (recur (inc iteration)))))

(def cli-options [[nil "--help" "Print this help" :default false]
                  ["-w" "--width WIDTH" "Image width." :default 1920
                   :parse-fn #(Integer/parseInt %)]
                  ["-h" "--height HEIGHT" "Image height" :default 1080
                   :parse-fn #(Integer/parseInt %)]
                  ["-n" "--count COUNT" "Number of iters." :default 200
                   :parse-fn #(Integer/parseInt %)]
                  ["-s" "--seed SEED" "Random seed value" :default 2
                   :parse-fn #(Integer/parseInt %)]])

(defn -main [& args]
  (let [{:keys [options arguments summary errors]}
        (parse-opts args cli-options)]
    (if (:help options)
      (println summary)
      (do (set-random-seed! (:seed options))
          (q/defsketch sketch
            :bgcolor (rand-nth (vals nord-pallete))
            :draw (partial draw (:count options))
            :size [(:width options) (:height options)])))))

