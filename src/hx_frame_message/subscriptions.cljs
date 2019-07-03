(ns hx-frame-message.subscriptions
  (:require [hx-frame.core :refer [reg-sub]]))

(defn toasts
  [{:keys [hx-frame-message]}]
  (:toasts hx-frame-message))

(defn register-subscriptions
  []

  (reg-sub :hx-frame-message/toasts toasts))
