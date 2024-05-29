(ns proyectotp2.main
  (:require
    [proyectotp2.sistemal :as sisl]
    [proyectotp2.filemanager :as fman]))

(java.util.Locale/setDefault java.util.Locale/US)

(defn -main [read_file num_iterations write_file]
  (let [read_vect (fman/obtener-archivo read_file)
        cmd_seq (sisl/transformar-axioma (fman/obtener-axioma read_vect) (fman/obtener-reglas read_vect) (Integer/parseInt num_iterations))
        turn_angle (fman/obtener-angulo read_vect)]

    (fman/escribir-primer-linea write_file)
    (sisl/procesar cmd_seq (Float/parseFloat turn_angle) write_file)
    (fman/escribir-ultima-linea write_file)))