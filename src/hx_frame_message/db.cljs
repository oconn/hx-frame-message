(ns hx-frame-message.db
  (:require [cljs.spec.alpha :as s]))

(def initial-state
  "Initial hx-frame-message state.

  All requests are associated into a map."
  {:hx-frame-message {:toasts []
                      :alerts []}})

;; Specs for each individual request
(s/def :hx-frame-message/toasts (s/coll-of any?))
(s/def :hx-frame-message/alerts (s/coll-of any?))
(s/def ::hx-frame-message (s/keys :req-un [::toasts
                                           ::alerts]))
