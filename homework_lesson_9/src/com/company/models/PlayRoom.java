package com.company.models;

import com.company.models.Game;
import com.company.models.GameConsole;

import java.util.Arrays;
import java.util.Comparator;

public class PlayRoom {
    public static void main(String[] args) {

        Game.GameDisk[] physicalGames = new Game.GameDisk[4];
        physicalGames[0] = Game.getDisk("FIFA 22", Game.Genre.SPORT, "Football simulation game");
        physicalGames[1] = Game.getDisk("Call of Duty: Modern Warfare", Game.Genre.ACTION, "First-person shooter game");
        physicalGames[2] = Game.getDisk("Gran Turismo Sport", Game.Genre.RACE, "Racing simulation game");
        physicalGames[3] = Game.getDisk("NBA 2K22", Game.Genre.SPORT, "Basketball simulation game");


        System.out.println("Physical Games:");
        for (Game.GameDisk gameDisk : physicalGames) {
            System.out.println("Name: " + gameDisk.getData().getName());
            System.out.println("Genre: " + gameDisk.getData().getGenre());
            System.out.println("Description: " + gameDisk.getDescription());
            System.out.println();
        }
        Arrays.sort(physicalGames, new Comparator<Game.GameDisk>() {
            @Override
            public int compare(Game.GameDisk game1, Game.GameDisk game2) {
                return game1.getData().getGenre().compareTo(game2.getData().getGenre());
            }
        });

        System.out.println("Sorted Physical Games:");
        for (Game.GameDisk gameDisk : physicalGames) {
            System.out.println("Name: " + gameDisk.getData().getName() + ", Genre: " + gameDisk.getData().getGenre());
        }

        Game.VirtualGame[] virtualGames = new Game.VirtualGame[4];
        virtualGames[0] = Game.getVirtualGame("Minecraft", Game.Genre.ACTION);
        virtualGames[1] = Game.getVirtualGame("Fortnite", Game.Genre.ACTION);
        virtualGames[2] = Game.getVirtualGame("The Sims 4", Game.Genre.SIMULATION);
        virtualGames[3] = Game.getVirtualGame("Rocket League", Game.Genre.SPORT);

        System.out.println();
        System.out.println("Virtual Games:");
        for (Game.VirtualGame virtualGame : virtualGames) {
            System.out.println("Name: " + virtualGame.getData().getName());
            System.out.println("Genre: " + virtualGame.getData().getGenre());
            System.out.println();
        }

        Arrays.sort(virtualGames, new Comparator<Game.VirtualGame>() {
            @Override
            public int compare(Game.VirtualGame game1, Game.VirtualGame game2) {
                return Integer.compare(game1.getRating(), game2.getRating());
            }
        });


        System.out.println("\nSorted Virtual Games:");
        for (Game.VirtualGame virtualGame : virtualGames) {
            System.out.println("Name: " + virtualGame.getData().getName() + ", Rating: " + virtualGame.getRating());
        }

        System.out.println();
        GameConsole gameConsole = new GameConsole(GameConsole.ConsoleBrand.SONY, "XC123QeWR");

        gameConsole.powerOn();

        gameConsole.getFirstGamepad().powerOn();

        gameConsole.getSecondGamepad().powerOn();

        gameConsole.loadGame(physicalGames[2].getData());

        gameConsole.playGame();

        gameConsole.powerOff();

    }
}
