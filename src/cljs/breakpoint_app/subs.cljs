(ns breakpoint-app.subs
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [re-frame.core :as re-frame :refer [reg-sub]]))

(re-frame/reg-sub
 :images
 (fn [db]
   (:images db)))

(re-frame/reg-sub
 :search-text
 (fn [db]
   (:search-input db)))
