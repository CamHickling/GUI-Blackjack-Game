# My Personal Project

## Blackjack Game

I propose to design a single-player game of blackjack written in Java that is played against a dealer.
Just as in a regular game of Blackjack, the **user** will be able to choose from a few actions on their turn.
These actions will **include**:
- stand = hold the hand as is
- hit = add a card to the hand

The **dealer** will select actions on its turn according to the rules of Blackjack
and will be controlled by some simple logic. 

The game will follow the generally accepted rules of blackjack which **includes but are not limited to:**

- A hand greater than 21 is considered a 'bust' 
- If the player has 'bust' and the dealer has not, the **player loses**
- If the dealer has 'bust' and the player has not, the **player wins**
- If the player has not 'bust' and has drawn a hand greater than the dealer the **player wins**
- If the dealer has not 'bust' and has drawn a hand greater than the player, the **player loses**
- If player and dealer both 'bust' neither win, **it is a tie**
- If neither the player nor dealer have 'bust' and both draw an equal hand, **the player loses**

The user will use this program as it will accurately simulate a game of blackjack,
a game that is fun to play.
However, my game would have a few key advantages. 
People can play just by themselves as it will not require more than a single player to play the game.
Users can play without monetary stakes,
only a score counter/virtual money count to introduce a competitive aspect to the game between single player users.
As my game with properly emulate a real game of Blackjack, users can practise their skills/strategies for real games
without any consequences. 

The game of blackjack interests me because of the statistics and strategy involved.
By completing this project, I can explore this side of the game, and maybe even illustrate the effect of card counting
and different strategies on the winnings/losings over time.

## User Stories

- As a user, I want to be able to select which move to perform as in real blackjack
- As a user, I want to be able to choose how much money to wager
- As a user, I want to be able to view a round report after each round
- As a user, I want to be told if my save game has more balance than my current game
- 
- As a user, I want to choose between loading an old game or starting a new one
- As a user, I want to be able to choose to save or not save at the end of each round

# Instructions for Grader

- You can generate the first required event related to adding Xs to a Y by selecting to hit
- You can generate the second required event related to adding Xs to a Y by selecting to stand
- You can locate my visual component on my menu load screen
- You can save the state of my application by selecting the save and close button
- You can reload the state of my application by selecting the load button in the loading menu