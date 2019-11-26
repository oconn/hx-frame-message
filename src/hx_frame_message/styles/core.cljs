(ns hx-frame-message.styles.core
  (:require [hx-comp.core :refer [gs create-font-styles]]))

(def hx-frame-message-styles
  {".message--toasts-container"
   {:position :fixed
    :bottom (gs [:spacing :p20])
    :right (gs [:spacing :p20])
    :width (str "calc(100% - " (gs [:spacing :p20]) ")")
    :max-width "300px"
    :pointer-events :none
    :z-index 9999}

   ".message--toast-message-container"
   {:overflow :hidden
    :border-radius (gs [:radius :r4])
    :box-shadow (gs [:shadows :shadow-20])
    :margin-bottom (gs [:spacing :p4])
    :padding [(gs [:spacing :p4])
              (gs [:spacing :p20])]
    :transition "opacity 300ms ease-in"

    "&.message--item-enter, .message--item-appear"
    {:opacity 0}

    "&.message--item-enter.message--item-enter-active,
    .message--item-appear.message--item-appear-active"
    {:opacity 1}

    "&.message--item-exit"
    {:opacity 1}

    "&.message--item-exit.message--item-exit-active"
    {:opacity 0}

    "&:last-child"
    {:margin-bottom (gs [:spacing :p0])}}

   ".message--toast-message-container-success"
   {:background-color (gs [:colors :success-500])}

   ".message--toast-message-container-info"
   {:background-color (gs [:colors :warning-500])}

   ".message--toast-message-container-error"
   {:background-color (gs [:colors :error-500])}

   ".message--toast-message"
   (create-font-styles
    {:style :caption-20
     :color :true-white
     :family :secondary})

   ;; Alert Styles
   ".hx-frame-message--no-scroll"
   {:overflow :hidden}

   ".message--alert-underlay"
   {:background-color "rgba(0,0,0,0.4)"
    :width "100%"
    :height "100%"
    :position :fixed
    :top 0
    :left 0
    :display :flex
    :justify-content :center
    :align-items :center}

   ".message--alert-container"
   {:width "620px"
    :max-width "100%"
    :padding [(gs [:spacing :p20])
              (gs [:spacing :p40])]
    :background-color (gs [:colors :true-white])
    :border-radius (gs [:radius :r4])}

   ".message--alert-title"
   (merge (create-font-styles
           {:style :title-20
            :color :gray-scale-600
            :family :primary})
          {:text-align :center})

   ".message--alert-message"
   (create-font-styles
    {:style :caption-30
     :color :gray-scale-500
     :family :primary})

   ".message--actions-container"
   {:display :flex
    :justify-content :center
    :margin-top (gs [:spacing :p28])

    "& > button"
    {:min-width "calc(50% - 10px)"

     "&:first-child"
     {:margin-right "20px"}}}

   ".message--actions-container.message--actions-container-confirm-only"
   {"& > button:first-child"
    {:margin-right (gs [:spacing :p0])}}

   ".message--actions-confirm-button, .message--actions-deny-button"
   ;; Copied from hx-comp button styles
   (merge
    (create-font-styles {:style :caption-30
                         :color :primary-500
                         :family :primary})
    {:padding [(gs [:spacing :p8])
               (gs [:spacing :p20])]
     :white-space :nowrap
     :text-decoration :none
     :cursor :pointer
     :outline :none
     :position :relative
     :transition (str "background-color 0.2s ease, "
                      "color 0.2s ease, "
                      "border-color 0.2s ease")
     :border (gs [:borders :border-primary-500-2])

     "&:disabled"
     {:background-color (gs [:colors :gray-scale-50])
      :color (gs [:colors :gray-scale-300])
      :border (gs [:borders :border-100-2])
      :cursor :default}})

   ".message--actions-confirm-button"
   {:background-color (gs [:colors :primary-500])
    :color (gs [:colors :true-white])

    "&:hover"
    {:background-color (gs [:colors :primary-600])
     :border-color (gs [:colors :primary-600])}}

   ".message--actions-confirm-button.message--actions-confirm-button-warning"
   {:background-color (gs [:colors :error-500])
    :border-color (gs [:colors :error-500])

    "&:hover"
    {:background-color (gs [:colors :error-600])
     :border-color (gs [:colors :error-600])}}

   ".message--actions-deny-button"
   {:background-color (gs [:colors :true-white])

    "&:hover"
    {:background-color (gs [:colors :primary-500])
     :color (gs [:colors :true-white])}}

   ;; Modal Styles
   ".message--modal-underlay"
   {:position :absolute
    :width "100%"
    :height "100%"
    :top (gs [:spacing :p0])
    :left (gs [:spacing :p0])
    :background-color "rgba(0,0,0,0.35)"
    :display :flex
    :justify-content :center
    :align-items :center}

   ".message--modal-container"
   {:background-color (gs [:colors :true-white])
    :padding (gs [:spacing :p20])
    :border-radius (gs [:radius :r4])
    :position :relative
    :min-width "400px"
    :max-width "100%"
    :min-height "300px"
    :max-height "100%"
    :display :flex
    :justify-content :center
    :align-items :center}

   ".message--modal-close-btn"
   (merge
    (create-font-styles {:style :caption-30
                         :color :primary-500
                         :family :primary})
    {:padding (gs [:spacing :p0])
     :white-space :nowrap
     :text-decoration :none
     :cursor :pointer
     :outline :none
     :transition (str "background-color 0.2s ease, "
                      "color 0.2s ease, "
                      "border-color 0.2s ease")
     :border (gs [:borders :border-primary-500-2])
     :border-radius "50%"
     :width (gs [:spacing :p32])
     :height (gs [:spacing :p32])
     :display :flex
     :justify-content :center
     :align-items :center
     :position :absolute
     :top (str "-" (gs [:spacing :p16]))
     :right (str "-" (gs [:spacing :p16]))

     "&:disabled"
     {:background-color (gs [:colors :gray-scale-50])
      :color (gs [:colors :gray-scale-300])
      :border (gs [:borders :border-100-2])
      :cursor :default}})})
