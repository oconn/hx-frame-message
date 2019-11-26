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
  {:db (update-in db
                  [:hx-frame-message :alerts]
                  conj
                  (assoc alert :uuid (str (random-uuid))))})

(defn- clear-alert
  [{:keys [db]} [_ {clearing-alert-uuid :uuid}]]
  {:db (update-in db
                  [:hx-frame-message :alerts]
                  (partial filterv (fn [{alert-uuid :uuid}]
                                     (not= alert-uuid clearing-alert-uuid))))})

(defn- create-modal
  [{:keys [db]} [_ modal]]
  {:db (assoc-in db [:hx-frame-message :modal]
                 (assoc modal :uuid (str (random-uuid))))})

(defn- clear-modal
  [{:keys [db]} _]
  {:db (assoc-in db [:hx-frame-message :modal] nil)})

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
           clear-modal-interceptors
           create-alert-interceptors
           create-toast-interceptors
           create-modal-interceptors
           message-interceptors]
    :or {clear-alert-interceptors []
         clear-toast-interceptors []
         clear-modal-interceptors []
         create-alert-interceptors []
         create-toast-interceptors []
         create-modal-interceptors []
         message-interceptors []}}]

  (reg-event-fx :hx-frame-message/create-modal
                (into message-interceptors create-modal-interceptors)
                create-modal)

  (reg-event-fx :hx-frame-message/clear-modal
                (into message-interceptors clear-modal-interceptors)
                clear-modal)

  (reg-event-fx :hx-frame-message/create-alert
                (into message-interceptors create-alert-interceptors)
                create-alert)

  (reg-event-fx :hx-frame-message/clear-alert
                (into message-interceptors clear-alert-interceptors)
                clear-alert)

  (reg-event-fx :hx-frame-message/create-toast
                (into message-interceptors create-toast-interceptors)
                create-toast)

  (reg-event-fx :hx-frame-message/clear-toast
                (into message-interceptors clear-toast-interceptors)
                clear-toast)

  (reg-fx :schedule-toast-dismissal schedule-toast-dismissal))
