(ns hx-frame-message.subscriptions
  (:require [hx-frame.core :refer [reg-sub]]))

(defn toasts
  [{:keys [hx-frame-message]}]
  (:toasts hx-frame-message))

(defn alert
  [{:keys [hx-frame-message]}]
  (-> hx-frame-message :alerts first))

(defn register-subscriptions
  []

  (reg-sub :hx-frame-message/toasts toasts)
  (reg-sub :hx-frame-message/alert alert))
