(ns proyectotp2.sistemal-test
  (:require [clojure.test :refer :all]
            [clojure.string :as str]
            [proyectotp2.sistemal :as sisl]))


(def rules1 ["X X+YF++YF-FX--FXFX-YF+X" "Y -FX+YFYF++YF+FX--FX-YF"])
(def axiom2 "F-G-G-")
(def rules2 ["F F-G+F+G-F" "G GG"])

(deftest a-test
  (testing
    (not= 0 (compare "X+YF++YF-FX--FXFX-YF+X" (sisl/get-corresponding-rule "Y" rules1)))
    (= 0 (compare "-FX+YFYF++YF+FX--FX-YF" (sisl/get-corresponding-rule "Y" rules1)))

    (= 0 (compare "F-G+F+G-F-GG-GG-" (str/join (sisl/transformar-axioma axiom2 rules2 1))))
    (= 0 (compare "F-G+F+G-F-GG+F-G+F+G-F+GG-F-G+F+G-F-GGGG-GGGG-" (str/join (sisl/transformar-axioma axiom2 rules2 2))))
    (= 0 (compare "F-G+F+G-F-GG-GG-" (sisl/change-recurrences-by-rules axiom2 rules2 "")))
    (= 0 (compare "F-G+F+G-F-GG+F-G+F+G-F+GG-F-G+F+G-F-GGGG-GGGG-" (sisl/change-recurrences-by-rules "F-G+F+G-F-GG-GG-" rules2 "")))
    )
  )