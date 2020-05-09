class Info extends Scene {
  Info() {
    buttons = new ArrayList<Button>();
    Button back = new Button(width/2, height/2, 70, 40, "Back");
    buttons.add(back);
  }

  void repeat() {
    background(51);
    textSize(16);
    fill(220);
    textAlign(CENTER, CENTER);
    text("Select using mouse or arrow keys", width/2, height/16);
    text("Enter numbers from keyboard and press Enter to solve", width/2, height/16 + 20);
    text("Press space to reset the entire sudoku or\n press 0 to reset the current block", width/2, height/16 + 60);

    textSize(14);
    fill(255);
    text("Developer : Md Irtiaz Kabir\n\nPrevious Version (v1.0) devlopers :\nMd Imtiaz Kabir & Md Irtiaz Kabir", width/2, 3 * height/4);

    for (Button b : buttons) b.display();
  }
}
