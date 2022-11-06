package server;

import java.util.ArrayList;
import java.util.regex.Pattern;
import lombok.Getter;
import plate.MonteCarloPlate;

@Getter
public class CommandParser {

  private MonteCarloPlate plate;
  private MonteCarloPlate guessingPlate;
  public final static Integer startingAttempts = 5;
  private int attemptsLeft = 0;
  public static final Pattern POINT_PATTERN = Pattern.compile(
      "[(][0-9]{1,13}(.[0-9]*)?;[0-9]{1,13}(.[0-9]*)?[)]");

  public static final String WHO = "Volodymyr Kravchuk, group K-26, variant #27 "
      + "(guessing points on the place using Monte Carlo method)";

  public static final String HELP =
      "Hello! This is Monte Carlo guessing game. Here some main commands:\n"
          + "\n"
          + "who\n"
          + "\tShows info about the author and the programm\n"
          + "help\n"
          + "\tShows this message\n"
          + "start-guessing\n"
          + "\tStarts guessing of points that are stored on server\n"
          + "guess (x;y)\n"
          + "\tGuesses if poit x;y (where x and y are doubles or integers) is stored of server and \n"
          + "\treturn plate of already guessed points.\n"
          + "stop-guessing\n"
          + "\tStops guessing, resets numbers of attemps and resets plate of guessed points\n"
          + "stop-server\n"
          + "\tStops server and closes all connections\n";

  public static final String COMMAND_DO_NOT_EXIST = "This command do not exist.";
  public static final String WRONG_COMMAND_FORMAT = "Wrong command syntax error. Command \"help\" to get help.";

  public static final String START_GUESSING =
      "Guessing is started. You have " + startingAttempts + " attempts left";

  public static final String STOP_GUESSING = "Guessing is stopped. Guessing plate has been reset. "
      + "Command \"start-guessing\" to start guess again.";

  public CommandParser(MonteCarloPlate plate) {
    this.plate = plate;
    resetGuessingPlate();
  }

  public String parseCommand(String command) {
    return switch ((command + ' ').substring(0, (command + ' ').indexOf(' '))) {
      case "who" -> WHO;
      case "help" -> HELP;
      case "start-guessing" -> startGuessingCommand();
      case "stop-guessing" -> stopGuessingCommand();
      case "guess" -> guessCommandParser(command);
      default -> COMMAND_DO_NOT_EXIST;
    };
  }

  private String guessCommandParser(String command) {
    command = command.substring(command.indexOf(' ') + 1);
    if (POINT_PATTERN.matcher(command).matches()) {
      if (attemptsLeft <= 0) {
        return "You have no attempts left. \"start-guessing\" to begin guessing again";
      }
      StringBuilder result = new StringBuilder();
      double x = Double.parseDouble(command.substring(1, command.indexOf(';')));
      double y = Double.parseDouble(
          command.substring(command.indexOf(';') + 1, command.length() - 1));
      if (isInExistingPointRange(x, y)) {
        guessingPlate.getTable().get((int) Math.floor(y)).set((int) Math.floor(x), true);
        result.append("POINT EXIST\n").append(guessingPlate.toString());
      } else {
        result.append("POINT DOES NOT EXIST\n").append(guessingPlate.toString());
      }
      attemptsLeft--;
      result.append("You have ").append(attemptsLeft).append(" more attempts to guess");
      return result.toString();
    } else {
      return WRONG_COMMAND_FORMAT;
    }
  }

  private String startGuessingCommand() {
    attemptsLeft = startingAttempts;
    resetGuessingPlate();
    return START_GUESSING;
  }

  private String stopGuessingCommand() {
    attemptsLeft = 0;
    resetGuessingPlate();
    return START_GUESSING;
  }

  private void resetGuessingPlate() {
    this.guessingPlate = new MonteCarloPlate(plate.getWidth(), plate.getHeight());
    for (int i = 0; i < plate.getHeight(); i++) {
      guessingPlate.getTable().add(new ArrayList<>());
      for (int j = 0; j < plate.getHeight(); j++) {
        guessingPlate.getTable().get(i).add(false);
      }
    }
  }

  private boolean isInExistingPointRange(double x, double y) {
    return plate.isExist((int) Math.floor(x), (int) Math.floor(y));
  }
}
