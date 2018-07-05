(ns rainbow.service.init
  (:require [firebase]))

(.initializeApp firebase {:apiKey "AIzaSyD10xC43behK2MVbhMRC0bRnb1kyCgw7SY"
                          :authDomain "hughfenghen.firebaseapp.com"
                          :databaseURL "https://hughfenghen.firebaseio.com"
                          :projectId "hughfenghen"
                          :storageBucket "hughfenghen.appspot.com"
                          :messagingSenderId "508484712713"})

(def fb firebase)
