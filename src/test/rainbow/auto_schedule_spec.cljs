(ns test.rainbow.auto-schedule-spec
  (:require [cljs-time.core :as t]
            [cljs-time.coerce :refer [to-date]]
            [cljs.test :refer (deftest is)]))

(def data {:prjName "项目一"
           :startTime #inst "2018-03-03"
           :endTime #inst "2018-06-03"
           :resource [{:id "r1" :name "zhangsan" :task ["t1" "t4"]}
                      {:id "r2" :name "lisi" :task ["t2" "t3"]}]
           :prjTask [{:id "t1" :name "task1" :workload 24}
                     {:id "t2" :name "task2" :workload 4}
                     {:id "t4" :name "task4" :workload 12}]})

(defn create-alloc [s-date e-date]
  (let [limit (t/in-days (t/interval s-date e-date))
        balance (atom limit)
        cursor (atom (t/date-time s-date))]

    (fn [bill]
      (if (>= @balance bill)
        (let [cur-start-date @cursor
              end-date (t/plus (t/date-time @cursor)
                               (t/days bill))]
          (reset! balance (- @balance bill))
          (reset! cursor end-date)
          (map to-date [cur-start-date end-date]))
        nil))))

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
                                                (:id %))))
        alloc (create-alloc prj-startTime prj-endTime)]

    (map #(if-let [plan (alloc (:workload %))]
            (assoc % :plan plan)
            %)
         allocated-task)))

(deftest auto-schedule
  (is (= (ttt data) [#inst "2018-03-03" #inst "2018-03-13"])))

(deftest a-failing-test
  (is (= 1 1)))
