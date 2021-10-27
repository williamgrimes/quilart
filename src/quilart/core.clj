(ns quilart.core
  (:gen-class)
  (:require [quilart.color :as c])
  (:require [quil.core :as q])
  (:require [clojure.tools.cli :refer [parse-opts]])
  (:require [random-seed.core :refer :all])
  (:refer-clojure :exclude [rand rand-int rand-nth shuffle]))

;TODO rename project
;TODO output file as CLI
;TODO use svg instead of png
;FIXME fix background color seed


(defn draw-circle []
    (let [color1 (rand-nth (vals c/nord-palette))
          color2 (rand-nth (vals c/nord-palette))
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
    (let [color1 (rand-nth (vals c/nord-palette))
          color2 (rand-nth (vals c/nord-palette))
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

