(ns hx-frame-message.core
  (:require
   [goog.object :as gobj]
   [hx.hooks :as hooks]
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

(defnc ToastContainer
  [{:keys [toasts TransitionGroup CSSTransition]}]
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
       ^{:key uuid} [Toast toast])]))

(def no-scroll-class "hx-frame-message--no-scroll")

(defnc AlertContainer
  [{:keys [alert CSSTransition]}]
  (let [{:keys [message
                title
                confirm-action
                confirm-copy
                deny-action
                deny-copy
                confirm-only
                confirm-status]
         :or {confirm-action identity
              confirm-copy "Yes"
              deny-action identity
              deny-copy "No"
              confirm-status :default
              confirm-only false}}
        alert]

    (hooks/useEffect
     (fn []
       (let [class-list (gobj/getValueByKeys js/document "body" "classList")]
         (if (and (some? alert)
                  (some? class-list))
           (js-invoke class-list "add" no-scroll-class)
           (js-invoke class-list "remove" no-scroll-class)))
       (fn []
         (let [class-list (gobj/getValueByKeys js/document "body" "classList")]
           (when (some? class-list)
             (js-invoke class-list "remove" no-scroll-class)))))
     [(:uuid alert)])

    (when (some? alert)
      [:div {:class ["message--alert-underlay"]}
       [:div {:class ["message--alert-container"]}
        (when (some? title)
          [:h3 {:class ["message--alert-title"]} title])

        [:p {:class ["message--alert-message"]} message]

        [:div {:class (cond-> ["message--actions-container"]
                        (true? confirm-only)
                        (conj "message--actions-container-confirm-only"))}
         [:button
          {:class ["message--actions-confirm-button"
                   (str "message--actions-confirm-button-"
                        (name confirm-status))]
           :on-click (fn []
                       (hx-frame/dispatch [:hx-frame-message/clear-alert
                                           {:uuid (:uuid alert)}])
                       (confirm-action))}
          confirm-copy]

         (when-not (true? confirm-only)
           [:button
            {:class ["message--actions-deny-button"]
             :on-click (fn []
                         (hx-frame/dispatch [:hx-frame-message/clear-alert
                                             {:uuid (:uuid alert)}])
                         (deny-action))}
            deny-copy])]]])))

(defnc HXFrameMessage
  [props]
  (let [toasts (hx-frame/subscribe [:hx-frame-message/toasts])
        alert (hx-frame/subscribe [:hx-frame-message/alert])]
    [:<>
     [ToastContainer (merge
                      (dissoc props :children)
                      {:toasts toasts})]
     [AlertContainer (merge
                      (dissoc props :children)
                      {:alert alert})]]))
