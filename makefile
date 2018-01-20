pkg = blackjack
source = src/$(pkg)/Card.java src/$(pkg)/Deck.java src/$(pkg)/Blackjack.java src/$(pkg)/BlackjackApp.java

build: $(source)
	javac -d . $(source)

run:
	java $(pkg)/BlackjackApp

clean:
	rm $(pkg)