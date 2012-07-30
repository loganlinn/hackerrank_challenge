(ns hackerrank.httpsolver
  (:require [clj-http.client :as client]
            hackerrank.challenge
            hackerrank.numberparse)
  (:use [cheshire.core]))

(defn- parse-challenge [request]
  "Extracts id and encrypted number of challenge"
  (let [body (parse-string (request :body))
        game (body "game")]
    {:id (game "id")
     :n (game "n")
     :number (game "cph_number")}))

(defn- get-challenge [n]
  "Makes HTTP request for challenge"
  (parse-challenge (client/post "https://hackerrank.com/game.json"
                               {:form-params {:n n
                                              :remote true}})))
(defn solve-challenge [challenge]
  "Fetches challenge and solves it. Answer (integer) is returned"
  (let [solution (hackerrank.challenge/solve (challenge :number))]
    (if-not solution
      (println "Unable to solve" challenge)
      (hackerrank.numberparse/parse solution))))

(defn- submit-answer [challenge answer]
  (client/put "https://hackerrank.com/game.json"
               {:form-params {:id (challenge :id)
                              :answer answer
                              :remote true}}))

(defn- login [username password]
  "Makes request to login to HackerRank."
  (client/post "https://hackerrank.com/users/sign_in.json"
               {:form-params {(keyword "user[login]") username
                              (keyword "user[password]") password
                              (keyword "user[remember_me]") true
                              :commit "Sign in"
                              :remote true}}))

(defn- solve-challenges [challenges credentials]
  "Solves a batch of challenges"
  (binding [clj-http.core/*cookie-store* (clj-http.cookies/cookie-store)]
    (if (login (credentials :username) (credentials :password))
      (doseq [n challenges]
        (println (str "Solving challenge #" n "..."))
        (let [challenge (get-challenge n)
              answer (solve-challenge challenge)]
          (if answer (submit-answer challenge answer))))
      (println "Failed to login"))))

(defn -main [start-challenge num-challenges]
  "Solves batches of challenges sequentially. Given a starting challenge number, and a batch size."
  (let [start (Integer/parseInt start-challenge 10)
        end (+ start (Integer/parseInt num-challenges 10))]
    (println (str "Starting challenges " start ".." end))
    (solve-challenges (range start end) {:username (System/getenv "HACKERRANK_USERNAME")
                                         :password (System/getenv "HACKERRANK_PASSWORD")})))
