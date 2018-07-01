(ns rainbow.pages.gantt.view
  (:require [rainbow.components.header :refer [header]]
            [reagent.core :as r :refer [atom]]
            [echarts]
            [cljs-http.client :as http]
            [cljs.core.async :refer [<!]])
  (:require-macros [cljs.core.async.macros :refer [go]]))

(defn show-revenue-chart
 []
 (let [myChart (echarts/init (js/document.getElementById "rev-chartjs"))
       chart-data {:title {:text "ECharts 入门示例"},
                   :tooltip {}
                   :xAxis {:data ["衬衫" "羊毛衫" "雪纺衫" "裤子" "高跟鞋" "袜子"]}
                   :yAxis {}
                   :series [{:name "销量"
                             :type "bar"
                             :data [5 20 36 10 10 20]}]}]
     (.setOption myChart (clj->js chart-data))))

(defn gantt-view []
  (r/create-class
    {:component-did-mount #(show-revenue-chart)
     :display-name        "chartjs-component"
     :reagent-render      (fn []
                            [:canvas {:id "rev-chartjs" :width "700" :height "300"}])}))
