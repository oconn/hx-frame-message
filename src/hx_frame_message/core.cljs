(ns hx-frame-message.core
  (:require
   [hx.react :refer [defnc]]
   [hx-frame.core :as hx-frame]
   [hx-frame-message.events :as hxm-events]
   [hx-frame-message.subscriptions :as hxm-subscriptions]))

(defn register-events
  "Registers hx-frame-message events and request handler"
  [opts]
  (hxm-events/register-events opts))

(defn register-subscriptions
  "Registers hx-frame-message subscriptions"
  []
  (hxm-subscriptions/register-subscriptions))

(defn register-all
  "Registers both hx-frame-message events & subscriptions"
  [{:keys [event-options]}]
  (register-events event-options)
  (register-subscriptions))

(defnc Toast
  [{:keys [uuid message status] :as props}]
  [:li {:class ["message--toast-message-container"
                (case status
                  :success "message--toast-message-container-success"
                  :info "message--toast-message-container-info"
                  :error "message--toast-message-container-error"
                  (js/console.error "Message status '"
                                    status
                                    "'not supported"))]}
   [:p {:class ["message--toast-message"]}
    message]])

(defnc HXFrameMessage
  [{:keys [TransitionGroup CSSTransition]}]
  (let [toasts (hx-frame/subscribe [:hx-frame-message/toasts])]
    (if (and (some? TransitionGroup)
             (some? CSSTransition))
      [:ul {:class ["message--toasts-container"]}
       [TransitionGroup {:component nil}
        (for [{:keys [uuid] :as toast} toasts]
          [CSSTransition {:timeout 300
                          :key uuid
                          :in true
                          :appear true
                          :mountOnEnter true
                          :unmountOnExit true
                          :classNames "message--item"}
           [Toast toast]])]]
      [:ul {:class ["message--toasts-container"]}
       (for [{:keys [uuid] :as toast} toasts]
         ^{:key uuid} [Toast toast])])))
