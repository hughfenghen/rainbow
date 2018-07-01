(ns rainbow.core
  (:require [reagent.core :as r :refer [atom]]
            [rainbow.pages.team :refer [team-view]]
            [rainbow.pages.gantt.view :refer [gantt-view]]
            [goog.events :as events]
            [goog.history.EventType :as EventType]
            [secretary.core :as secretary :refer-macros [defroute]])
  (:import goog.History))

(secretary/set-config! :prefix "#")

(defonce history (History.))
(defonce route-handler #(secretary/dispatch! (.-token %)))

(defn render [comp]
  (r/render-component [comp]
                            (js/document.getElementById "app")))

(defroute home-path "/" []
  (prn "home-paths113")
  (render team-view))

(defroute gantt "/gantt" []
  (render gantt-view))

(defn start []
  (devtools.core/install! [:formatters :hints :async])
  (goog.events/listen history EventType/NAVIGATE route-handler)
  (.setEnabled history true))

(defn ^:export init []
  ;; init is called ONCE when the page loads
  ;; this is called in the index.html and must be exported
  ;; so it is available even in :advanced release builds
  (start))

(defn stop []
  ;; stop is called before any code is reloaded
  ;; this is controlled by :before-load in the config
  (goog.events/unlisten history EventType/NAVIGATE route-handler)
  (js/console.log "stop"))
