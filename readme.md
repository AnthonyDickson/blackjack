# Blackjack
A game of blackjack run on the terminal.
The main feature of this game are the AI agents that I implement.
I implement a few rule based agents and one agent that learns through playing (see [src/blackjack/WizardOfOdds.java](https://github.com/eight0153/blackjack/blob/master/src/blackjack/WizardOfOdds.java)).

## Usage

First compile using make:

```shell
> make build
```

To run the application:

```shell
> make run
```

OR

```shell
> java blackjack/BlackJack
```

To clean the working directory of the compiled files use make:

```shell
> make clean
```

You can change which AI agents you play against by (un)commenting the relevant lines in the file [src/blackjack/Blackjack.java](https://github.com/eight0153/blackjack/blob/master/src/blackjack/Blackjack.java), and recompiling the code.
