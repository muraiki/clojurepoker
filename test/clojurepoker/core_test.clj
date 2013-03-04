(ns clojurepoker.core-test
  (:use clojure.test
        clojurepoker.core))

(deftest drawmulti-test
  (testing "Testing drawmulti"
    (is (= (or {:drawncards [{:suit :clubs :rank :2} {:suit :clubs :rank :3}]
                :remainingdeck {:suit :clubs :rank :4}}
               {:drawncards [{:suit :clubs :rank :2} {:suit :clubs :rank :4}]
                :remainingdeck {:suit :clubs :rank :3}}
               {:drawncards [{:suit :clubs :rank :3} {:suit :clubs :rank :4}]
                :remainingdeck {:suit :clubs :rank :2}}))
        (drawmulti 2 [{:suit :clubs :rank :2}
                      {:suit :clubs :rank :3}
                      {:suit :clubs :rank :4}]))))

(deftest extractrankings-test
  (testing "Testing extractrankings"
           (is (= [14 13 12 11 10])
               (hand [:hearts :queen :10 :jack :ace :king]))))

(deftest handeq?-eq
  (testing "Testing handeq? equal hands"
           (is (handeq? (hand [:clubs :2]
                              [:diamonds :10 :6]
                              [:hearts :queen :jack]
                              [:spades :4 :8])
                        (hand [:clubs :2]
                              [:diamonds :10 :6]
                              [:hearts :queen :jack]
                              [:spades :4 :8])))))

(deftest handeq?-noteq
  (testing "Testing handeq? not equal hands"
           (is (false? (handeq? (hand [:clubs :2]
                              [:diamonds :10 :6]
                              [:hearts :queen :jack]
                              [:spades :4 :8])
                        (hand [:clubs :2]
                              [:diamonds :10 :6]
                              [:hearts :queen :jack]
                              [:spades :7 :8]))))))


(deftest resulteq?-eq
  (testing "Testing resulteq? equal results"
           (is (resulteq? {:best :royalflush,
                           :result (hand [:hearts :queen :10 :jack :ace :king]
                                         [:clubs :5]
                                         [:diamonds :5])}
                     {:best :royalflush,
                      :result (hand [:clubs :5]
                                    [:diamonds :5]
                                    [:hearts :queen :king :ace :jack :10])}))))

(deftest resulteq?-eq
  (testing "Testing resulteq? not equal results"
           (is (false? (resulteq? {:best :royalflush,
                                   :result (hand [:hearts :queen :10 :jack :ace :king]
                                                 [:clubs :5]
                                                 [:diamonds :5])}
                     {:best :royalflush,
                      :result (hand [:clubs :5]
                                    [:diamonds :3]
                                    [:hearts :queen :king :ace :jack :10])})))))

(deftest resulteq?-eq
  (testing "Testing resulteq? equal best"
           (is (resulteq? {:best :royalflush,
                           :result (hand [:hearts :queen :10 :jack :ace :king]
                                         [:clubs :5]
                                         [:diamonds :5])}
                     {:best :royalflush,
                      :result (hand [:clubs :5]
                                    [:diamonds :5]
                                    [:hearts :queen :king :ace :jack :10])}))))

(deftest resulteq?-eq
  (testing "Testing resulteq? not equal best"
           (is (false? (resulteq? {:best :royalflush,
                                   :result (hand [:hearts :queen :10 :jack :ace :king]
                                                 [:clubs :5]
                                                 [:diamonds :5])}
                     {:best :onepair,
                      :result (hand [:clubs :5]
                                    [:diamonds :3]
                                    [:hearts :queen :king :ace :jack :10])})))))

(deftest aflush-true
         (testing "Testing aflush for flush ace high")
         (is (= {:high :ace, :cards (hand [:spades :ace :2 :10 :king :5])}
                (aflush (hand [:spades :ace :2 :10 :king :5])))))

(deftest aflush-false
         (testing "Testing aflush for not flush")
         (is (= false
                (aflush (hand [:spades :ace :king :5 :4] [:diamonds :10])))))

(deftest royalflush?-test
         (testing "Testing royalflush? for royal flush true")
         (is (= true
                (royalflush? (hand [:spades :ace :king :jack :queen :10])))))

(deftest royalflush?-false1
         (testing "Testing royalflush? for royal flush false; not same suit")
         (is (= false
                (royalflush? (hand
                               [:spades :ace :king :queen :jack]
                               [:diamonds :10])))))

(deftest royalflush?-false2
         (testing "Testing royalflush? for royal flush false; wrong ranks")
         (is (= false
                (royalflush? (hand [:spades :ace :king :queen :jack :5])))))

(deftest straight-acelow
         (testing "Testing straight for ace low")
         (is (= {:high :5, :cards (hand [:spades :ace :2 :3 :4 :5])}
                (straight (hand [:spades :ace :2 :3 :4 :5])))))

(deftest straight-false
         (testing "Testing straight for invalid straight")
         (is (= false
                (straight (hand [:spades :king :2 :3 :4 :5])))))

(deftest straight-acehigh
         (testing "Testing straight for ace high")
         (is (= {:high :ace, :cards (hand [:spades :10 :ace :king :jack]
                                          [:hearts :queen])}
                 (straight (hand [:spades :10 :ace :king :jack]
                                 [:hearts :queen])))))

(deftest straightflush-acelow
         (testing "Testing straightflush for valid straight flush ace low")
         (is (= {:high :5, :cards (hand [:spades :ace :2 :3 :4 :5])}
                 (straightflush (hand [:spades :ace :2 :3 :4 :5])))))

(deftest straightflush-acehigh
         (testing "Testing straightflush for valid straight flush ace high")
         (is (= {:high :ace, :cards (hand
                                      [:spades :ace :king :queen :10 :jack])}
                (straightflush (hand [:spades :ace :king :queen :10 :jack])))))

(deftest straightflush-false
         (testing "Testing straightflush for invalid straight flush")
         (is (= false
                (straightflush (hand [:spades :ace :3 :4 :5] [:hearts :2])))))

(deftest highcard-ace
         (testing "Testing highcard with ace")
         (is (= :ace
                (highcard (hand [:spades :2 :5]
                                [:hearts :ace]
                                [:diamonds :10 :jack])))))

(deftest kind-2
         (testing "Testing 2 of a kind")
         (is (= (list {:rank :king, :high :ace, :cards
                       (hand [:hearts :king]
                             [:diamonds :king]
                             [:clubs :ace :3 :4])})
                (kind (hand [:hearts :king]
                            [:diamonds :king]
                            [:clubs :ace :3 :4]) 2))))

(deftest kind-3
         (testing "Testing 3 of a kind")
         (is (= (list {:rank :king, :high :ace,
                       :cards (list {:rank :king, :suit :spades}
                                    {:rank :king, :suit :hearts}
                                    {:rank :3, :suit :diamonds}
                                    {:rank :king, :suit :diamonds}
                                    {:rank :ace, :suit :clubs})})
                (kind (list {:rank :king, :suit :spades}
                            {:rank :king, :suit :hearts}
                            {:rank :3, :suit :diamonds}
                            {:rank :king, :suit :diamonds}
                            {:rank :ace, :suit :clubs}) 3))))

(deftest kind-4
         (testing "Testing 4 of a kind")
         (is (= (list {:rank :king, :high :3,
                       :cards (list {:rank :king, :suit :spades}
                                    {:rank :king, :suit :hearts}
                                    {:rank :3, :suit :diamonds}
                                    {:rank :king, :suit :diamonds}
                                    {:rank :king, :suit :clubs})})
                (kind (list {:rank :king, :suit :spades}
                            {:rank :king, :suit :hearts}
                            {:rank :3, :suit :diamonds}
                            {:rank :king, :suit :diamonds}
                            {:rank :king, :suit :clubs}) 4))))

(deftest two-pair
         (testing "Testing two pair")
         (is (= (list {:rank :king, :high :ace,
                       :cards (list {:rank :king, :suit :spades}
                                    {:rank :king, :suit :hearts}
                                    {:rank :3, :suit :diamonds}
                                    {:rank :3, :suit :diamonds}
                                    {:rank :ace, :suit :clubs})}
                      {:rank :3, :high :ace,
                       :cards (list {:rank :king, :suit :spades}
                                    {:rank :king, :suit :hearts}
                                    {:rank :3, :suit :diamonds}
                                    {:rank :3, :suit :diamonds}
                                    {:rank :ace, :suit :clubs})})
                (kind (list {:rank :king, :suit :spades}
                            {:rank :king, :suit :hearts}
                            {:rank :3, :suit :diamonds}
                            {:rank :3, :suit :diamonds}
                            {:rank :ace, :suit :clubs}) 2))))

(deftest two-pair-diffhigh
         (testing "Testing two pair with different high cards for each pair")
         (is (= (list {:rank :king, :high :3,
                       :cards (list {:rank :king, :suit :spades}
                                    {:rank :king, :suit :hearts}
                                    {:rank :3, :suit :diamonds}
                                    {:rank :3, :suit :diamonds}
                                    {:rank :1, :suit :clubs})}
                      {:rank :3, :high :king,
                       :cards (list {:rank :king, :suit :spades}
                                    {:rank :king, :suit :hearts}
                                    {:rank :3, :suit :diamonds}
                                    {:rank :3, :suit :diamonds}
                                    {:rank :1, :suit :clubs})})
                (kind (list {:rank :king, :suit :spades}
                            {:rank :king, :suit :hearts}
                            {:rank :3, :suit :diamonds}
                            {:rank :3, :suit :diamonds}
                            {:rank :1, :suit :clubs}) 2))))

(deftest two-pair-fail
         (testing "Testing two pair with no matching pair")
         (is (= ()
                (kind (list {:rank :king, :suit :spades}
                            {:rank :10, :suit :hearts}
                            {:rank :3, :suit :diamonds}
                            {:rank :4, :suit :diamonds}
                            {:rank :1, :suit :clubs}) 2))))

(deftest fullhouse-true
         (testing "Testing fullhouse ace high full of 5s"
                  (is (= {:high :ace, :full :5}
                         (fullhouse (list {:rank :ace, :suit :spades}
                                          {:rank :ace, :suit :hearts}
                                          {:rank :ace, :suit :diamonds}
                                          {:rank :5, :suit :clubs}
                                          {:rank :5, :suit :spades}))))))

(deftest fullhouse-false
         (testing "Testing fullhouse false"
                  (is (false?
                         (fullhouse (list {:rank :ace, :suit :spades}
                                          {:rank :ace, :suit :hearts}
                                          {:rank :ace, :suit :diamonds}
                                          {:rank :3, :suit :clubs}
                                          {:rank :5, :suit :spades}))))))

(deftest fullhouse-four-of-a-kind
         (testing "Four-of-a-kind should not be mistaken for full house"
                  (is (false?
                         (fullhouse (hand [:spades :ace :5]
                                          [:hearts :ace]
                                          [:diamonds :ace]
                                          [:clubs :ace]))))))

(deftest comparetwobesthands-firstgreater
  (testing "Testing comparetwobesthands, first hand greater."
           (is (resulteq? (comparetwobesthands {:best :royalflush, :result (hand [:hearts :queen :10 :jack :ace :king])}
                                               {:best :onepair, :result (hand [:hearts :2 :3 :4] [:spades :4 :jack])})
                          {:best :royalflush,
                           :result (hand [:hearts :queen :10 :jack :ace :king])}))))

(deftest comparetwobesthands-secondgreater
  (testing "Testing comparetwobesthands, secondhand greater."
           (is (resulteq? (comparetwobesthands {:best :onepair, :result (hand [:hearts :2 :3 :4] [:spades :4 :jack])}
                                               {:best :royalflush, :result (hand [:hearts :queen :10 :jack :ace :king])})
                          {:best :royalflush,
                           :result (hand [:hearts :queen :10 :jack :ace :king])}))))

(deftest comparetwobesthands-queenpair
  (testing "Testing comparetwobesthands, pair of queens beats pair of fours."
           (is (resulteq? (comparetwobesthands {:best :onepair, :result (hand [:hearts :2 :3 :4] [:spades :4 :jack])}
                                               {:best :onepair, :result (hand [:hearts :2 :3 :queen] [:spades :queen :jack])})
                          {:best :onepair,
                           :result (hand [:hearts :2 :3 :queen] [:spades :queen :jack])}))))

(deftest comparetwobesthands-queenpairacehigh
  (testing "Testing comparetwobesthands, pair of queens, ace high."
           (is (resulteq? (comparetwobesthands {:best :onepair, :result (hand [:hearts :2 :3 :queen] [:spades :queen :ace])}
                                               {:best :onepair, :result (hand [:hearts :2 :3 :queen] [:spades :queen :jack])})
                          {:best :onepair,
                           :result (hand [:hearts :2 :3 :queen] [:spades :queen :ace])}))))

(deftest bestallhands-royalflush
  (testing "Testing bestallhands, royal flush"
           (is (resulteq? {:best :royalflush
                           :result (hand [:hearts :queen :10 :jack :ace :king])}
                  (bestallhands (hand [:clubs :5]
                                      [:diamonds :5]
                                      [:hearts :queen :king :ace :jack :10]))))))

(deftest bestallhands-straightflush
  (testing "Testing bestallhands, straight flush ace low"
           (is (resulteq? {:best :straightflush
                           :result (hand [:hearts :ace :2 :3 :4 :5])}
                  (bestallhands (hand [:clubs :5]
                                      [:diamonds :5]
                                      [:hearts :ace :2 :3 :4 :5]))))))

(deftest bestallhands-fourkind
  (testing "Testing bestallhands, four of a kind"
           (is (resulteq? {:best :fourkind
                           :result (hand [:hearts :5]
                                         [:diamonds :5]
                                         [:clubs :5]
                                         [:spades :5 :ace])}
                          (bestallhands (hand [:hearts :5]
                                              [:diamonds :5 :jack]
                                              [:clubs :5 :10]
                                              [:spades :5 :ace]))))))

(deftest bestallhands-fullhouse
  (testing "Testing bestallhands, full house"
           (is (resulteq? {:best :fullhouse
                           :result (hand [:hearts :5 :2]
                                         [:clubs :5 :2]
                                         [:diamonds :5])}
                  (bestallhands (hand [:hearts :5 :2 :jack] [:clubs :5 :2] [:diamonds :5 :2]))))))

(deftest bestallhands-flush
  (testing "Testing bestallhands, flush"
           (is (resulteq? {:best :flush
                           :result (hand [:hearts :ace :2 :3 :jack :8])}
                  (bestallhands (hand [:hearts :ace :2 :3 :jack :8]
                                      [:spades :jack :queen]))))))

(deftest bestallhands-straight
  (testing "Testing bestallhands, straight ace low"
           (is (resulteq? {:best :straight
                           :result (hand [:hearts :ace :2 :3 :5]
                                         [:spades :4])}
                  (bestallhands (hand [:hearts :ace :2 :3 :5]
                                      [:spades :4 :5]
                                      [:clubs :5]))))))

(deftest bestallhands-straighthigh
  (testing "Testing bestallhands, straight ace high"
           (is (resulteq? {:best :straight
                           :result (hand [:hearts :ace :king :queen]
                                         [:spades :jack :10])}
                  (bestallhands (hand [:hearts :ace :king :queen]
                                      [:spades :jack :10]
                                      [:clubs :5 :ace]))))))

(deftest bestallhands-threekind
  (testing "Testing bestallhands, three of a kind"
           (is (resulteq? {:best :threekind
                           :result (hand [:hearts :ace :10]
                                         [:spades :ace :9]
                                         [:clubs :ace])}
                  (bestallhands (hand [:hearts :ace :10]
                                      [:spades :ace :9]
                                      [:clubs :ace :2 :5]))))))

(deftest bestallhands-twopair
  (testing "Testing bestallhands, two pair"
           (is (resulteq? {:best :twopair
                           :result (hand [:hearts :ace :10]
                                         [:spades :ace :10]
                                         [:clubs :jack])}
                  (bestallhands (hand [:hearts :ace :10]
                                      [:spades :ace :10]
                                      [:clubs :jack :2 :5]))))))

(deftest bestallhands-onepair
  (testing "Testing bestallhands, one pair"
           (is (resulteq? {:best :onepair
                           :result (hand [:hearts :ace :9]
                                         [:spades :ace :8]
                                         [:clubs :jack])}
                  (bestallhands (hand [:hearts :ace :9]
                                      [:spades :ace :8]
                                      [:clubs :jack :2 :5]))))))

(deftest bestallhands-highcard
  (testing "Testing bestallhands, high card"
           (is (resulteq? {:best :highcard
                           :result (hand [:hearts :ace :9]
                                         [:spades :7 :8]
                                         [:clubs :jack])}
                  (bestallhands (hand [:hearts :ace :9]
                                      [:spades :7 :8]
                                      [:clubs :jack :2 :5]))))))

(deftest comparetwo7-flush
  (testing "Testing comparetwo7, flush"
           (is (resulteq? {:best :flush
                           :result (hand [:hearts :ace :2 :3 :jack :8])}
                  (comparetwo7 (hand [:hearts :ace :2 :3 :jack :8]
                                     [:spades :jack :queen])
                               (hand [:hearts :2 :4 :8 :queen]
                                     [:clubs :2 :8 :jack]))))))