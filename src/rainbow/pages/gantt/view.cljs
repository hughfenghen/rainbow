(ns rainbow.pages.gantt.view
  (:require [rainbow.components.header :refer [header]]
            [reagent.core :as r :refer [atom]]
            [echarts]
            [cljs-http.client :as http]
            [cljs.core.async :refer [<!]])
  (:require-macros [cljs.core.async.macros :refer [go]]))

(defn show-revenue-chart
  []
  (let [chart (echarts/init (js/document.getElementById "rev-chartjs"))
        chart-data {:title  {:text "工作流甘特图"}
                    :legend  {:data  ["计划完成时间" "实际完成时间"]}
                    :xAxis  {:type "time", :position "top"}
                    :yAxis  {:type "category", :data ["测试" "开发" "设计" "总进度"]}
                    :tooltip {}
                    :series [{:name "计划开始时间"
                              :type "bar"
                              :stack "总量"
                              :itemStyle {:normal {:color "rgba(0,0,0,0)"}}
                              :data [#inst "2017-09-23"
                                     #inst "2017-09-10"
                                     #inst "2017-09-01"
                                     #inst "2017-09-01"]}

                             {:name "计划完成时间"
                              :type "bar"
                              :stack "总量"
                              :data [#inst "2017-09-30"
                                     #inst "2017-09-20"
                                     #inst "2017-09-15"
                                     #inst "2017-09-30"]}

                             {:name "实际完成时间"
                              :type "bar"
                              :stack "总量"
                              :data [#inst "2017-09-30"
                                     #inst "2017-09-23"
                                     #inst "2017-09-14"
                                     #inst "2017-09-30"]}]}]
    (.setOption chart (clj->js chart-data))))

(defn gantt-view []
  (r/create-class
   {:component-did-mount #(show-revenue-chart)
    :display-name "chartjs-component"
    :reagent-render (fn []
                      [:canvas {:id "rev-chartjs" :width "700" :height "300"}])}))
