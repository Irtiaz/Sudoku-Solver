class Menu extends Scene {

  Menu() {
    buttons = new ArrayList<Button>();
    Button start = new Button(width/2, height/2, 120, 40, "Start");
    Button info = new Button(width/2, height/2+50, 120, 40, "Instructions");
    Button exit = new Button(width/2, height/2+100, 120, 40, "Exit");
    buttons.add(start);
    buttons.add(info);
    buttons.add(exit);
  }

  void repeat() {
    background(51);
    textSize(50);
    textAlign(CENTER, CENTER);
    fill(34, 177, 76);
    text("Sudoku Solver", width/2, height/8);
    textSize(20);
    text("v2.0", 3 * width/4, height/8 + 40);

    for (Button b : buttons) b.display();
  }
}
