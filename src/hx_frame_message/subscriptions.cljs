(ns hx-frame-message.subscriptions
  (:require [hx-frame.core :refer [reg-sub]]))

(defn toasts
  [{:keys [hx-frame-message]}]
  (:toasts hx-frame-message))

(defn alert
  [{:keys [hx-frame-message]}]
  (-> hx-frame-message :alerts first))

(defn modal
  [{:keys [hx-frame-message]}]
  (:modal hx-frame-message))

(defn register-subscriptions
  []

  (reg-sub :hx-frame-message/toasts toasts)
  (reg-sub :hx-frame-message/alert alert)
  (reg-sub :hx-frame-message/modal modal))
