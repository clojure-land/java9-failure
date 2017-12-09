(ns failure.main)

(defn -main [& args]
  (println "class:")
  (println (failure.Class/answer))

  (println "\ninterface:")
  (println (failure.Interface/answer)))
