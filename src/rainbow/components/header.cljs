(ns rainbow.components.header
  (:require [reagent.core :as reagent :refer [atom]]))

(defn header
  [title]
  [:div {:style {:height "50px" :width "100%"}}
   [:nav.navbar.navbar-default.navbar-fixed-top
    [:div.container {:style {:text-align "center"
                             :line-height "50px"
                             :font-size "20px"}}
     title]]])
