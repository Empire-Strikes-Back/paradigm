(ns paradigm.main
  (:gen-class)
  (:require
   [clojure.core.async :as a :refer [chan go go-loop <! >! <!! >!!  take! put! offer! poll! alt! alts! close! onto-chan!
                                     pub sub unsub mult tap untap mix admix unmix pipe
                                     timeout to-chan  sliding-buffer dropping-buffer
                                     pipeline pipeline-async]]
   [clojure.string]
   [clojure.spec.alpha :as s]
   [clojure.java.io :as io]
   [cljfx.api]
   
   [expanse.fs.runtime.core :as fs.runtime.core])
  (:import
   (javafx.event Event EventHandler)
   (javafx.stage WindowEvent)
   (javafx.scene.control DialogEvent Dialog ButtonType ButtonBar$ButtonData)
   #_javafx.application.Platform))

(println "clojure.compiler.direct-linking" (System/getProperty "clojure.compiler.direct-linking"))
(clojure.spec.alpha/check-asserts true)
(do (set! *warn-on-reflection* true) (set! *unchecked-math* true))

(defn stage
  [{:as opts
    :keys []}]
  {:fx/type :stage
   :showing true
   :width 1024
   :height 768
   :scene {:fx/type :scene
           :root {:fx/type :h-box
                  :children [{:fx/type :label :text "paradigm"}]}}})

(defonce stateA (atom nil))

(defn -main [& args]
  (println ::-main)
  (let [data-dir (fs.runtime.core/path-join (System/getProperty "user.dir"))
        renderer (cljfx.api/create-renderer)]
    (reset! stateA {:fx/type stage
                    ::renderer renderer})
    (add-watch stateA :watch-fn (fn [k stateA old-state new-state] (renderer new-state)))

    (javafx.application.Platform/setImplicitExit true)
    (renderer @stateA)
    #_(cljfx.api/mount-renderer stateA render)

    (go)))

(comment

  (require
   '[paradigm.main]
   :reload)

  (-main)

  (def renderer (::renderer @stateA))

  (renderer @stateA)

  ;
  )