(ns breakpoint-app.views
  (:require [breakpoint-app.animation.animation-core :as a]
            [breakpoint-app.animation.animation-views :as aw]
            [re-frame.core :refer [subscribe dispatch]]))

(defn- results-item [image]
  [:div.results-item
   [:div.results-item-header
    [:i.medium.material-icons.remove-button
     {:on-click #(println "remove")}
     "cancel"]
    [:i.medium.material-icons.add-to-list-button
     {:on-click #(println "save")}
     "add_circle"]]
   [:img {:src (:url image)}]])

(defn- results-box []
  (let [images (subscribe [:images])]
    (fn []
      (into
        [:div.results-box]
        (map results-item @images)))))

(defn- search-box []
  (let [text (subscribe [:search-text])]
    (fn []
      [:input.search-input
       {:type "text"
        :placeholder "This does nothing for now :("
        :value @text
        :on-change #(dispatch [:set-search-text (.-value (.-target %))])}])))

(defn animation-toggle []
  [:button.animation-toggle])

(defn main-panel []
  (fn []
    [:div.main-wrapper
     [aw/animation-header :header]
     [:div.main
      [:h1 "Breakpoint Giphy"]
      [:div.input-container
       [search-box]
      ]
      [:div.input-container
       [:button.random-button
        {:on-click #(dispatch [:load-random-giphy])}
        "Load random!"]
       [:button.clear-button
        {:on-click #(dispatch [:clear-all-images])}
        "Clear all!"]
      ]
      [results-box]]
     [aw/animation-header :footer]
     [:div.toggle-button-container
      ;[animation-toggle]
      ]]))
