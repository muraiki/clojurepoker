# clojurepoker

A poker library for Clojure and Clojurescript, early stages.

# Usage
A card is a map with this format:
```clj
{:suit :hearts, :rank :ace}
```

Generate a hand:
```clj
(hand [:hearts :ace] [:spades :queen] [:diamonds :king] [:clubs :4 :8])
; result
[{:suit :hearts :rank :ace}
 {:suit :spades :rank :queen}]
 {:suit :diamonds :rank :king}]
 {:suit :clubs :rank :4}]
 {:suit :clubs :rank :8}]]
```

Draw seven cards and determine the best possible hand that can generated from them:
```clj
(bestallhands (:drawncards (drawmulti 7 deck)))
; result
{:best :straight, :result ({:suit :clubs, :rank :jack}
													 {:suit :spades, :rank :9}
                           {:suit :hearts, :rank :8}
                           {:suit :diamonds, :rank :10}
                           {:suit :spades, :rank :queen})}
```

Compare two seven card hands (as in Texas Hold 'em):
```clj
(comparetwo7 (hand [:hearts :queen :10 :jack :ace :king])
             (hand [:hearts :2 :3 :4] [:spades :4 :jack]))
; result
{:best :flush :result [{:suit :hearts, :rank :ace}
											 {:suit :hearts, :rank :2}
                       {:suit :hearts, :rank :3}
                       {:suit :hearts, :rank :jack}
                       {:suit :hearts, :rank :8}]}
```

## License

Copyright © 2013 Erik Ferguson and Chip Hogg.

Distributed under the Eclipse Public License, the same as Clojure.