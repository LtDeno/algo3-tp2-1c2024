(ns proyectotp2.sistemal
  (:require
    [clojure.string :as str]))

(defn get-string-if-char-at-first-pos [char string]
  (if (= 0 (compare (first char) (first string)))
    (reduced string)
    char
    )
  )

(defn get-corresponding-rule [char rules]
  (str/trim (str/replace-first (reduce get-string-if-char-at-first-pos char rules) char " "))
  )

(defn change-recurrences-by-rules [toChange rules _unused]
  (reduce #(if (str/blank? (get-corresponding-rule (str %2) rules))
             (str/join (concat %1 (str %2)))
             (str/join (concat %1 (get-corresponding-rule (str %2) rules)))) "" (seq toChange))
  )

(defn transformar-axioma
  "Axioma es un String.
  Reglas es un vector de Strings.
  Iteraciones es un Integer.
  Devuelve un vector de caracteres."
  [axioma reglas iteraciones]
  (vec (seq (reduce #(change-recurrences-by-rules %1 reglas %2) axioma (range iteraciones))))
  )

(comment
  (def rules1 ["X X+YF++YF-FX--FXFX-YF+X" "Y -FX+YFYF++YF+FX--FX-YF"])
  (println (not= 0 (compare "X+YF++YF-FX--FXFX-YF+X" (get-corresponding-rule "Y" rules1))))
  (println (= 0 (compare "-FX+YFYF++YF+FX--FX-YF" (get-corresponding-rule "Y" rules1))))

  (def axiom2 "F-G-G-")
  (def rules2 ["F F-G+F+G-F" "G GG"])
  (println (= 0 (compare "F-G+F+G-F-GG-GG-" (str/join (transformar-axioma axiom2 rules2 1)))))
  (println (= 0 (compare "F-G+F+G-F-GG+F-G+F+G-F+GG-F-G+F+G-F-GGGG-GGGG-" (str/join (transformar-axioma axiom2 rules2 2)))))
  (println (transformar-axioma axiom2 rules2 5))
  (println (= 0 (compare "F-G+F+G-F-GG-GG-" (change-recurrences-by-rules axiom2 rules2 ""))))
  (println (= 0 (compare "F-G+F+G-F-GG+F-G+F+G-F+GG-F-G+F+G-F-GGGG-GGGG-" (change-recurrences-by-rules "F-G+F+G-F-GG-GG-" rules2 ""))))
  )