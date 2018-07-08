(ns rainbow.service.init
  (:require [firebase]
            ["firebase/firestore"]
            ["firebase/functions"]))

(def fb firebase)

(set! js/window.fb fb)

(defn- init []
  (if (= 0 (.. fb -apps -length))
    (.initializeApp fb #js {:apiKey "AIzaSyD10xC43behK2MVbhMRC0bRnb1kyCgw7SY"
                            :authDomain "hughfenghen.firebaseapp.com"
                            :databaseURL "https://hughfenghen.firebaseio.com"
                            :projectId "hughfenghen"
                            :storageBucket "hughfenghen.appspot.com"
                            :messagingSenderId "508484712713"}))

  (.. fb
      firestore
      (settings #js {:timestampsInSnapshots true})))

(init)
