(ns hx-frame-message.events
  (:require [hx-frame.core :as hx-frame
             :refer [reg-event-fx reg-fx]]))

(def ^{:private true} active-toasts
  (atom {}))

(defn- schedule-toast-dismissal
  [{:keys [uuid time]}]
  (when (nil? (get @active-toasts uuid))
    (swap! active-toasts
           assoc
           uuid
           (js-invoke js/window
                      "setTimeout"
                      (fn []
                        (hx-frame/dispatch [:hx-frame-message/clear-toast
                                            {:uuid uuid}]))
                      time))))

(defn- create-alert
  [{:keys [db]} [_ alert]]
  {:db db})

(defn- clear-alert
  [{:keys [db]} [_ alert]]
  {:db db})

(defn- create-toast
  [{:keys [db]} [_ {:keys [message status time]
                    :or {time 3000
                         status :info}}]]
  (let [toast {:uuid (str (random-uuid))
               :message message
               :status status
               :time time}]
    {:db (update-in db [:hx-frame-message :toasts] conj toast)
     :schedule-toast-dismissal toast}))

(defn- clear-toast
  [{:keys [db]} [_ {clearing-toast-uuid :uuid}]]
  {:db (update-in db
                  [:hx-frame-message :toasts]
                  (partial filterv (fn [{toast-uuid :uuid}]
                                     (not= toast-uuid clearing-toast-uuid))))})

(defn register-events
  [{:keys [clear-alert-interceptors
           clear-toast-interceptors
           create-alert-interceptors
           create-toast-interceptors]
    :or {clear-alert-interceptors []
         clear-toast-interceptors []
         create-alert-interceptors []
         create-toast-interceptors []}}]

  (reg-event-fx :hx-frame-message/create-alert
                create-alert-interceptors
                create-alert)

  (reg-event-fx :hx-frame-message/clear-alert
                clear-alert-interceptors
                clear-alert)

  (reg-event-fx :hx-frame-message/create-toast
                create-toast-interceptors
                create-toast)

  (reg-event-fx :hx-frame-message/clear-toast
                clear-toast-interceptors
                clear-toast)

  (reg-fx :schedule-toast-dismissal schedule-toast-dismissal))
