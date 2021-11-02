(ns quilart.core
  (:require [quilart.color :as c])
  (:require [quil.core :as q])
  (:require [clojure.tools.cli :refer [parse-opts]])
  (:gen-class))

;TODO use svg instead of png

(defn random-color [palette]
  (let [colors (vals palette)
        random-color (nth colors (int (q/random (- (count colors) 1))))]
    (apply q/color random-color)))

(defn draw-circle []
    (let [color1 (random-color c/nord-palette)
          color2 (random-color c/nord-palette)
          diam   (int (q/random 100))
          x      (int (q/random (q/width)))
          y      (int (q/random (q/height)))]
      (q/stroke color1)
      (q/stroke-weight (q/random 10))
      (q/fill color2)
      (q/ellipse x y diam diam)
      (println (str " circle \tx: " x "\ty " y "\tcolor1: " color1
                    "\tcolor2: " color2 "\tdiam: " diam))))

(defn draw-triangle []
    (let [color1 (random-color c/nord-palette)
          color2 (random-color c/nord-palette)
          diam   (int (q/random 100))
          x      (int (q/random (q/width)))
          y      (int (q/random (q/height)))]
      (q/stroke color1)
      (q/stroke-weight (q/random 10))
      (q/fill color2)
      (q/triangle (- x (/ diam 2)) (- y (/ diam 2))
                  x (+ y (/ diam 2))
                  (+ x (/ diam 2)) (- y (/ diam 2)))
      (println (str " triangle \tx: " x "\ty " y "\tcolor1: " color1
                    "\tcolor2: " color2 "\tdiam: " diam))))

(defn draw [count seed]
  (println "setting seed to:" seed)
  (q/random-seed seed)
  (let [cur-time (System/currentTimeMillis)
        seed (System/nanoTime)]

    (loop [iteration 1]
      (print (str "Iteration: " iteration "\t"))
      (draw-triangle)
      (draw-circle)
      (if (< iteration count)
        (recur (inc iteration))
        (do
          (let [filename (str "sketch-" cur-time "-seed-" seed ".png")]
            (q/save filename)
            (println "done saving" filename))
          (q/exit))
        ))))

(def cli-options
  [[nil "--help" "Print this help" :default false]
   ["-w" "--width WIDTH" "Image width." :default 1920
    :parse-fn #(Integer/parseInt %)]
   ["-h" "--height HEIGHT" "Image height" :default 1080
    :parse-fn #(Integer/parseInt %)]
   ["-n" "--count COUNT" "Number of iters." :default 200
    :parse-fn #(Integer/parseInt %)]
   ["-s" "--seed SEED" "Random seed value" :default 22
    :parse-fn #(Integer/parseInt %)]])

(defn -main [& args]
  (let [{:keys [options arguments summary errors]}
        (parse-opts args cli-options)]
    (if (:help options)
      (println summary)
      (q/defsketch sketch
        :draw (partial draw (:count options) (:seed options))
        :size [(:width options) (:height options)]))))
