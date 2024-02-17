package com.company.models;

interface Powered {
    void powerOn();
    void powerOff();
}
public class GameConsole implements Powered {

    public enum ConsoleBrand {
        SONY, MICROSOFT, NINTENDO
    }

    public enum GamepadColor {
        BLACK, WHITE, RED, BLUE, GREEN
    }

    private final ConsoleBrand brand;
    private final String serial;
    private Gamepad firstGamepad;
    private Gamepad secondGamepad;
    private boolean isOn;

    private Game activeGame;
    private int waitingCounter;

    public GameConsole(ConsoleBrand brand, String serial) {
        this.brand = brand;
        this.serial = serial;
        this.firstGamepad = new Gamepad(this, brand, 1, serial);
        this.secondGamepad = new Gamepad(this, brand, 2, serial);
        this.isOn = false;
    }


    public ConsoleBrand getBrand() {
        return brand;
    }

    public String getSerial() {
        return serial;
    }

    public Gamepad getFirstGamepad() {
        return firstGamepad;
    }

    public Gamepad getSecondGamepad() {
        return secondGamepad;
    }

    public boolean isOn() {
        return isOn;
    }


    public void setOn(boolean isOn) {
        this.isOn = isOn;
    }

    @Override
    public void powerOn() {
        isOn = true;
    }

    @Override
    public void powerOff() {
        isOn = false;
    }

    public void loadGame(Game game) {
        activeGame = game;
        System.out.println("Game " + game.getName() + " loading");
    }

    public void playGame() {
        checkStatus();
        if (activeGame != null) {
            System.out.println("Play in " + activeGame.getName());
            if (firstGamepad != null && firstGamepad.isOn()) {
                System.out.println("First gamepad charge: " + firstGamepad.getChargeLevel());
                firstGamepad.decreaseChargeLevel();
            }
            if (secondGamepad != null && secondGamepad.isOn()) {
                System.out.println("Second gamepad charge: " + secondGamepad.getChargeLevel());
                secondGamepad.decreaseChargeLevel();
            }
        }
    }

    private void checkStatus() {
        if (firstGamepad == null && secondGamepad == null) {
            waitingCounter++;
            if (waitingCounter > 5) {
                powerOff();
                throw new NoActivityException("The console shuts down due to inactivity");
            }
            System.out.println("Connected gamepad");
        } else {
            waitingCounter = 0;
        }
    }

    private static class NoActivityException extends RuntimeException {
        public NoActivityException(String message) {
            super(message);
        }
    }


    class Gamepad implements Powered {

        private final ConsoleBrand brand;
        private final String consoleSerial;
        private final int connectedNumber;
        private GamepadColor color;
        private double chargeLevel;
        private boolean isOn;

        private final GameConsole console;

        public Gamepad(GameConsole console, ConsoleBrand brand, int connectedNumber, String consoleSerial) {
            this.console = console;
            this.brand = brand;
            this.connectedNumber = connectedNumber;
            this.consoleSerial = consoleSerial;
            this.chargeLevel = 100.0;
            this.isOn = false;
        }


        public ConsoleBrand getBrand() {
            return brand;
        }

        public String getConsoleSerial() {
            return consoleSerial;
        }

        public int getConnectedNumber() {
            return connectedNumber;
        }

        public GamepadColor getColor() {
            return color;
        }

        public void setColor(GamepadColor color) {
            this.color = color;
        }

        public double getChargeLevel() {
            return chargeLevel;
        }

        public void setChargeLevel(double chargeLevel) {
            this.chargeLevel = chargeLevel;
        }

        public boolean isOn() {
            return isOn;
        }

        public void setOn(boolean isOn) {
            this.isOn = isOn;
        }

        @Override
        public void powerOn() {
            isOn = true;
            if (!console.isOn) {
                console.firstGamepad = this;
            }
        }

        @Override
        public void powerOff() {
            isOn = false;
            if (console.firstGamepad == this) {
                console.firstGamepad = console.secondGamepad;
                console.secondGamepad = null;
            }
        }

        public void decreaseChargeLevel() {
            chargeLevel -= 10.0;
            if (chargeLevel <= 0) {
                powerOff();
            }
        }
    }
}
