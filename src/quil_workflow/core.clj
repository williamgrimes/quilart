(ns quil-workflow.core
  (:require [quil.core :as q])
  (:require [quil-workflow.dynamic :as dynamic])
  (:require [clojure.tools.cli :refer [parse-opts]]))

(def cli-config [[nil "--help" "Print this help" :default false]
                 ["-w" "--width WIDTH" "Image width" :default 400]
                 ["-h" "--height HEIGHT" "Image height" :default 400]
                 ["-s" "--seed SEED" "Random seed value" :default 42]
                 ])

(defn -main [& args]
  (let [{:keys [options arguments summary errors]}
        (parse-opts args cli-config)]
    (if (:help options)
      (println summary)
      (q/defsketch sketch
       :setup dynamic/setup
       :draw dynamic/draw
       :size [(:width options) (:height options)]))))
