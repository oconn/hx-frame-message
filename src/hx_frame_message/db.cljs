(ns hx-frame-message.db
  (:require [cljs.spec.alpha :as s]))


(def initial-state
  "Initial hx-frame-message state.

  All requests are associated into a map."
  {:hx-frame-message {:toasts []
                      :alerts []
                      :modal nil}})

;; Shared
(s/def ::message string?)
(s/def ::uuid string?)

;; Modal
(s/def ::modal (s/nilable (s/keys :req-un [::uuid])))

;; Toast
(s/def ::time number?)
(s/def ::status #{:info :error :success})

(s/def ::toast (s/keys :req-un [::time
                                ::status
                                ::message
                                ::uuid]))

;; Alert
(s/def ::title string?)
(s/def ::confirm-only boolean?)
(s/def ::confirm-action fn?)
(s/def ::confirm-copy string?)
(s/def ::confirm-status #{:default :warning})
(s/def ::deny-action fn?)
(s/def ::deny-copy string?)

(s/def ::alert (s/keys :req-un [::message
                                ::uuid]
                       :opt-un [::title
                                ::confirm-action
                                ::confirm-copy
                                ::deny-action
                                ::deny-copy
                                ::confirm-only
                                ::confirm-status]))

(s/def :hx-frame-message/toasts (s/coll-of ::toast))
(s/def :hx-frame-message/alerts (s/coll-of ::alert))
(s/def ::hx-frame-message (s/keys :req-un [::toasts
                                           ::alerts]))
