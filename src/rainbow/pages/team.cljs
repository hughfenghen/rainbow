(ns rainbow.pages.team
  (:require [rainbow.components.header :refer [header]]
            [reagent.core :as reagent :refer [atom]]
            [cljs-http.client :as http]
            [cljs.core.async :refer [<!]])
  (:require-macros [cljs.core.async.macros :refer [go]]))

(defonce projects (atom [{:name "项目一" :progress 90}
                         {:name "项目二" :progress 20}]))

(defn team-view []
  (go (let [response (<! (http/get "http://localhost:3000/api/team"))]
    (reset! projects (-> response :body :data))))
  (fn []
    [:div
     [header "团队视图"]
     [:section
      [:h4 {:style {:padding "0 3px"}} "前端研发组"]
      (for [prj @projects
            :let [name (:name prj)
                  progress (:progress prj)
                  key name]]
        ^{:key key}
        [prj-bar name progress])]]))

(defn prj-bar [name progress]
  [:div {:style {:border-width "1px 0"
                 :border-style "solid"
                 :border-color "#eee"
                 :margin "5px 0"
                 :line-height "30px"
                 :height "30px"}}
   [:div {:style {:height "28px"
                  :background-color "#8bc34a"
                  :position "absolute"
                  :width (str progress "%")}}]
   [:div {:style {:position "absolute"
                  :display "flex"
                  :padding "0 10px 0 5px"
                  :width "100%"}}
    [:div {:style {:flex 1}} name]
    [:div ">"]]])
