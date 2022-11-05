package server;

import java.util.regex.Pattern;
import plate.MonteCarloPlate;

public class CommandParser {

  MonteCarloPlate plate;
  public static final Pattern POINT_PATTERN = Pattern.compile(
      "[(][0-9]{1,13}(\\\\.[0-9]*)?;[0-9]{1,13}(\\\\.[0-9]*)?[)]");

  public static final String WHO = "Volodymyr Kravchuk, group K-26, variant #27 "
      + "(guessing points on the place using Monte Carlo method)";

  public static final String HELP =
      "To guess the point you should type command \"guess\" "
          + "and the coordinates of the points in the following format:"
          + "guess (x;y)"
          + "where x and y are int or double values."
          + "If it will be in point range of the point that exists server will return"
          + "conformation of guessing and client will update its table.";

  public static final String COMMAND_DO_NOT_EXIST = "This command do not exist";
  public static final String WRONG_COMMAND_FORMAT = "Wrong command error. Command \"help\" to get help;";

  public CommandParser(MonteCarloPlate plate) {
    this.plate = plate;
  }

  public String parseCommand(String command) {
    return switch (command.substring(0, command.indexOf(' '))) {
      case "who" -> WHO;
      case "help" -> HELP;
      case "guess" -> guessCommandParser(command);
      default -> COMMAND_DO_NOT_EXIST;
    };
  }

  private String guessCommandParser(String command) {
    command = command.substring(command.indexOf(' ') + 1);
    if (POINT_PATTERN.matcher(command).matches()) {
      double x = Double.parseDouble(command.substring(0, command.indexOf(';')));
      double y = Double.parseDouble(command.substring(command.indexOf(';')) + 1);
      if (isInExistingPointRange(x, y)) {
        return "POINT EXIST";
      } else {
        return "POINT DOES NOT EXIST";
      }
    } else {
      return WRONG_COMMAND_FORMAT;
    }
  }

  private boolean isInExistingPointRange(double x, double y) {
    return plate.isExist((int) Math.floor(x), (int) Math.floor(y));
  }
}
