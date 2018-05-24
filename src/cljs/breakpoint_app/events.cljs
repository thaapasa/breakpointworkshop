(ns breakpoint-app.events
  (:require [re-frame.core :as re-frame :refer [reg-event-db reg-event-fx]]
            [ajax.core :as ajax]
            [re-frame.core :refer [dispatch]]
            [day8.re-frame.http-fx] ; required to initialize http fx handler
            [breakpoint-app.db :as db]))

(reg-event-db
 :initialize-db
 (fn [_ _]
   db/default-db))

(reg-event-db
  :add-images
  (fn [db [_ response]]
    (println response)
    (update db :images into response)
    ; the list of images goes in the database map under the key :images (see db.cljs).
  ))


(reg-event-db
  :clear-all-images
  (fn [db _]
    (assoc db :images [])
  ))


(reg-event-db
  :update-search-box
  (fn [db [_ x]]
    (println x)
    (assoc db :search-input x)
  ))

(reg-event-fx
  :set-search-text
  (fn [db [_ text]]
    (println "Trololo")
    (dispatch [:update-search-box text])
    {:http-xhrio {:method          :get
                  :uri             "/api/search"
                  :response-format (ajax/json-response-format {:keywords? true})
                  :on-success      [:add-images]}}
  ))


;; ;Used in ex. 2:
(reg-event-fx
   :load-random-giphy
   (fn [{:keys [db]} _]
     {:http-xhrio {:method          :get
                   :uri             "/api/random"
                   :response-format (ajax/json-response-format {:keywords? true})
                   :on-success      [:add-images]}
      :db         db}))

;; ;Used in ex.3:
;; (defonce debounces (atom {}))

;; (re-frame/reg-fx
;;   :dispatch-debounced
;;   (fn [{:keys [id dispatch timeout]}]
;;     (js/clearTimeout (@debounces id))
;;     (swap! debounces assoc id (js/setTimeout
;;                                 (fn []
;;                                   (re-frame/dispatch dispatch)
;;                                   (swap! debounces dissoc id))
;;                                 timeout))))
