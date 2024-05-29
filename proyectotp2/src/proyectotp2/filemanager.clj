(ns proyectotp2.filemanager
  (:require
    [clojure.string :as str]))

(defn obtener-archivo
  "Devuelve un vector de Strings sacado del archivo de nombre especificado con separacion de salto de linea."
  [read_file]
  (str/split (slurp read_file) #"\n"))

(defn obtener-angulo
  "Devuelve el angulo como String. El primer String del vector obtenido del archivo de lectura."
  [read_vect]
  (get read_vect 0))

(defn obtener-axioma
  "Devuelve el axioma como String. El segundo String del vector obtenido del archivo de lectura."
  [read_vect]
  (get read_vect 1))

(defn obtener-reglas
  "Devuelve el axioma como String. El tercero a ultimo String del vector obtenido del archivo de lectura."
  [read_vect]
  (next (next read_vect)))

(defn escribir-primer-linea
  "Escribe la primera linea para un archivo SVG en el archivo especificado.
  Sobreescribe el archivo."
  [write_file]
  (spit write_file "<svg viewBox=\"-900 -900 1500 1500\" preserveAspectRatio=\"xMidYMid meet\" xmlns=\"http://www.w3.org/2000/svg\">"))

(defn escribir-comienzo-path
  "Escribe el comienzo para el elemento Path de SVG en el archivo especificado.
  No sobreescribe el archivo."
  [write_file]
  (spit write_file "\n<path d=\"M 0.00 0.00" :append true))

(defn escribir-path-M
  "Escribe las coordenadas para moverse a una coordenada en Path de SVG en el archivo especificado.
  No sobreescribe el archivo."
  [write_file end_pos]
  (spit write_file (format " M %.2f %.2f" (get end_pos 0) (get end_pos 1)) :append true))

(defn escribir-path-L
  "Escribe las coordenadas para moverse a una coordenada en Path de SVG en el archivo especificado.
  No sobreescribe el archivo."
  [write_file end_pos]
  (spit write_file (format " L %.2f %.2f" (get end_pos 0) (get end_pos 1)) :append true))

(defn escribit-fin-path
  "Escribe el final para el elemento Path de SVG en el archivo especificado.
  No sobreescribe el archivo."
  [write_file]
  (spit write_file "\" stroke-width=\"1\" stroke=\"black\" fill=\"none\"/>" :append true))

(defn escribir-ultima-linea
  "Escribe la ultima linea para un archivo SVG en el archivo especificado.
  No sobreescribe el archivo."
  [write_file]
  (spit write_file "\n</svg>" :append true))