pkg = blackjack
source = src/$(pkg)/*.java

build: $(source)
	javac -d . $(source)

run:
	java $(pkg)/Blackjack

clean:
	rm $(pkg)