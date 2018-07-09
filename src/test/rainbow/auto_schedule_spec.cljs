(ns test.rainbow.auto-schedule-spec
  (:require [cljs-time.core :as t]
            [cljs.test :refer (deftest is)]))

(def data {:prjName "项目一"
           :startTime #inst "2018-03-03"
           :endTime #inst "2018-06-03"
           :resource [{:id "r1" :name "zhangsan" :task ["t1"]}
                      {:id "r2" :name "lisi" :task ["t2" "t3"]}]
           :prjTask [{:id "t1" :name "task1" :workload 24 :startTime #inst "2018-06-01"}
                     {:id "t2" :name "task2" :workload 4}
                     {:id "t4" :name "task4" :workload 12}]})
(js/console.log 111
                (t/in-days (t/days 9)))

(defn create-alloc [s-date e-date]
  (let [limit (t/in-days (t/interval s-date e-date))
        balance (atom limit)
        cursor (atom s-date)]

    (fn [bill]
      (if (>= @balance bill)
        (do (reset! balance (- @balance bill))
            (reset! cursor (t/plus @cursor
                                   (t/days bill)))
            true)
        false))))

(defn ttt [data]
  (let [allocated-taskid (->> data
                              (:resource)
                              (map :task)
                              (flatten)
                              (set))
        prj-startTime (:startTime data)
        prj-endTime (:endTime data)
        allocated-task (->> data
                            (:prjTask)
                            (filter #(contains? allocated-taskid
                                                (:id %))))]
    allocated-task))

(deftest auto-schedule
  (is (= (ttt data) 1)))

(deftest a-failing-test
  (is (= 1 1)))
