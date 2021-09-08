package classes.ui;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import classes.enums.CommandType;
import exceptions.InvalidCommandException;
import exceptions.InvalidCommandFormatException;

public class Parser {
  private static final Pattern ADD_PATTERN =
      Pattern.compile(
          "^(?<keyword>\\bdeadline\\b|\\bevent\\b|\\btodo\\b)\\s?(?<args>.*)$",
          Pattern.CASE_INSENSITIVE);
  private static final Pattern DONE_PATTERN =
      Pattern.compile("^\\b(?<keyword>done)\\b (?<args>.+)$", Pattern.CASE_INSENSITIVE);
  private static final Pattern LIST_PATTERN =
      Pattern.compile("^(?<keyword>list)$", Pattern.CASE_INSENSITIVE);
  private static final Pattern EXIT_PATTERN =
      Pattern.compile("^(?<keyword>bye)$", Pattern.CASE_INSENSITIVE);

  public Command readInput(Scanner sc)
      throws InvalidCommandException, InvalidCommandFormatException {
    String input = sc.nextLine();
    Matcher addMatcher = ADD_PATTERN.matcher(input);
    Matcher doneMatcher = DONE_PATTERN.matcher(input);
    Matcher listMatcher = LIST_PATTERN.matcher(input);
    Matcher exitMatcher = EXIT_PATTERN.matcher(input);

    if (addMatcher.matches()) {
      String args = addMatcher.group("args");
      String keyword = addMatcher.group("keyword");
      return new Command(CommandType.ADD, keyword, args);
    } else if (doneMatcher.matches()) {
      String args = doneMatcher.group("args");
      String keyword = doneMatcher.group("keyword");
      return new Command(CommandType.COMPLETE, keyword, args);
    } else if (listMatcher.matches()) {
      String keyword = listMatcher.group("keyword");
      return new Command(CommandType.LIST, keyword);
    } else if (exitMatcher.matches()) {
      String keyword = exitMatcher.group("keyword");
      return new Command(CommandType.EXIT, keyword);
    }
    throw new InvalidCommandException(InvalidCommandException.ERROR_GENERIC);
  }
}
