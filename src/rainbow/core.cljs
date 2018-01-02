(ns rainbow.core
  (:require [reagent.core :as reagent :refer [atom]])
  (:require [rainbow.pages.team :refer [team-view]]))

;; define your app data so that it doesn't get over-written on reload

(defonce app-state (atom {:text "Hello world!"}))

(defn app []
  [:div
    [team-view]])

(defn start []
  (.log js/console @app-state)
  (reagent/render-component [app]
                            (. js/document (getElementById "app"))))

(defn ^:export init []
  ;; init is called ONCE when the page loads
  ;; this is called in the index.html and must be exported
  ;; so it is available even in :advanced release builds
  (start))

(defn stop []
  ;; stop is called before any code is reloaded
  ;; this is controlled by :before-load in the config
  (js/console.log "stop"))
